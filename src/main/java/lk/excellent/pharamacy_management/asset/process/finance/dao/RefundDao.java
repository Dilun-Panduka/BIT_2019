package lk.excellent.pharamacy_management.asset.process.finance.dao;

import lk.excellent.pharamacy_management.asset.process.finance.entity.Refund;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RefundDao extends JpaRepository<Refund, Integer> {
}
