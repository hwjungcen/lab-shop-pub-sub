package labshoppubsub.domain;

import labshoppubsub.domain.OrderPlaced;
import labshoppubsub.OrderApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;


@Entity
@Table(name="Order_table")
@Data

public class Order  {


    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
    
    
    private Long id;
    
    
    
    
    
    private String productId;
    
    
    
    
    
    private Integer qty;
    
    
    
    
    
    private String customerId;
    
    
    
    
    
    private Double amount;
    
    
    
    
    
    private String status;

    @PostPersist
    public void onPostPersist(){


        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

    }
    @PrePersist
    public void onPrePersist(){
        // Get request from Inventory
        labshoppubsub.external.Inventory inventory =
           OrderApplication.applicationContext.getBean(labshoppubsub.external.InventoryService.class)
           .getInventory(Long.valueOf(getProductId()));

        if (inventory.getStock() < getQty()) {
            throw new RuntimeException("Out of stock!!!");
        }

    }

    public static OrderRepository repository(){
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(OrderRepository.class);
        return orderRepository;
    }




    public static void updateStatus(DeliveryStarted deliveryStarted){

        /** Example 1:  new item 
        Order order = new Order();
        repository().save(order);

        */

        /** Example 2:  finding and process */
        
        repository().findById(Long.valueOf(deliveryStarted.getOrderId())).ifPresent(order->{
            
            order.setStatus("DeliveryStarted"); // do something
            repository().save(order);


         });
        
    }


}
