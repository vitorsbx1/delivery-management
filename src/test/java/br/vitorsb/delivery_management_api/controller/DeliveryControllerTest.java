package br.vitorsb.delivery_management_api.controller;

import br.vitorsb.delivery_management_api.config.exception.DeliveryNotFoundException;
import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.CustomerRequest;
import br.vitorsb.delivery_management_api.domain.dto.request.AddressRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;

import br.vitorsb.delivery_management_api.service.DeliveryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DeliveryController.class)
class DeliveryControllerTest {

    @Autowired
    private  MockMvc mockMvc;

    @Autowired
    private  ObjectMapper objectMapper;

    @MockBean
    private DeliveryService deliveryService;

    private DeliveryRequest deliveryRequest;
    private DeliveryResponse deliveryResponse;



    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest("Vitor", "13293199666");
        AddressRequest addressRequest = new AddressRequest("98765432", "RJ", "Rio", "Centro", "AV. Peixoto", "500", null);

        deliveryRequest = new DeliveryRequest(
                1,
                LocalDateTime.now().plusDays(2),
                customerRequest,
                addressRequest
        );

        deliveryResponse = new DeliveryResponse(
                1L,
                deliveryRequest.packageQuantity(),
                deliveryRequest.deliveryDeadline(),
                null,
                null
        );
    }

    @Test
    @DisplayName("Shuld return 201 created")
    void shouldCreateDeliveryAndReturn201() throws Exception {
        when(deliveryService.createDelivery(any(DeliveryRequest.class))).thenReturn(deliveryResponse);

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryRequest)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "http://localhost/api/v1/deliveries/" + deliveryResponse.deliveryId()))
                .andExpect(jsonPath("$.deliveryId").value(deliveryResponse.deliveryId()))
                .andExpect(jsonPath("$.packageQuantity").value(deliveryResponse.packageQuantity()));

        verify(deliveryService, times(1)).createDelivery(any(DeliveryRequest.class));
    }

    @Test
    @DisplayName("Should return 400 bad request")
    void shouldReturn400WhenCreatingWithInvalidRequest() throws Exception {

        DeliveryRequest invalidRequest = new DeliveryRequest(
                null,
                LocalDateTime.now().plusDays(2),
                new CustomerRequest("Sem nome", "123"),
                new AddressRequest("123", "MG", "City", "Plana", "Rua", "Num", null)
        );

        mockMvc.perform(post("/api/v1/deliveries")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.packageQuantity").exists());

        verify(deliveryService, never()).createDelivery(any(DeliveryRequest.class));
    }


    @Test
    @DisplayName("Should return 200 after search delivery ")
    void shouldGetDeliveryByIdAndReturn200() throws Exception {
        when(deliveryService.getDeliveryById(1L)).thenReturn(deliveryResponse);

        mockMvc.perform(get("/api/v1/deliveries/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deliveryId").value(deliveryResponse.deliveryId()));

        verify(deliveryService, times(1)).getDeliveryById(1L);
    }

    @Test
    @DisplayName("Should return 404 when delivery not found")
    void shouldReturn404WhenDeliveryNotFound() throws Exception {
        doThrow(new DeliveryNotFoundException("Delivery not found")).when(deliveryService).getDeliveryById(2L);

        mockMvc.perform(get("/api/v1/deliveries/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Delivery not found"));

        verify(deliveryService, times(1)).getDeliveryById(2L);
    }

    @Test
    @DisplayName("Should update delivery and return 200")
    void shouldUpdateDeliveryAndReturn200() throws Exception {
        when(deliveryService.updateDelivery(eq(1L), any(DeliveryRequest.class))).thenReturn(deliveryResponse);

        mockMvc.perform(put("/api/v1/deliveries/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deliveryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deliveryId").value(deliveryResponse.deliveryId()));

        verify(deliveryService, times(1)).updateDelivery(eq(1L), any(DeliveryRequest.class));
    }

    @Test
    @DisplayName("Should return 204 deleting delivery")
    void shouldDeleteDeliveryAndReturn204() throws Exception {
        doNothing().when(deliveryService).deleteDelivery(1L);

        mockMvc.perform(delete("/api/v1/deliveries/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(deliveryService, times(1)).deleteDelivery(1L);
    }
}