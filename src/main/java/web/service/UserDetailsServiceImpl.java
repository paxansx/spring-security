package web.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import org.springframework.stereotype.Service;
import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;


@Service
public class UserDetailsServiceImpl implements UserDetailsService,UserService {
    private final UserDao userDao;

    public UserDetailsServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDao.getUserByName(s);
    }
    @Override
    @Transactional
    public void addUser(User user) {
        userDao.addUser(user);

    }

    @Override
    @Transactional
    public void updateUser(User user) {
        userDao.updateUser(user);
    }

    @Override
    @Transactional
    public void removeUser(long id) {
        userDao.removeUser(id);
    }

    @Override

    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override

    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public List<Role> getAllRole() {
        return userDao.getAllRole();
    }

    @Override
    public Set<Role> getSetRole(String[] roles) {
        return userDao.getSetRole(roles);
    }

}
