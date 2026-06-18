package az.finalproject.mstracking.service;

import az.finalproject.mstracking.dto.TrackingResponse;
import az.finalproject.mstracking.entity.TrackingData;
import az.finalproject.mstracking.event.CourierLocationEvent;
import az.finalproject.mstracking.repository.TrackingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingService {

    private final TrackingRepository repository;
    private final RedisTemplate<String, Object> redisTemplate;

    private static final String REDIS_KEY_PREFIX = "tracking:";

    @RabbitListener(queues = "tracking-service-queue")
    public void handleLocationUpdate(CourierLocationEvent event) {
        String redisKey = REDIS_KEY_PREFIX + event.orderId();
        redisTemplate.opsForValue().set(redisKey, event, Duration.ofMinutes(30));
        TrackingData data = TrackingData.builder()
                .orderId(event.orderId())
                .courierId(event.courierId())
                .latitude(event.latitude())
                .longitude(event.longitude())
                .recordedAt(LocalDateTime.now())
                .build();

        repository.save(data);
        log.info("Location saved for Order: {}", event.orderId());
    }


    public TrackingResponse getLatestLocation(UUID orderId) {
        String redisKey = REDIS_KEY_PREFIX + orderId;
        CourierLocationEvent cachedData = (CourierLocationEvent) redisTemplate.opsForValue().get(redisKey);
        if (cachedData != null) {
            log.info("Latest location fetched from REDIS for order: {}", orderId);
            return new TrackingResponse(cachedData.latitude(), cachedData.longitude(), LocalDateTime.now());

        }
        log.info("Redis cache miss, fetching from DATABASE for order: {}", orderId);
        return repository.findFirstByOrderIdOrderByRecordedAtDesc(orderId)
                .map(data
                        -> new TrackingResponse(data.getLatitude(), data.getLongitude(), data.getRecordedAt()))
                .orElseThrow(() -> new RuntimeException("Tracking data not found"));
    }

    public List<TrackingResponse> getFullRoute(UUID orderId) {
        return repository.findAllByOrderIdOrderByRecordedAtAsc(orderId)
                .stream()
                .map(d -> new TrackingResponse(d.getLatitude(), d.getLongitude(), d.getRecordedAt()))
                .toList();
    }
}