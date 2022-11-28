package vn.edu.fpt.authentication.utils;

import java.time.format.DateTimeFormatter;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:27
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
public class CustomDateTimeFormatter {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss SSS");

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
}
