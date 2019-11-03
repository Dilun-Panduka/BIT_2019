package lk.excellent.pharamacy_management.asset.prescriber.dao;


import lk.excellent.pharamacy_management.asset.prescriber.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DoctorDao extends JpaRepository<Doctor, Integer> {
}
