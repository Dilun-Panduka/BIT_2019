package lk.excellent.pharamacy_management.asset.prescriber.dao;


import lk.excellent.pharamacy_management.asset.prescriber.entity.Consultations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationDao extends JpaRepository<Consultations, Integer> {
}
