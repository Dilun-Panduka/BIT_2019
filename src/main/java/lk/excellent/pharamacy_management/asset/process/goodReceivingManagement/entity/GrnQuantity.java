package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity;

import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class GrnQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int requestedQty;

    private int receivedQuantity;

    private BigDecimal amount;

    @ManyToOne
    private Item item;

    @ManyToOne
    private GoodReceivingManagement goodReceivingManagement;
}
