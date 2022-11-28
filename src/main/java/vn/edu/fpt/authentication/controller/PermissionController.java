package vn.edu.fpt.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common.PageableResponse;
import vn.edu.fpt.authentication.dto.request.permission.CreatePermissionRequest;
import vn.edu.fpt.authentication.dto.request.permission.UpdatePermissionRequest;
import vn.edu.fpt.authentication.dto.response.permission.CreatePermissionResponse;
import vn.edu.fpt.authentication.dto.response.permission.GetPermissionResponse;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 20/11/2022 - 14:25
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@RequestMapping("${app.application-context}/public/api/v1/permissions")
public interface PermissionController {

    @PostMapping("/permission")
    ResponseEntity<GeneralResponse<CreatePermissionResponse>> createPermission(@RequestBody CreatePermissionRequest request);

    @PutMapping("/{permission-id}")
    ResponseEntity<GeneralResponse<Object>> updatePermission(
            @PathVariable("permission-id") String permissionId,
            @RequestBody UpdatePermissionRequest request
    );

    @DeleteMapping("/{permission-id}")
    ResponseEntity<GeneralResponse<Object>> deletePermissionById(@PathVariable("permission-id") String permissionId);

    @GetMapping
    ResponseEntity<GeneralResponse<PageableResponse<GetPermissionResponse>>> getPermissionByCondition(
            @RequestParam(name = "permission-id", required = false) String permissionId,
            @RequestParam(name = "permission-id-sort-by", required = false) String permissionIdSortBy,
            @RequestParam(name = "permission-name", required = false) String permissionName,
            @RequestParam(name = "permission-name-sort-by", required = false) String permissionNameSortBy,
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
}
