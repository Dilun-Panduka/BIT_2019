package lk.excellent.pharamacy_management.asset.prescriber.service;


import lk.excellent.pharamacy_management.asset.prescriber.dao.DoctorDao;
import lk.excellent.pharamacy_management.asset.prescriber.entity.Doctor;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DoctorService implements AbstractService<Doctor, Integer> {
    private final DoctorDao doctorDao;


    @Autowired
    public DoctorService(DoctorDao doctorDao) {
        this.doctorDao = doctorDao;
    }



    public List<Doctor> findAll() {
        return doctorDao.findAll();
    }


    public Doctor findById(Integer id) {
        return doctorDao.getOne(id);
    }


    public Doctor persist(Doctor doctor) {
        return doctorDao.save(doctor);
    }


    public boolean delete(Integer id) {
        doctorDao.deleteById(id);
        return false;
    }


    public List<Doctor> search(Doctor doctor) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Doctor> doctorExample = Example.of(doctor, matcher);
        return doctorDao.findAll(doctorExample);
    }
}
