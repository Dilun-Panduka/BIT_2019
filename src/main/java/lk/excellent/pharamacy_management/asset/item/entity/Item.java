package lk.excellent.pharamacy_management.asset.item.entity;


import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Category;
import lk.excellent.pharamacy_management.asset.commonAsset.Enum.Status;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Item {

    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    @Column( unique = true )
    @NotNull( message = "This code is already add or enter incorrectly" )
    private String code;

    private String description;

    @NotNull
    private String generic;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String suppliers;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Double cost;
    private Double selling;
    private String soh;

    private LocalDate updatedAt;

    private LocalDate createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGeneric() {
        return generic;
    }

    public void setGeneric(String generic) {
        this.generic = generic;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getSupplier() {
        return suppliers;
    }

    public void setSupplier(String supplier) {
        this.suppliers = supplier;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getSelling() {
        return selling;
    }

    public void setSelling(Double selling) {
        this.selling = selling;
    }

    public String getSoh() {
        return soh;
    }

    public void setSoh(String soh) {
        this.soh = soh;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
