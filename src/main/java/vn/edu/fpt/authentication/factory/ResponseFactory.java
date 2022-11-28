package vn.edu.fpt.authentication.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common._ResponseStatus;
import vn.edu.fpt.authentication.service.DisplayMessageService;

import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:34
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Component
public class ResponseFactory {
    private final DisplayMessageService displayMessageService;

    @Autowired(required = false)
    public ResponseFactory(DisplayMessageService displayMessageService) {
        this.displayMessageService = displayMessageService;
    }

    public <T> ResponseEntity<GeneralResponse<T>> response(T data, ResponseStatusEnum status, String message) {
        _ResponseStatus responseStatus = new _ResponseStatus();
        responseStatus.setStatus(status.getStatus());
        responseStatus.setCode(status.getCode());
        if (Objects.nonNull(displayMessageService)) {
            String displayMessage = displayMessageService.getDisplayMessage(status.getCode());
            if (Objects.nonNull(displayMessage)) {
                responseStatus.setDisplayMessage(displayMessage);
            }
        }
        if (Objects.nonNull(message)) {
            responseStatus.setMessage(message);
        }

        return ResponseEntity
                .status(status.getStatus())
                .body(createResponse(data, responseStatus));
    }

    public <T> ResponseEntity<GeneralResponse<T>> response(T data, ResponseStatusEnum status) {
        return response(data, status, null);
    }

    public <T> ResponseEntity<GeneralResponse<T>> response(T data) {
        return response(data, ResponseStatusEnum.SUCCESS, null);
    }

    public ResponseEntity<GeneralResponse<Object>> response(ResponseStatusEnum status, String message) {
        return response(null, status, message);
    }

    public ResponseEntity<GeneralResponse<Object>> response(ResponseStatusEnum status) {
        return response(null, status, null);
    }

    private <T> GeneralResponse<T> createResponse(T data, _ResponseStatus status) {
        GeneralResponse<T> generalResponse = new GeneralResponse<>();
        generalResponse.setStatus(status);
        generalResponse.setData(data);
        return generalResponse;
    }
}
