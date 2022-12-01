package vn.edu.fpt.authentication.dto.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 01/12/2022 - 10:40
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CreateAccountActivityEvent implements Serializable {

    private static final long serialVersionUID = 1921172752115994910L;
    private String accountId;
}
