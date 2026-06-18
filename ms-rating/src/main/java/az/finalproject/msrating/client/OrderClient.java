package az.finalproject.msrating.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "ms-order")
public interface OrderClient {
    @GetMapping("/{id}")
    OrderResponse getOrderById(@PathVariable UUID id);
}