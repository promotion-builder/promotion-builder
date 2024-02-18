package kr.njw.promotionbuilder.event.entity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

@Schema(description = "onDateError: 프로모션 기간 아닐시 오류, onUserConditionError: 유저 조건 불충족시 오류, " +
        "onUserLimitError: 유저 응모 가능 횟수 초과시 오류, onSoldOutError: 경품 소진시 오류")
@Document(collection = "event_block_message_container")
@TypeAlias("EventBlockMessageContainer")
@Data
public class EventBlockErrorMessageContainer {
    @Valid
    private EventBlockMessage onDateError;

    @Valid
    private EventBlockMessage onUserConditionError;

    @Valid
    private EventBlockMessage onUserLimitError;

    @Valid
    private EventBlockMessage onSoldOutError;
}
