package lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.service;

import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.dao.GoodReceivingManagementDao;
import lk.excellent.pharamacy_management.asset.process.goodReceivingManagement.entity.GoodReceivingManagement;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodReceivingManagementService implements AbstractService<GoodReceivingManagement, Integer> {
    private final GoodReceivingManagementDao goodReceivingManagementDao;

    @Autowired
    public GoodReceivingManagementService(GoodReceivingManagementDao goodReceivingManagementDao) {
        this.goodReceivingManagementDao = goodReceivingManagementDao;
    }

    @Override
    public List<GoodReceivingManagement> findAll() {
        return goodReceivingManagementDao.findAll();
    }

    @Override
    public GoodReceivingManagement findById(Integer id) {
        return goodReceivingManagementDao.getOne(id);
    }

    @Override
    public GoodReceivingManagement persist(GoodReceivingManagement goodReceivingManagement) {
        return goodReceivingManagementDao.save(goodReceivingManagement);
    }

    @Override
    public boolean delete(Integer id) {
        goodReceivingManagementDao.deleteById(id);
        return false;
    }

    @Override
    public List<GoodReceivingManagement> search(GoodReceivingManagement goodReceivingManagement) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<GoodReceivingManagement> goodReceivingManagementExample = Example.of(goodReceivingManagement, matcher);
        return goodReceivingManagementDao.findAll(goodReceivingManagementExample);
    }

    public GoodReceivingManagement lastGrn(){
        return goodReceivingManagementDao.findFirstByOrderByIdDesc();
    }
}
