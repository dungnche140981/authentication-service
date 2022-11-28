package vn.edu.fpt.authentication.constant;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Charity System
 * @created : 05/11/2022 - 17:05
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Getter
@NoArgsConstructor
public enum ProjectStatus {

    DONE("DONE"),
    IN_PROGRESS("IN PROGRESS"),
    TO_DO("TO DO");

    private String status;

    ProjectStatus(String status) {
        this.status = status;
    }
}
