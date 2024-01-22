package kr.njw.promotionbuilder.event.repository;

import kr.njw.promotionbuilder.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventRepository extends MongoRepository<Event, String> {
    Page<Event> findByUserIdAndDeletedAtNullOrderByCreatedAtDesc(Long userId, Pageable pageable);

    Optional<Event> findByIdAndDeletedAtNull(String id);
}
