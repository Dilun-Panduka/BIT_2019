package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity;


import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.Enum.GRNStatus;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GoodReceivingManagement extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private GRNStatus grnStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    private Item item;

    private String quantity;

    @Column(unique = true)
    private BigDecimal selPrice;

    @Column(unique = true)
    private BigDecimal cost;

    private LocalDate createdDate;

    private LocalDate receivedDate;

    private String remarks;

    @ManyToOne(cascade = CascadeType.ALL)
    private PurchaseOrder purchaseOrder;

}



