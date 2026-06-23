package az.finalproject.msrating.repository;

import az.finalproject.msrating.entity.Review;
import az.finalproject.msrating.enums.RatingTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ReviewRepository extends JpaRepository<Review, UUID> {

    List<Review> findAllByTargetIdAndTargetType(UUID targetId, RatingTarget targetType);

    boolean existsByOrderIdAndTargetType(UUID orderId, RatingTarget targetType);

    @Query("SELECT AVG(r.score) FROM Review r WHERE r.targetId = :targetId")
    Double getAverageScore(UUID targetId);

    long countByTargetId(UUID targetId);
}
