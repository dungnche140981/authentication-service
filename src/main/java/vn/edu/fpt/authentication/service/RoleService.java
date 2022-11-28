package vn.edu.fpt.authentication.service;

import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.role.*;
import vn.edu.fpt.authentication.dto.response.role.CreateRoleResponse;
import vn.edu.fpt.authentication.dto.response.role.GetRoleResponse;
import vn.edu.fpt.authentication.entity._Role;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:23
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public interface RoleService {

    void init();

    CreateRoleResponse createRole(CreateRoleRequest request);

    void updateRole(String roleId, UpdateRoleRequest request);

    void deleteRole(String roleId);

    void addPermissionToRole(String roleId, AddPermissionToRoleRequest permissionId);

    PageableResponse<GetRoleResponse> getRoleByCondition(GetRoleRequest request);

    void removePermissionFromRole(String roleId, String permissionId);


    GetRoleResponse convertToRoleResponse(_Role role);
}
