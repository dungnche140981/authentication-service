package vn.edu.fpt.authentication.dto.response.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.edu.fpt.authentication.config.datetime.CustomDateTimeSerializer;
import vn.edu.fpt.authentication.dto.response.role.GetRoleResponse;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 31/08/2022 - 02:22
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetAccountResponse implements Serializable {

    private static final long serialVersionUID = -7918752112103180312L;
    private String accountId;
    private String username;
    private String fullName;
    private String email;
    private List<GetRoleResponse> roles;
    private Boolean isEnable;
    private Boolean isNonExpired;
    private Boolean isNonLocked;
    private Boolean isCredentialNonExpired;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime createdDate;
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    private LocalDateTime lastModifiedDate;

}
