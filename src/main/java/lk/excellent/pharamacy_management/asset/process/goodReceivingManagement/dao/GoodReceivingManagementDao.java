package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.dao;


import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodReceivingManagementDao extends JpaRepository<GoodReceivingManagement, Long> {
}
