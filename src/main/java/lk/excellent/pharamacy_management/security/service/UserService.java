package lk.excellent.pharamacy_management.security.service;


import lk.excellent.pharamacy_management.security.dao.UserDao;
import lk.excellent.pharamacy_management.security.entity.User;
import lk.excellent.pharamacy_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService implements AbstractService<User, Integer> {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder, UserDao userDao) {
        this.passwordEncoder = passwordEncoder;
        this.userDao = userDao;
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public User findById(Integer id) {
        return (User) userDao.getOne(id);
    }

    public User persist(User user) {
        user.setEnabled(true);

        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        } else {
            user.setPassword(userDao.getOne(user.getId()).getPassword());
        }

        user.setUsername(user.getUsername().toLowerCase());
        return userDao.save(user);
    }

    public boolean delete(Integer id) {
        userDao.deleteById(id);
        return false;
    }


    public List<User> search(User user) {
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreCase().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<User> userExample = Example.of(user, matcher);
        return userDao.findAll(userExample);
    }

    public Integer findByEmployeeId(Integer id) {
        return userDao.findByEmployeeId(id);
    }

    public Integer findByUserIdByUserName(String userName) {
        return userDao.findUserIdByUserName(userName);
    }

    public User findByUserName(String name) {
        return userDao.findByUsername(name);
    }
}
