package vn.edu.fpt.authentication.dto.request.permission;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.authentication.dto.common.AuditableRequest;
import vn.edu.fpt.authentication.utils.RequestDataUtils;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 19:58
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@SuperBuilder
public class GetPermissionRequest extends AuditableRequest implements Serializable {

    private static final long serialVersionUID = -7606758838431794635L;
    private String permissionId;
    private String permissionName;
    private String description;
    private Boolean isEnable;

    public String getPermissionId() {
        return permissionId;
    }

    public String getPermissionName() {
        return RequestDataUtils.convertSearchableData(permissionName);
    }

    public String getDescription() {
        return RequestDataUtils.convertSearchableData(description);
    }

    public Boolean getEnable() {
        return RequestDataUtils.convertSearchableData(isEnable);
    }
}
