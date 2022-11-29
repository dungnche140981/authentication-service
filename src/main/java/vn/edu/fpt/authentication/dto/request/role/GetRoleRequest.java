package vn.edu.fpt.authentication.dto.request.role;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.authentication.dto.common.AuditableRequest;
import vn.edu.fpt.authentication.utils.RequestDataUtils;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 19:59
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetRoleRequest extends AuditableRequest {

    private static final long serialVersionUID = 636570691925831402L;
    private String roleId;
    private String roleName;
    private String description;
    private Boolean isEnable;

    public String getRoleId() {
        return roleId;
    }

    public String getRoleName() {
        return RequestDataUtils.convertSearchableData(roleName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }

    public Boolean getIsEnable() {
        return RequestDataUtils.convertSearchableData(isEnable);
    }
}
