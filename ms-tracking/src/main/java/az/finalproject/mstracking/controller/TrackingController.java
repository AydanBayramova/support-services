package az.finalproject.mstracking.controller;

import az.finalproject.mstracking.dto.TrackingResponse;
import az.finalproject.mstracking.service.TrackingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/tracking")
@RequiredArgsConstructor
public class TrackingController {

    private final TrackingService trackingService;


    @GetMapping("/{orderId}/latest")
    public TrackingResponse getLatest(@PathVariable UUID orderId) {
        return trackingService.getLatestLocation(orderId);
    }

    @GetMapping("/{orderId}/route")
    public List<TrackingResponse> getRoute(@PathVariable UUID orderId) {
        return trackingService.getFullRoute(orderId);
    }
}
