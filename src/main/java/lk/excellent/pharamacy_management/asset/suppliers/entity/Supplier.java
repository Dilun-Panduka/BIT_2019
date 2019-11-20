package lk.excellent.pharamacy_management.asset.suppliers.entity;


import lk.excellent.pharamacy_management.asset.commonAsset.entity.SupplierItem;
import lk.excellent.pharamacy_management.asset.process.generalLedger.entity.Ledger;
import lk.excellent.pharamacy_management.asset.process.purchaseOrder.entity.ItemQuantity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String code;

    @Column(unique = true)
    @Size(min = 4, message = "Provide valid name")
    private String name;

    @Size(min = 10, message = "Provide valid name")
    private String address;

    @Size(min = 10, message = "Provide valid phone number")
    private String land;

    @Email(message = "Provide valid email")
    private String email;

    private String contactName;

    @Transient
    private List<Integer> ids;

    @OneToMany(mappedBy = "supplier")
    private List<Ledger> ledgers;


    @OneToMany(mappedBy = "supplier")
    private List<SupplierItem> supplierItems = new ArrayList<>();

    @OneToMany(mappedBy = "supplier")
    private List<ItemQuantity> itemQuantities;

    @Size(min = 10, message = "Provide valid phone number")
    private String contactMobile;

    @Email(message = "Provide valid email")
    private String contactEmail;

    private LocalDate updatedAt;

    private LocalDate createdAt;


}
