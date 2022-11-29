package vn.edu.fpt.authentication.dto.request.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 22:10
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateRoleRequest implements Serializable {

    private static final long serialVersionUID = -7342949210860406239L;
    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[a-zA-Z0-9_]{3,20}$")
    private String roleName;
    private String description;
}
