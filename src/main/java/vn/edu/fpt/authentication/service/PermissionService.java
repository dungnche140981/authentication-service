package vn.edu.fpt.authentication.service;

import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.permission.CreatePermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.GetPermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.UpdatePermissionRequest;
import vn.edu.fpt.authentication.dto.response.permission.CreatePermissionResponse;
import vn.edu.fpt.authentication.dto.response.permission.GetPermissionResponse;
import vn.edu.fpt.authentication.entity._Permission;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface PermissionService {

    void init();

    CreatePermissionResponse createPermission(CreatePermissionRequest request);

    void updatePermission(String permissionId, UpdatePermissionRequest request);

    void deletePermission(String permissionId);

    PageableResponse<GetPermissionResponse> getPermissionByCondition(GetPermissionRequest request);

    GetPermissionResponse convertToPermissionResponse(_Permission permission);
}
