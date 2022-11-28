package vn.edu.fpt.authentication.controller.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.controller.PermissionController;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.common.SortableRequest;
import vn.edu.fpt.authentication.dto.request.permission.CreatePermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.GetPermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.UpdatePermissionRequest;
import vn.edu.fpt.authentication.dto.response.permission.CreatePermissionResponse;
import vn.edu.fpt.authentication.dto.response.permission.GetPermissionResponse;
import vn.edu.fpt.authentication.factory.ResponseFactory;
import vn.edu.fpt.authentication.service.PermissionService;

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
public class PermissionControllerImpl implements PermissionController {

    private final PermissionService permissionService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<GeneralResponse<CreatePermissionResponse>> createPermission(CreatePermissionRequest request) {
        return responseFactory.response(permissionService.createPermission(request), ResponseStatusEnum.CREATED);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> updatePermission(String permissionId, UpdatePermissionRequest request) {
        permissionService.updatePermission(permissionId, request);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<Object>> deletePermissionById(String permissionId) {
        permissionService.deletePermission(permissionId);
        return responseFactory.response(ResponseStatusEnum.SUCCESS);
    }

    @Override
    public ResponseEntity<GeneralResponse<PageableResponse<GetPermissionResponse>>> getPermissionByCondition(
            String permissionId,
            String permissionIdSortBy,
            String permissionName,
            String permissionNameSortBy,
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
        if(Objects.nonNull(permissionId)){
            sortableRequests.add(new SortableRequest("_id", permissionIdSortBy));
        }
        if(Objects.nonNull(permissionName)){
            sortableRequests.add(new SortableRequest("role_name", permissionNameSortBy));
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

        GetPermissionRequest request = GetPermissionRequest.builder()
                .permissionId(permissionId)
                .permissionName(permissionName)
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
        return responseFactory.response(permissionService.getPermissionByCondition(request));
    }
}
