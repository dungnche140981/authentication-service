package vn.edu.fpt.authentication.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.permission.CreatePermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.GetPermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.UpdatePermissionRequest;
import vn.edu.fpt.authentication.dto.response.permission.CreatePermissionResponse;
import vn.edu.fpt.authentication.dto.response.permission.GetPermissionResponse;
import vn.edu.fpt.authentication.entity._Permission;
import vn.edu.fpt.authentication.exception.BusinessException;
import vn.edu.fpt.authentication.repository.BaseMongoRepository;
import vn.edu.fpt.authentication.repository.PermissionRepository;
import vn.edu.fpt.authentication.service.PermissionService;
import vn.edu.fpt.authentication.service.UserInfoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final UserInfoService userInfoService;
    private final PermissionRepository permissionRepository;
    private final MongoTemplate mongoTemplate;

    @Override
    public void init() {
        if(permissionRepository.findByPermissionName("readAndWriteAnyProject").isEmpty()){
            _Permission adminPermission = _Permission.builder()
                    .permissionName("readAndWriteAnyProject")
                    .description("Read and write any project")
                    .createdBy(null)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedBy(null)
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            try {
                permissionRepository.save(adminPermission);
                log.info("Init readAndWriteAnyProject permission success");
            }catch (Exception ex){
                throw new BusinessException("Can't init readAndWriteAnyProject permission in database: "+ ex.getMessage());
            }
        }
        if(permissionRepository.findByPermissionName("readAndWriteProject").isEmpty()){
            _Permission managerPermission = _Permission.builder()
                    .permissionName("readAndWriteProject")
                    .description("Read and write project")
                    .createdBy(null)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedBy(null)
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            try {
                permissionRepository.save(managerPermission);
                log.info("Init readAndWriteProject permission success");
            }catch (Exception ex){
                throw new BusinessException("Can't init readAndWriteProject permission in database: "+ ex.getMessage());
            }
        }
        if(permissionRepository.findByPermissionName("readProject").isEmpty()){
            _Permission userPermission = _Permission.builder()
                    .permissionName("readProject")
                    .description("Read only project for user")
                    .createdBy(null)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedBy(null)
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            try {
                permissionRepository.save(userPermission);
                log.info("Init readProject permission success");
            }catch (Exception ex){
                throw new BusinessException("Can't init readProject permission in database: "+ ex.getMessage());
            }
        }
    }

    @Override
    public CreatePermissionResponse createPermission(CreatePermissionRequest request) {
        Optional<_Permission> optionalPermission = permissionRepository.findByPermissionName(request.getPermissionName());
        if(optionalPermission.isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Permission name already in database");
        }
        _Permission permission = _Permission.builder()
                .permissionName(request.getPermissionName())
                .description(request.getDescription())
                .build();
        try {
            permission = permissionRepository.save(permission);
            log.info("Create permission success");
        }catch (Exception ex){
            throw new BusinessException("Can't create permission in database: "+ ex.getMessage());
        }
        return CreatePermissionResponse.builder()
                .permissionId(permission.getPermissionId())
                .build();
    }

    @Override
    public void updatePermission(String permissionId, UpdatePermissionRequest request) {
        _Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Permission ID not exist"));
        Optional<_Permission> permissionOptional = permissionRepository.findByPermissionName(request.getPermissionName());
        if(permissionOptional.isPresent()){
            throw new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Permission name already in database");
        }
        if(Objects.nonNull(request.getPermissionName())){
            permission.setPermissionName(request.getPermissionName());
        }
        if(Objects.nonNull(request.getDescription())){
            permission.setDescription(request.getDescription());
        }
        if(Objects.nonNull(request.getIsEnable())){
            permission.setIsEnable(request.getIsEnable());
        }
        try {
            permissionRepository.save(permission);
            log.info("Update permission success");
        }catch (Exception ex){
            throw new BusinessException("Can't save permission in database: "+ ex.getMessage());
        }

    }

    @Override
    public void deletePermission(String permissionId) {
        _Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new BusinessException(ResponseStatusEnum.BAD_REQUEST, "Permission ID not exist"));
        try {
            permissionRepository.deleteById(permissionId);
            log.info("Delete permission: {} success", permission.getPermissionName());
        }catch (Exception ex){
            throw new BusinessException("Can't delete permission in database: "+ ex.getMessage());
        }
    }

    @Override
    public PageableResponse<GetPermissionResponse> getPermissionByCondition(GetPermissionRequest request) {
        Query query = new Query();
        if(Objects.nonNull(request.getPermissionId())){
            query.addCriteria(Criteria.where("_id").is(request.getPermissionId()));
        }
        if(Objects.nonNull(request.getPermissionName())){
            query.addCriteria(Criteria.where("permission_name").regex(request.getPermissionName()));
        }
        if(Objects.nonNull(request.getDescription())){
            query.addCriteria(Criteria.where("description").regex(request.getDescription()));
        }
        if(Objects.nonNull(request.getEnable())){
            query.addCriteria(Criteria.where("is_enable").is(request.getEnable()));
        }
        BaseMongoRepository.addCriteriaWithAuditable(query, request);

        Long totalElements = mongoTemplate.count(query, _Permission.class);

        BaseMongoRepository.addCriteriaWithPageable(query, request);
        BaseMongoRepository.addCriteriaWithSorted(query, request);

        List<_Permission> permissions = mongoTemplate.find(query, _Permission.class);

        List<GetPermissionResponse> permissionResponses = permissions.stream().map(this::convertToPermissionResponse).collect(Collectors.toList());

        return new PageableResponse<>(request, totalElements, permissionResponses);
    }

    @Override
    public GetPermissionResponse convertToPermissionResponse(_Permission permission) {
        return GetPermissionResponse.builder()
                .permissionId(permission.getPermissionId())
                .permissionName(permission.getPermissionName())
                .description(permission.getDescription())
                .isEnable(permission.getIsEnable())
                .createdBy(permission.getCreatedBy())
                .createdByInfo(userInfoService.getUserInfo(permission.getCreatedBy()))
                .createdDate(permission.getCreatedDate())
                .lastModifiedBy(permission.getLastModifiedBy())
                .lastModifiedByInfo(userInfoService.getUserInfo(permission.getLastModifiedBy()))
                .lastModifiedDate(permission.getLastModifiedDate())
                .build();
    }
}
