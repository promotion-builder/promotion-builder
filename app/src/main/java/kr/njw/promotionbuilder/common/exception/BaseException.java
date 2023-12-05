package kr.njw.promotionbuilder.common.exception;

import kr.njw.promotionbuilder.common.dto.BaseResponseStatus;

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
