package lk.excellent.pharamacy_management.security.dao;


import lk.excellent.pharamacy_management.security.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RoleDao extends JpaRepository<Role, Integer> {
}
