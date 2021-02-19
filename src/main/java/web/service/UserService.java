package web.service;

import web.model.Role;
import web.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public void addUser(User user);
    public void updateUser(User user);
    public void removeUser(long id);
    public User getUserById(long id);
    public List<User> getAllUser();
    public  List<Role> getAllRole();
    public Set<Role> getSetRole(String[] roles);
    Object loadUserByUsername(String name);
}
