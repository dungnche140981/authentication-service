package vn.edu.fpt.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 06/09/2022 - 18:27
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppRegex {

    public static final String PARAM_REGEX = "^%%([a-zA-Z0-9_]*)%%$";
    public static final String PARAM_INFIX = "%%";
}
