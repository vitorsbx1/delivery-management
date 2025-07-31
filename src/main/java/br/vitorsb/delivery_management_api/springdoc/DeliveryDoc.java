package br.vitorsb.delivery_management_api.springdoc;

import br.vitorsb.delivery_management_api.domain.dto.request.DeliveryRequest;
import br.vitorsb.delivery_management_api.domain.dto.response.DeliveryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@Tag(name = "Delivery Management", description = "Endpoints for managing deliveries")
public interface DeliveryDoc {


    @Operation(summary = "Create Delivery", description = "Create a new delivery with the specified details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Delivery registred successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request data"),
            @ApiResponse(responseCode = "500", description = "Internal server error during delivery creation")
    })
    public ResponseEntity<DeliveryResponse> createDelivery(

            @RequestBody(
                    description = "Data for the new delivery.",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryRequest.class),
                            examples = @ExampleObject(
                                    name = "Example of Delivery Request",
                                    value = "{\"packageQuantity\": 1, \"deliveryDeadline\": \"2025-07-31T15:30:00\", \"customer\": {\"name\": \"Igor Duarte\", \"cpf\": \"8888888888\"}, \"addressDelivery\": {\"cep\": \"38400100\", \"uf\": \"MG\", \"city\": \"Uberlândia\", \"neighborhood\": \"São Jorge\", \"street\": \"Rua das Rosas\", \"number\": \"456\", \"complement\": \"Apto 101\"}}"
                            ))
            )
            DeliveryRequest request
    );

    @Operation(
            summary = "Update Delivery",
            description = "Update existing delivery with ID "
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(example = "{\"deliveryDeadline\":\"Delivery deadline is required\"}"))),
            @ApiResponse(responseCode = "404", description = "Delivery not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during delivery update")
    })
    public ResponseEntity<DeliveryResponse> updateDelivery(
            @Parameter(description = "ID Delivery to update", required = true, example = "1")
            Long id,
            @RequestBody(
                    description = "Data for updating the delivery.",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryRequest.class),
                            examples = @ExampleObject(
                                    name = "Example of delivery request update",
                                    value = "{\"packageQuantity\": 3, \"deliveryDeadline\": \"2026-01-15T10:00:00\", \"customer\": {\"name\": \"Vitor Sampaio\", \"cpf\": \"999999999\"}, \"addressDelivery\": {\"cep\": \"38400200\", \"uf\": \"MG\", \"city\": \"Uberlândia\", \"neighborhood\": \"Martins\", \"street\": \"Av. Rondon\", \"number\": \"789\"}}"
                            ))
            )
            DeliveryRequest request
    );

    @Operation(
            summary = "Get delivery by id",
            description = "Return delivery by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Delivery find",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = DeliveryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Delivery not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during search delivery")
    })
    public ResponseEntity<DeliveryResponse> getDeliveryById(
            @Parameter(description = "Id delivery", required = true, example = "1")
            Long id
    );

    @Operation(
            summary = "Delete delivery",
            description = "Delete delivery by id"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Delivery deleted success"),
            @ApiResponse(responseCode = "404", description = "Delivery not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error during delivery delete")
    })
    public ResponseEntity<Void> deleteDelivery(
            @Parameter(description = "Id delivery", required = true, example = "1")
            Long id
    );

}
