package labshoppubsub.domain;

import labshoppubsub.domain.*;
import labshoppubsub.infra.AbstractEvent;
import lombok.*;
import java.util.*;
@Data
@ToString
public class DeliveryStarted extends AbstractEvent {

    private Long id;
    private String customerId;
    private String orderId;
}


