package lk.excellent.pharamacy_management.asset.medicine.dao;


import lk.excellent.pharamacy_management.asset.medicine.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineDao extends JpaRepository<Medicine, Long > {
}
