package kr.njw.promotionbuilder.event.entity.vo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EventBlockTest {

    @Test
    @DisplayName("이벤트 블럭의 ID는 초기에 null이다")
    void idNull() {
        EventBlock eventBlock = new EventImageBlock();

        assertThat(eventBlock.getId()).isNull();
    }

    @Test
    @DisplayName("이벤트 블럭의 ID를 갱신할 수 있다")
    void issueId() {
        EventBlock eventBlock = new EventImageBlock();
        eventBlock.issueId();
        String oldId = eventBlock.getId();

        eventBlock.issueId();

        assertThat(oldId.length()).isEqualTo(32);
        assertThat(eventBlock.getId().length()).isEqualTo(32);
        assertThat(eventBlock.getId()).isNotEqualTo(oldId);
    }
}
