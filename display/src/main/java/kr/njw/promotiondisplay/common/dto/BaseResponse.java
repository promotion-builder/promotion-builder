package kr.njw.promotiondisplay.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"code", "message", "result"})
public class BaseResponse<T> {
    // @Schema(example = "200")
    private int code;
    // @Schema(example = "요청에 성공하였습니다.")
    private String message;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public BaseResponse(T result) {
        this.code = BaseResponseStatus.SUCCESS.getCode();
        this.message = BaseResponseStatus.SUCCESS.getMessage();
        this.result = result;
    }

    public BaseResponse(BaseResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
    }

    public BaseResponse(T result, BaseResponseStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.result = result;
    }
}
