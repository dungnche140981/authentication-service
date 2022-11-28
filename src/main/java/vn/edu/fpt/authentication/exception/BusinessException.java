package vn.edu.fpt.authentication.exception;

import lombok.Getter;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:52
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@Getter
public class
BusinessException extends RuntimeException{

    private static final long serialVersionUID = -7568326660433031236L;
    private String message;
    private ResponseStatusEnum status;

    public BusinessException(String message){
        this.status = ResponseStatusEnum.INTERNAL_SERVER_ERROR;
        this.message = message;
    }

    public BusinessException(ResponseStatusEnum status, String message){
        this.status = status;
        this.message = message;
    }
}
