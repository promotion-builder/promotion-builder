package kr.njw.promotionbuilder.sample.repository;

import kr.njw.promotionbuilder.sample.entity.Sample;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SampleRepository extends JpaRepository<Sample, Long> {
    Page<Sample> findByDeletedAtNullOrderByIdDesc(Pageable pageable);

    Optional<Sample> findFirstByNameAndDeletedAtNull(String name);
}
