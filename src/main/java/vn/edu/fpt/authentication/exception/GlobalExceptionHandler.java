package vn.edu.fpt.authentication.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.LazyLoadingException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.edu.fpt.authentication.constant.ResponseStatusEnum;
import vn.edu.fpt.authentication.dto.common.GeneralResponse;
import vn.edu.fpt.authentication.dto.common._ResponseStatus;
import vn.edu.fpt.authentication.factory.ResponseFactory;
import vn.edu.fpt.authentication.service.DisplayMessageService;

import java.util.Objects;

/**
 * @author : Hoang Lam
 * @product : Charity Management System
 * @project : Authentication Service
 * @created : 30/08/2022 - 19:52
 * @contact : 0834481768 - hoang.harley.work@gmail.com
 **/
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ResponseFactory responseFactory;
    private DisplayMessageService displayMessageService;

    @Autowired
    public GlobalExceptionHandler(ResponseFactory responseFactory, DisplayMessageService displayMessageService) {
        this.responseFactory = responseFactory;
        this.displayMessageService = displayMessageService;
    }


    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<GeneralResponse<Object>> handleBusinessException(BusinessException ex){
        if(Objects.isNull(ex)){
            return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "BusinessException missing handler details");
        }
        log.error("BusinessException: {}", ex.getMessage());

        if(Objects.nonNull(ex.getMessage())) {
            return responseFactory.response(ex.getStatus(), ex.getMessage());
        }else{
            return responseFactory.response(ex.getStatus());
        }
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<GeneralResponse<Object>> handleAccessDeniedException(AccessDeniedException ex){
        if(Objects.isNull(ex)){
            return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "AccessDeniedException missing handler details");
        }
        log.error("AccessDeniedException: {}", ex.getMessage());

        if(Objects.nonNull(ex.getMessage())) {
            return responseFactory.response(ResponseStatusEnum.FORBIDDEN, ex.getMessage());
        }else{
            return responseFactory.response(ResponseStatusEnum.FORBIDDEN);
        }
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<GeneralResponse<Object>> handleAuthenticationException(AuthenticationException ex){
        if(Objects.isNull(ex)){
            return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "AuthenticationException missing handler details");
        }
        log.error("UsernameNotFoundException: {}", ex.getMessage());

        if(Objects.nonNull(ex.getMessage())) {
            return responseFactory.response(ResponseStatusEnum.UNAUTHORIZED, ex.getMessage());
        }else{
            return responseFactory.response(ResponseStatusEnum.UNAUTHORIZED);
        }
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<GeneralResponse<Object>> handleException(Exception ex){
        if(Objects.isNull(ex)){
            return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Exception missing handler details");
        }
        log.error("Exception: {}", ex.getMessage());

        return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @ExceptionHandler(LazyLoadingException.class)
    public ResponseEntity<GeneralResponse<Object>> handleLazyLoadingException(LazyLoadingException ex){
        if(Objects.isNull(ex)){
            return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, "Exception missing handler details");
        }
        log.error("Exception: {}", ex.getMessage());

        return responseFactory.response(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("HttpRequestMethodNotSupportedException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, "Request method not supported: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("HttpMediaTypeNotSupportedException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, "Media type not supported: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("HttpMediaTypeNotAcceptableException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, "Media type not acceptable: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MissingPathVariableException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, "Missing path variable: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MissingServletRequestParameterException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST,"Missing servlet request param: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("ServletRequestBindingException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleConversionNotSupported(ConversionNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("ConversionNotSupportedException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("TypeMismatchException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("HttpMessageNotReadableException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("HttpMessageNotWritableException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MethodArgumentNotValidException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, "Method argument not valid: "+ ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(MissingServletRequestPartException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("MissingServletRequestPartException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.BAD_REQUEST, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("BindException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("NoHandlerFoundException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("AsyncRequestTimeoutException: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.REQUEST_TIMEOUT, ex.getMessage());
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        log.error("ExceptionInternal: {}", ex.getMessage());
        logDetails(headers, status, request);

        return generateResponseEntity(ResponseStatusEnum.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    private void logDetails(HttpHeaders headers, HttpStatus status, WebRequest request){
        log.error("HttpHeaders: {}", headers);
        log.error("HttpStatus: {}", status);
        log.error("WebRequest: {}", request);
    }

    private ResponseEntity<Object> generateResponseEntity(ResponseStatusEnum status, String message){
        _ResponseStatus responseStatus = new _ResponseStatus(status.getStatus(), status.getCode());
        responseStatus.setDisplayMessage(displayMessageService.getDisplayMessage(status.getCode()));
        responseStatus.setMessage(message);
        return ResponseEntity.status(status.getStatus()).body(responseStatus);
    }
}
