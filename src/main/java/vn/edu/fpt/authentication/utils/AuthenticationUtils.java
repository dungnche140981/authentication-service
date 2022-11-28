package vn.edu.fpt.authentication.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 02/09/2022 - 22:05
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AuthenticationUtils {


    public static String addRolePrefix(String role) {
        return "ROLE_" + role;
    }

    public static String addPermissionPrefix(String role, String permission) {
        return role + ":" + permission;
    }

}
