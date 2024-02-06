package kr.njw.promotionbuilder.event.entity;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

@ExtendWith(MockitoExtension.class)
class EventTest {
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        this.now = LocalDateTime.now();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("생성시 삭제 상태가 아니어야 한다")
    void notDeletedWhenBuild() {
        Event event = Event.builder().build();

        assertThat(event.getDeletedAt()).isNull();
    }

    @Test
    @DisplayName("삭제를 하면 삭제 상태가 되어야 한다")
    void delete() {
        Event event = Event.builder().build();

        event.delete();

        assertThat(event.getDeletedAt()).isCloseTo(this.now, within(1, ChronoUnit.MINUTES));
    }
}
