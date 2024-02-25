package kr.njw.promotionbuilder.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum BaseResponseStatus {
    SUCCESS(HttpStatus.OK, 200, "요청에 성공하였습니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "입력을 확인해주세요."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, 401, "인증에 실패했습니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, 403, "권한이 없습니다."),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "대상을 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "서버 오류가 발생했습니다."),

    // auth (10xxx)
    LOGIN_ERROR(HttpStatus.UNAUTHORIZED, 10000, "로그인에 실패했습니다."),
    SIGNUP_ERROR_BAD_USER(HttpStatus.UNAUTHORIZED, 10100, "계정 상태가 올바르지 않습니다."),
    SIGNUP_ERROR_BAD_LOCATION(HttpStatus.BAD_REQUEST, 10101, "지역 정보가 올바르지 않습니다."),
    NOT_FOUND_USER(HttpStatus.UNAUTHORIZED, 10102, "유저를 찾을 수 없습니다."),
    DUPLICATED_USER(HttpStatus.BAD_REQUEST, 10103, "중복된 유저가 있습니다."),
    RENEW_TOKEN_ERROR_BAD_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 10200, "리프레시 토큰이 올바르지 않습니다."),
    TOKEN_ERROR_BAD_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 10201, "토큰이 올바르지 않습니다."),
    NEED_NEW_LOGIN(HttpStatus.UNAUTHORIZED, 10202, "로그인이 필요합니다."),

    // sample (98xxx)
    SAMPLE_BAD_NAME(HttpStatus.BAD_REQUEST, 98001, "금지된 키워드가 포함되어 있습니다."),

    // etc (99xxx)
    ETC_MAX_UPLOAD_SIZE_EXCEEDED(HttpStatus.BAD_REQUEST, 99001, "파일 용량이 초과되었습니다."),
    ETC_UNKNOWN(HttpStatus.INTERNAL_SERVER_ERROR, 99999, "알 수 없는 오류입니다.");

    @JsonIgnore
    private final HttpStatus httpStatus;
    private final int code;
    private final String message;

    BaseResponseStatus(HttpStatus httpStatus, int code, String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
