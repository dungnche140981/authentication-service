package vn.edu.fpt.authentication.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/11/2022 - 22:25
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Getter
@NoArgsConstructor
public enum ActivityType {

    COMMENT("COMMENT"),
    HISTORY("HISTORY");

    private String type;

    ActivityType(String type) {
        this.type = type;
    }
}
