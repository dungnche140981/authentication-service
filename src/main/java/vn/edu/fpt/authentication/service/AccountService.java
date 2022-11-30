package vn.edu.fpt.authentication.service;

import org.springframework.security.core.userdetails.UserDetails;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.account.*;
import vn.edu.fpt.authentication.dto.response.account.CreateAccountResponse;
import vn.edu.fpt.authentication.dto.response.account.GetAccountResponse;
import vn.edu.fpt.authentication.dto.response.account.LoginResponse;
import vn.edu.fpt.authentication.dto.response.account.ResetPasswordResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface AccountService {

    void init();

    UserDetails getUserByUsername(String username);

    void changeEmail(String id, ChangeEmailRequest request);

    void changePassword(String id, ChangePasswordRequest request);

    CreateAccountResponse createAccount(CreateAccountRequest request);

    LoginResponse login(LoginRequest request);

    LoginResponse refreshToken(RefreshTokenRequest request);

    void resetPassword(ResetPasswordRequest request);

    void addRoleToAccount(String id, AddRoleToAccountRequest request);

    void deleteAccountById(String id);

    void removeRoleFromAccount(String id, String roleId);

    PageableResponse<GetAccountResponse> getAccountByCondition(GetAccountRequest request);
}
