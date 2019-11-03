package lk.excellent.pharamacy_management.asset.suppliers.dao;


import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierDao extends JpaRepository<Supplier, Integer> {
    Supplier findFirstByOrderByIdDesc();

    Supplier findByCode(String code);
}
