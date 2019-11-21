package lk.excellent.pharamacy_management.asset.process.generalLedger.entity;


import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode()
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(nullable = false)
    private Integer availableQuantity;

    @Column(nullable = false)
    private BigDecimal salePrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Item item;

    @ManyToOne(fetch = FetchType.EAGER)
    private Supplier supplier;

    private LocalDate updatedAt;

    private LocalDate createdAt;

}
