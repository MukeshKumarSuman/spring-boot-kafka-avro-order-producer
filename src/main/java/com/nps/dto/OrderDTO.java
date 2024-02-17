package com.nps.dto;

import avro.schema.generated.PickUp;
import avro.schema.generated.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {
    private String id;
    private String name;
    private String nickName;
    private StoreDTO store;
    private List<OrderLineItemDTO> orderLineItems;
    private PickUp pickUp;
    private Status status;
    private LocalDateTime orderedTime;
}
