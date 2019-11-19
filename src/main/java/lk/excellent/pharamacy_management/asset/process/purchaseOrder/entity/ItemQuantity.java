package lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ItemQuantity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    private int quantity;
}
