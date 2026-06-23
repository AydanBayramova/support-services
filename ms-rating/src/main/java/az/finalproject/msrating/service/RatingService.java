package az.finalproject.msrating.service;

import az.finalproject.msrating.client.OrderClient;
import az.finalproject.msrating.dto.RatingRequest;
import az.finalproject.msrating.dto.RatingResponse;
import az.finalproject.msrating.entity.Review;
import az.finalproject.msrating.event.RatingUpdatedEvent;
import az.finalproject.msrating.exception.IllegalArgumentException;
import az.finalproject.msrating.exception.RatingAlreadyExistsException;
import az.finalproject.msrating.exception.UnauthorizedException;
import az.finalproject.msrating.mapper.ReviewMapper;
import az.finalproject.msrating.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@RequiredArgsConstructor
@Slf4j
public class RatingService {
    private final ReviewRepository reviewRepository;
    private final ReviewMapper mapper;
    private final OrderClient orderClient;

    public void addRating(RatingRequest request) {

        var order = orderClient.getOrderById(request.orderId());

        if (!order.customerId().equals(request.customerId())) {
            throw new UnauthorizedException("You can only rate your own orders!");
        }
        if (!"DELIVERED".equals(order.status())) {
            throw new IllegalStateException("You can only rate delivered orders!");
        }
        if (reviewRepository.existsByOrderIdAndTargetType(request.orderId(), request.targetType())) {
            throw new RatingAlreadyExistsException("You have already rated this " + request.targetType());
        }
        if (request.score() < 1 || request.score() > 5) {
            throw new IllegalArgumentException("Score must be between 1 and 5");
        }
        Review review = mapper.toEntity(request);
        reviewRepository.save(review);
        log.info("New rating added for {}: {}", request.targetType(), request.targetId());
    }

    public RatingResponse getTargetRating(UUID targetId) {
        Double averageScore = reviewRepository.getAverageScore(targetId);
        long countComment = reviewRepository.countByTargetId(targetId);

        return new RatingResponse(
                targetId,
                averageScore != null ? averageScore : 0.0,
                countComment
        );
    }
}