package vn.edu.fpt.authentication.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.config.kafka.producer.SendEmailProducer;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.dto.cache.UserInfo;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.event.SendEmailEvent;
import vn.edu.fpt.authentication.dto.request.account.*;
import vn.edu.fpt.authentication.dto.response.account.CreateAccountResponse;
import vn.edu.fpt.authentication.dto.response.account.GetAccountResponse;
import vn.edu.fpt.authentication.dto.response.account.LoginResponse;
import vn.edu.fpt.authentication.entity.Account;
import vn.edu.fpt.authentication.entity._Permission;
import vn.edu.fpt.authentication.entity._Role;
import vn.edu.fpt.authentication.exception.BusinessException;
import vn.edu.fpt.authentication.repository.AccountRepository;
import vn.edu.fpt.authentication.repository.BaseMongoRepository;
import vn.edu.fpt.authentication.repository.RoleRepository;
import vn.edu.fpt.authentication.service.AccountService;
import vn.edu.fpt.authentication.service.RoleService;
import vn.edu.fpt.authentication.service._TokenService;
import vn.edu.fpt.authentication.utils.AuthenticationUtils;

import java.util.*;
import java.util.stream.Collectors;

import static vn.edu.fpt.authentication.utils.AuthenticationUtils.addPermissionPrefix;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final static Integer RECOMMEND_PASSWORD_LENGTH = 8;
    private final AccountRepository accountRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final _TokenService tokenService;
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;
    private final SendEmailProducer sendEmailProducer;
    private final MongoTemplate mongoTemplate;
    private final RoleService roleService;

    @Override
    public void init() {
        if (accountRepository.findAccountByEmailOrUsername("admin").isEmpty()) {
            _Role adminRole = roleRepository.findByRoleName("ADMIN")
                    .orElseThrow(() -> new BusinessException("Role ADMIN not exist"));
            Account account = Account.builder()
                    .email("admin.flab@gmail.com")
                    .username("admin")
                    .fullName("admin")
                    .password(passwordEncoder.encode(randomPassword()))
                    .roles(List.of(adminRole))
                    .build();
            try {
                accountRepository.save(account);
                log.info("Init account success");
            } catch (Exception ex) {
                throw new BusinessException("Can't init account in database: " + ex.getMessage());
            }
        }
    }

    @Override
    public UserDetails getUserByUsername(String username) {
        Account account = accountRepository.findAccountByEmailOrUsername(username)
                .orElseThrow(() -> new BusinessException("Username or email not found!"));
        List<_Role> roles = account.getRoles();
        return User.builder()
                .username(account.getAccountId())
                .password(account.getPassword())
                .authorities(this.getAuthorities(roles).toArray(String[]::new))
                .build();
    }

    @Override
    public void changeEmail(String id, ChangeEmailRequest request) {
        Account account = accountRepository.findAccountByAccountId(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account ID not exist!"));

        if (accountRepository.findAccountByEmail(request.getNewEmail()).isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Email already exist!");
        }
        account.setEmail(request.getNewEmail());

        pushAccountInfo(account);

        try {
            accountRepository.save(account);
            log.info("Change email success");
        } catch (Exception ex) {
            log.error("Change email failed: {}", ex.getMessage());
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't change email to database: " + ex.getMessage());
        }
    }

    @Override
    public void changePassword(String id, ChangePasswordRequest request) {
        Account account = accountRepository.findAccountByAccountId(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account ID not exist!"));
        if (Boolean.FALSE.equals(passwordEncoder.matches(request.getOldPassword(), account.getPassword()))) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Password incorrect!");
        }
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));
        try {
            accountRepository.save(account);
            log.info("Change password success");
        } catch (Exception ex) {
            throw new BusinessException("Can't not save account to database when change password: " + ex.getMessage());
        }
    }

    @Override
    public CreateAccountResponse createAccount(CreateAccountRequest request) {

        _Role defaultRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new BusinessException("Role USER not found in database"));

        Account account = Account.builder()
                .fullName(request.getFullName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(defaultRole))
                .build();

        pushAccountInfo(account);

        try {
            account = accountRepository.save(account);
            log.info("Create account success with email: {} and account id is: {}", account.getEmail(), account.getAccountId());
        } catch (Exception ex) {
            throw new BusinessException("Can't create account in database: " + ex.getMessage());
        }

        return CreateAccountResponse.builder()
                .accountId(account.getAccountId())
                .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        Account account = accountRepository.findAccountByEmailOrUsername(request.getEmailOrUsername())
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Username or email not exist!"));
        if (Boolean.FALSE.equals(passwordEncoder.matches(request.getPassword(), account.getPassword()))) {
            throw new BusinessException(ResponseStatusEnum.UNAUTHORIZED, "Password incorrect!");
        }
        if (Boolean.FALSE.equals(account.getIsCredentialNonExpired())) {
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Account credential expired!");
        }
        if (Boolean.FALSE.equals(account.getIsNonExpired())) {
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Account expired!");
        }
        if (Boolean.FALSE.equals(account.getIsNonLocked())) {
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Account locked");
        }
        if (Boolean.FALSE.equals(account.getIsEnabled())) {
            throw new BusinessException(ResponseStatusEnum.FORBIDDEN, "Account disable!");
        }

        String token = tokenService.generateToken(account, getUserByUsername(account.getUsername()));
        String refreshToken = tokenService.generateRefreshToken(request);

        return LoginResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .email(account.getEmail())
                .token(token)
                .tokenExpireTime(tokenService.getExpiredTimeFromToken(token))
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(tokenService.getExpiredTimeFromToken(refreshToken))
                .build();
    }

    @Override
    public LoginResponse refreshToken(RefreshTokenRequest request) {
        LoginRequest loginRequest = tokenService.getLoginRequestFromToken(request.getRefreshToken());
        return login(loginRequest);
    }

    @Override
    public void resetPassword(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account not found"));
        String newPassword = randomPassword();
        account.setPassword(passwordEncoder.encode(newPassword));

        SendEmailEvent sendEmailEvent = SendEmailEvent.builder()
                .sendTo(account.getEmail())
                .bcc(null)
                .cc(null)
                .templateId("6369cc43f258642ab1e18504")
                .params(Map.of("NEW_PASSWORD", newPassword))
                .build();

        sendEmailProducer.sendMessage(sendEmailEvent);

        try {
            accountRepository.save(account);
            log.info("Reset password success: {}", id);
        } catch (Exception ex) {
            throw new BusinessException("Can't reset password account to database: " + ex.getMessage());
        }
    }

    @Override
    public void addRoleToAccount(String id, AddRoleToAccountRequest request) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account id not found"));

        _Role role = roleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Role id not found"));

        List<_Role> roles = account.getRoles();
        Optional<_Role> roleInAccount = roles.stream().filter(v -> v.getRoleId().equals(request.getRoleId())).findAny();
        if (roleInAccount.isPresent()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Role already in account");
        }
        roles.add(role);
        account.setRoles(roles);

        pushAccountInfo(account);

        try {
            accountRepository.save(account);
            log.info("Add role to account success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save account update role to database");
        }
    }

    @Override
    public void deleteAccountById(String id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account not found"));
        try {
            accountRepository.delete(account);
            log.info("Delete account success");
        } catch (Exception ex) {
            throw new BusinessException("Delete account failed: " + ex.getMessage());
        }

        removeAccountInfo(account);
    }

    @Override
    public void removeRoleFromAccount(String id, String roleId) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Account not found"));
        Optional<_Role> optionalRole = account.getRoles().stream().filter(v -> v.getRoleId().equals(roleId)).findAny();
        if (optionalRole.isEmpty()) {
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Role id not exist in account");
        }
        _Role role = optionalRole.get();
        List<_Role> roles = account.getRoles();
        roles.remove(role);

        account.setRoles(roles);

        pushAccountInfo(account);
        try {
            accountRepository.save(account);
            log.info("Remove role from account success");
        } catch (Exception ex) {
            throw new BusinessException("Can't save account after remove role to database: " + ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetAccountResponse> getAccountByCondition(GetAccountRequest request) {
        Query query = new Query();
        if (Objects.nonNull(request.getAccountId())) {
            query.addCriteria(Criteria.where("_id").is(request.getAccountId()));
        }

        if (Objects.nonNull(request.getEmail())) {
            query.addCriteria(Criteria.where("email").regex(request.getEmail()));
        }

        if (Objects.nonNull(request.getUsername())) {
            query.addCriteria(Criteria.where("username").regex(request.getUsername()));
        }

        if (Objects.nonNull(request.getFullName())) {
            query.addCriteria(Criteria.where("full_name").regex(request.getFullName()));
        }

        if (Objects.nonNull(request.getNonExpired())) {
            query.addCriteria(Criteria.where("is_non_expired").is(request.getNonExpired()));
        }

        if (Objects.nonNull(request.getNonLocked())) {
            query.addCriteria(Criteria.where("is_non_locked").is(request.getNonLocked()));
        }

        if (Objects.nonNull(request.getCredentialNonExpired())) {
            query.addCriteria(Criteria.where("is_credential_non_expired").is(request.getCredentialNonExpired()));
        }

        if (Objects.nonNull(request.getEnable())) {
            query.addCriteria(Criteria.where("is_enable").is(request.getEnable()));
        }

        query.addCriteria(Criteria.where("created_date").gte(request.getCreatedDateFrom()).lte(request.getCreatedDateTo()));
        query.addCriteria(Criteria.where("last_modified_date").gte(request.getLastModifiedDateFrom()).lte(request.getLastModifiedDateTo()));

        Long totalElements = mongoTemplate.count(query, Account.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<Account> accounts;
        try {
            accounts = mongoTemplate.find(query, Account.class);
        } catch (Exception ex) {
            throw new BusinessException(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Can't find account by condition: " + ex.getMessage());
        }
        List<GetAccountResponse> getAccountResponses = accounts.stream().map(this::convertToGetAccountResponse).collect(Collectors.toList());
        return new PageableResponse<>(request, totalElements, getAccountResponses);
    }

    private GetAccountResponse convertToGetAccountResponse(Account account) {
        return GetAccountResponse.builder()
                .accountId(account.getAccountId())
                .username(account.getUsername())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .isEnable(account.getIsEnabled())
                .isNonExpired(account.getIsNonExpired())
                .isNonLocked(account.getIsNonLocked())
                .isCredentialNonExpired(account.getIsCredentialNonExpired())
                .roles(account.getRoles().stream().map(roleService::convertToRoleResponse).collect(Collectors.toList()))
                .createdDate(account.getCreatedDate())
                .lastModifiedDate(account.getLastModifiedDate())
                .build();
    }

    private String randomPassword() {

        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        int[] randomNumber;
        do {
            randomNumber = random.ints(RECOMMEND_PASSWORD_LENGTH * 5L, 48, 122)
                    .filter(x -> x > 97 && x < 122 || x > 65 && x < 90 || x > 48 && x < 57)
                    .toArray();
        } while (randomNumber.length < RECOMMEND_PASSWORD_LENGTH);

        for (int i = 0; i < RECOMMEND_PASSWORD_LENGTH; i++) {
            stringBuilder.append((char) randomNumber[i]);
        }
        return stringBuilder.toString();
    }

    private List<String> getAuthorities(List<_Role> roles) {
        List<String> authorities = new ArrayList<>();
        List<_Role> roleEnable = roles.stream()
                .filter(_Role::getIsEnable).collect(Collectors.toList());
        authorities.addAll(roleEnable.stream()
                .map(_Role::getRoleName)
                .map(AuthenticationUtils::addRolePrefix)
                .collect(Collectors.toList()));
        authorities.addAll(roleEnable.stream()
                .filter(v -> Objects.nonNull(v.getPermissions()))
                .map(this::getPermissions)
                .flatMap(List::stream)
                .collect(Collectors.toList()));

        return authorities;
    }

    private List<String> getPermissions(_Role role) {
        return role.getPermissions().stream().filter(Objects::nonNull)
                .filter(_Permission::getIsEnable)
                .map(_Permission::getPermissionName)
                .map(v -> addPermissionPrefix(role.getRoleName(), v))
                .collect(Collectors.toList());
    }

    private void pushAccountInfo(Account account) {
        UserInfo userInfo = UserInfo.builder()
                .email(account.getEmail())
                .username(account.getUsername())
                .fullName(account.getFullName())
                .roles(account.getRoles().stream().map(_Role::getRoleName).collect(Collectors.toList()))
                .build();

        try {
            redisTemplate.opsForValue().set(String.format("userinfo:%s", account.getAccountId()), objectMapper.writeValueAsString(userInfo));
            log.info("Push UserInfo to Redis success");
        } catch (JsonProcessingException ex) {
            throw new BusinessException("Can't push user info to Redis: " + ex.getMessage());
        }
    }

    private void removeAccountInfo(Account account) {
        try {
            Boolean result = redisTemplate.delete(String.format("userinfo:%s", account.getAccountId()));
            if (result) {
                log.info("remove account info success");
            } else {
                log.info("account info has been deleted");
            }
        } catch (Exception ex) {
            throw new BusinessException("Can't remove userinfo in redis: " + ex.getMessage());
        }
    }
}
