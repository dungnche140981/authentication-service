package vn.edu.fpt.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.role.AddPermissionToRoleRequest;
import vn.edu.fpt.authentication.dto.request.role.CreateRoleRequest;
import vn.edu.fpt.authentication.dto.request.role.UpdateRoleRequest;
import vn.edu.fpt.authentication.dto.response.role.CreateRoleResponse;
import vn.edu.fpt.authentication.dto.response.role.GetRoleResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:24
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/roles")
public interface RoleController {

    @PostMapping("/role")
    ResponseEntity<GeneralResponse<CreateRoleResponse>> createRole(@RequestBody CreateRoleRequest request);

    @PutMapping("/{role-id}")
    ResponseEntity<GeneralResponse<Object>> updateRole(
            @PathVariable("role-id") String roleId,
            @RequestBody UpdateRoleRequest request
    );

    @DeleteMapping("/{role-id}")
    ResponseEntity<GeneralResponse<Object>> deleteRoleById(@PathVariable("role-id") String roleId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetRoleResponse>>> getRoleByCondition(
            @RequestParam(name = "role-id", required = false) String roleId,
            @RequestParam(name = "role-id-sort-by", required = false) String roleIdSortBy,
            @RequestParam(name = "role-name", required = false) String roleName,
            @RequestParam(name = "role-name-sort-by", required = false) String roleNameSortBy,
            @RequestParam(name = "description", required = false) String description,
            @RequestParam(name = "description-sort-by", required = false) String descriptionSortBy,
            @RequestParam(name = "is-enable", required = false) Boolean isEnable,
            @RequestParam(name = "is-enable-sort-by", required = false) String isEnableSortBy,
            @RequestParam(name = "created-by", required = false) String createdBy,
            @RequestParam(name = "created-by-sort-by", required = false) String createdBySortBy,
            @RequestParam(name = "created-date-from", required = false) String createdDateFrom,
            @RequestParam(name = "created-date-to", required = false) String createdDateTo,
            @RequestParam(name = "created-date-sort-by", required = false) String createdDateSortBy,
            @RequestParam(name = "last-modified-by", required = false) String lastModifiedBy,
            @RequestParam(name = "last-modified-by-sort-by", required = false) String lastModifiedBySortBy,
            @RequestParam(name = "last-modified-date-from", required = false) String lastModifiedDateFrom,
            @RequestParam(name = "last-modified-date-to", required = false) String lastModifiedDateTo,
            @RequestParam(name = "last-modified-date-sort-by", required = false) String lastModifiedDateSortBy
            );

    @PostMapping("/{role-id}/permission")
    ResponseEntity<GeneralResponse<Object>> addPermissionToRole(
            @PathVariable("role-id") String roleId,
            @RequestBody AddPermissionToRoleRequest request
    );

    @DeleteMapping("/{role-id}/permissions/{permission-id}")
    ResponseEntity<GeneralResponse<Object>> removePermissionFromRole(
            @PathVariable("role-id") String roleId,
            @PathVariable("permission-id") String permissionId
    );
}
