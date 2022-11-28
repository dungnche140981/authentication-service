package vn.edu.fpt.authentication.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.controller.RoleController;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.common.SortableRequest;
import vn.edu.fpt.authentication.dto.request.role.*;
import vn.edu.fpt.authentication.dto.response.role.CreateRoleResponse;
import vn.edu.fpt.authentication.dto.response.role.GetRoleResponse;
import vn.edu.fpt.authentication.factory.ResponseFactory;
import vn.edu.fpt.authentication.service.RoleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:25
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RestController
@RequiredArgsConstructor
public class RoleControllerImpl implements RoleController {

    private final RoleService roleService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<CreateRoleResponse>> createRole(CreateRoleRequest request) {
        return responseFactory.response(roleService.createRole(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updateRole(String roleId, UpdateRoleRequest request) {
        roleService.updateRole(roleId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deleteRoleById(String roleId) {
        roleService.deleteRole(roleId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetRoleResponse>>> getRoleByCondition(
            String roleId,
            String roleIdSortBy,
            String roleName,
            String roleNameSortBy,
            String description,
            String descriptionSortBy,
            Boolean isEnable,
            String isEnableSortBy,
            String createdBy,
            String createdBySortBy,
            String createdDateFrom,
            String createdDateTo,
            String createdDateSortBy,
            String lastModifiedBy,
            String lastModifiedBySortBy,
            String lastModifiedDateFrom,
            String lastModifiedDateTo,
            String lastModifiedDateSortBy) {
        List<SortableRequest> sortableRequests = new ArrayList<>();
        if(Objects.nonNull(roleIdSortBy)){
            sortableRequests.add(new SortableRequest("_id", roleIdSortBy));
        }
        if(Objects.nonNull(roleNameSortBy)){
            sortableRequests.add(new SortableRequest("role_name", roleNameSortBy));
        }
        if(Objects.nonNull(descriptionSortBy)){
            sortableRequests.add(new SortableRequest("description", descriptionSortBy));
        }
        if(Objects.nonNull(isEnableSortBy)){
            sortableRequests.add(new SortableRequest("is_enable", isEnableSortBy));
        }
        if(Objects.nonNull(createdBySortBy)){
            sortableRequests.add(new SortableRequest("created_by", createdBySortBy));
        }
        if(Objects.nonNull(createdDateSortBy)){
            sortableRequests.add(new SortableRequest("created_date", createdDateSortBy));
        }
        if(Objects.nonNull(lastModifiedBySortBy)){
            sortableRequests.add(new SortableRequest("last_modified_by", lastModifiedBySortBy));
        }
        if(Objects.nonNull(lastModifiedDateSortBy)){
            sortableRequests.add(new SortableRequest("last_modified_date", lastModifiedDateSortBy));
        }

        GetRoleRequest request = GetRoleRequest.builder()
                .roleId(roleId)
                .roleName(roleName)
                .description(description)
                .isEnable(isEnable)
                .createdBy(createdBy)
                .createdDateFrom(createdDateFrom)
                .createdDateTo(createdDateTo)
                .lastModifiedBy(lastModifiedBy)
                .lastModifiedDateFrom(lastModifiedDateFrom)
                .lastModifiedDateTo(lastModifiedDateTo)
                .sortBy(sortableRequests)
                .build();
        return responseFactory.response(roleService.getRoleByCondition(request));
    }


    @Override
    public ResponseEntity<GeneralResponse<Object>> addPermissionToRole(String roleId, AddPermissionToRoleRequest request) {
        roleService.addPermissionToRole(roleId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> removePermissionFromRole(String roleId, String permissionId) {
        roleService.removePermissionFromRole(roleId, permissionId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

}
