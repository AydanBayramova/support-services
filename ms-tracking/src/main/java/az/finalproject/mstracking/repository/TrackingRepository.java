package az.finalproject.mstracking.repository;

import aj.org.objectweb.asm.commons.Remapper;
import az.finalproject.mstracking.entity.TrackingData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TrackingRepository extends JpaRepository<TrackingData, UUID> {


    Optional<TrackingData> findFirstByOrderIdOrderByRecordedAtDesc(UUID orderId);

    List<TrackingData> findAllByOrderIdOrderByRecordedAtAsc(UUID orderId);
}
