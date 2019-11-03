package lk.excellent.pharamacy_management.asset.suppliers.service;


import lk.excellent.pharamacy_management.asset.suppliers.dao.SupplierDao;
import lk.excellent.pharamacy_management.asset.suppliers.entity.Supplier;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SupplierService implements AbstractService<Supplier, Integer > {
    private final SupplierDao supplierdao;

    @Autowired
    public SupplierService(SupplierDao supplierdao) {
        this.supplierdao = supplierdao;
    }


    public List<Supplier> findAll() {
        return supplierdao.findAll();
    }


    public Supplier findById(Integer id) {
        return supplierdao.getOne(id);
    }


    public Supplier persist(Supplier supplier) {
        return supplierdao.save(supplier);
    }


    public boolean delete(Integer id) {
        supplierdao.deleteById(id);
        return false;
    }

    public Supplier lastSupplier(){
        return supplierdao.findFirstByOrderByIdDesc();
    }

public List<Supplier> search(Supplier supplier) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Supplier> supplierExample = Example.of(supplier, matcher);
        return supplierdao.findAll(supplierExample);
    }
}
