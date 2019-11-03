package lk.excellent.pharamacy_management.asset.medicine.entity;


import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class GenericName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "genericName")
    private List<Medicine> medicines;
}
