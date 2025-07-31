package br.vitorsb.delivery_management_api.controller;

import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import br.vitorsb.delivery_management_api.service.DeliveryService;
import br.vitorsb.delivery_management_api.springdoc.DeliveryDoc;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/deliveries")
public class DeliveryController implements DeliveryDoc {

    private final DeliveryService deliveryService;
    private static String BASE_URL = "/api/v1/deliveries/";

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@Valid @RequestBody DeliveryRequest request) {
        DeliveryResponse response = deliveryService.createDelivery(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.deliveryId())
                .toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable Long id, @Valid @RequestBody DeliveryRequest request) {
        DeliveryResponse response = deliveryService.updateDelivery(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id){
        DeliveryResponse response = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

}
