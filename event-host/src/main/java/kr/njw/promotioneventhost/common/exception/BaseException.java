package kr.njw.promotioneventhost.common.exception;

import kr.njw.promotioneventhost.common.dto.BaseResponseStatus;

public class BaseException extends RuntimeException {
    private final BaseResponseStatus status;

    public BaseException(BaseResponseStatus status) {
        this.status = status;
    }

    public BaseResponseStatus getStatus() {
        return this.status;
    }

    public String getMessage() {
        return this.status.getMessage();
    }
}
