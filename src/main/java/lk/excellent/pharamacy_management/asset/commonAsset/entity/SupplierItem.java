package lk.excellent.pharamacy_management.asset.commonAsset.entity;

import lk.excellent.pharamacy_management.asset.item.entity.Item;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class SupplierItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Supplier supplier;

    @ManyToOne
    private Item item;
}
