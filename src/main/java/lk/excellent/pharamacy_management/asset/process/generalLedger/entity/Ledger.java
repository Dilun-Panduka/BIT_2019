package lk.excellent.pharamacy_management.asset.process.generalLedger.entity;


import lk.excellent.pharamacy_management.asset.medicine.entity.Medicine;
import lk.excellent.pharamacy_management.asset.process.finance.entity.Invoice;
import lk.excellent.pharamacy_management.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class Ledger extends AuditEntity {

    private Integer quantity;

    private Integer availableQuantity;

    private Integer quantityLimit;

    private BigDecimal salePrice;

    @ManyToOne(fetch = FetchType.EAGER)
    private Medicine medicine;

    @ManyToOne(fetch = FetchType.EAGER)
    private Invoice invoice;




    //todo => medicine

    //quantity price so many thing


}
