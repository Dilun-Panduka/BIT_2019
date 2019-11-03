package lk.excellent.pharamacy_management.asset.medicine.service;


import lk.excellent.pharamacy_management.asset.medicine.dao.GenericNameDao;
import lk.excellent.pharamacy_management.asset.medicine.entity.GenericName;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenericNameService implements AbstractService<GenericName, Long > {
    private final GenericNameDao genericNameDao;
@Autowired
    public GenericNameService(GenericNameDao genericNameDao) {
        this.genericNameDao = genericNameDao;
    }

    @Override
    public List< GenericName > findAll() {
        return genericNameDao.findAll();
    }

    @Override
    public GenericName findById(Long id) {
        return null;
    }

    @Override
    public GenericName persist(GenericName genericName) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    @Override
    public List< GenericName > search(GenericName genericName) {
        return null;
    }
}
