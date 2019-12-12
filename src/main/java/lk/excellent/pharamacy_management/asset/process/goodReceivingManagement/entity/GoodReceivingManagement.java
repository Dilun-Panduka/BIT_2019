package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.Enum.GRNStatus;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.PurchaseOrder;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(value = {"updatedDate", "remarks"}, allowGetters = true)
public class GoodReceivingManagement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    private GRNStatus grnStatus;

    @ManyToOne
    private Supplier supplier;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate createdDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate updatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receivedDate;

    private String remarks;

    private BigDecimal total;

    @ManyToOne
    private PurchaseOrder purchaseOrder;

    @OneToMany( cascade = CascadeType.PERSIST, mappedBy = "goodReceivingManagement")
    private List<GrnQuantity> grnQuantities;

}



