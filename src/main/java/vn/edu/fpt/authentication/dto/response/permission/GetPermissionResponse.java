package vn.edu.fpt.authentication.dto.response.permission;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;
import lombok.experimental.SuperBuilder;
import vn.edu.fpt.authentication.dto.common.AuditableResponse;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 12:04
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@JsonPropertyOrder({"permissionId", "permissionName", "isEnable", "description"})
public class GetPermissionResponse extends AuditableResponse implements Serializable {

    private static final long serialVersionUID = -2983397104975710917L;
    private String permissionId;
    private String permissionName;
    private Boolean isEnable;
    private String description;
}
