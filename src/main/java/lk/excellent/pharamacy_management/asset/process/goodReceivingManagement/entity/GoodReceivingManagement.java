package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity;


import lk.excellent.pharamacy_management.util.audit.AuditEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode( callSuper = true )
public class GoodReceivingManagement extends AuditEntity {

    private String quantity;

    private BigDecimal itemPrice;


    //todo-> need more thing



//todo -> supplier medicine


}



