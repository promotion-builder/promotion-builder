package kr.njw.promotionbuilder.common.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import kr.njw.promotionbuilder.common.dto.BaseResponse;
import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;
import kr.njw.promotionbuilder.common.exception.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.validation.BindException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Objects;

@Slf4j
@RestControllerAdvice(annotations = RestController.class)
public class RestControllerExceptionAdvice {
    @Autowired
    private Environment environment;

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMethodException(HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        BaseResponse<?> response = new BaseResponse<>(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BaseResponse<?>> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> (error.getField() + ": " +
                        Objects.requireNonNullElse(error.getDefaultMessage(), "")).trim()).toList();
        BaseResponse<?> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), StringUtils.join(errors, ", "), null);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<BaseResponse<?>> handleConstraintViolationException(ConstraintViolationException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        BaseResponse<?> response = new BaseResponse<>(HttpStatus.BAD_REQUEST.value(), e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<BaseResponse<?>> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<BaseResponse<?>> handleBindException(BindException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<BaseResponse<?>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<BaseResponse<?>> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new BaseResponse<>(BaseResponseStatus.BAD_REQUEST));
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<BaseResponse<?>> handleMaxUploadSizeExceededException(MaxUploadSizeExceededException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);

        return ResponseEntity.status(BaseResponseStatus.ETC_MAX_UPLOAD_SIZE_EXCEEDED.getHttpStatus())
                .body(new BaseResponse<>(BaseResponseStatus.ETC_MAX_UPLOAD_SIZE_EXCEEDED));
    }

    @ExceptionHandler(ClientAbortException.class)
    public void handleClientAbortException(ClientAbortException e, HttpServletRequest request) {
        this.logException(Level.INFO, e, request);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse<?>> handleBaseException(BaseException e, HttpServletRequest request) {
        this.logException(e.getStatus().getHttpStatus().is5xxServerError() ? Level.ERROR : Level.INFO, e, request);

        return ResponseEntity.status(e.getStatus().getHttpStatus()).body(new BaseResponse<>(e.getStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<?>> handleException(Exception e, HttpServletRequest request) {
        this.logException(Level.ERROR, e, request);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR));
    }

    private void logException(Level level, Exception e, HttpServletRequest request) {
        if (level == Level.ERROR || !this.environment.acceptsProfiles(Profiles.of("prod"))) {
            log.atLevel(level).log("[{}] Error Detail", e.hashCode(), e);
        }

        log.atLevel(Level.INFO).log("[{}] Method: {} | URL: {} | Message: {}",
                e.hashCode(), request.getMethod(), UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request)).build().toUriString(), e.getMessage());
    }
}
