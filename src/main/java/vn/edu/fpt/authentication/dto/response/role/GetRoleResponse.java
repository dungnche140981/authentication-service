package vn.edu.fpt.authentication.dto.response.role;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.authentication.dto.common.AuditableResponse;
import vn.edu.fpt.authentication.dto.response.permission.GetPermissionResponse;

import java.io.Serializable;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 12:04
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonPropertyOrder({"roleId", "roleName", "description", "isEnable", "status", "permissions"})
public class GetRoleResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = 1509654297902859049L;
    private String roleId;
    private String roleName;
    private String description;
    private Boolean isEnable;
    private List<GetPermissionResponse> permissions;
}
