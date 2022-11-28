package vn.edu.fpt.authentication.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:17
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public enum ResponseStatusEnum implements ResponseStatusOperation {
    SUCCESS("SUCCESS", HttpStatus.OK),
    CREATED("CREATED", HttpStatus.CREATED),
    ACCEPTED("ACCEPTED", HttpStatus.ACCEPTED),
    FORBIDDEN("FORBIDDEN", HttpStatus.FORBIDDEN),
    UNAUTHORIZED("UNAUTHORIZED", HttpStatus.UNAUTHORIZED),
    GATEWAY_TIMEOUT("GATEWAY_TIMEOUT", HttpStatus.GATEWAY_TIMEOUT),
    REQUEST_TIMEOUT("REQUEST_TIMEOUT", HttpStatus.REQUEST_TIMEOUT),
    NOT_FOUND("NOT_FOUND", HttpStatus.NOT_FOUND),
    BAD_REQUEST("BAD_REQUEST", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR("VALIDATION_ERROR", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),

    ;
    private String code;
    private Integer status;

    ResponseStatusEnum(String code, HttpStatus status) {
        this.code = code;
        this.status = status.value();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public Integer getStatus() {
        return this.status;
    }
}

