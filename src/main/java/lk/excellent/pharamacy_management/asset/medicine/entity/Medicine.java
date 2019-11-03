package lk.excellent.pharamacy_management.asset.medicine.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Medicine {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( unique = true )
    private Integer id;

    private String name;

    @ManyToOne(cascade = CascadeType.ALL)
    private GenericName genericName;

    @ManyToOne
    private Pharmacopoeia pharmacopoeia;
//todo -> need to more details to medicine registration

}
