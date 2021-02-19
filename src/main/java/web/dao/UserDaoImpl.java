package web.dao;

import web.model.Role;
import web.model.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public User getUserByName(String name) {
        Query query =  em.createQuery("from User where name =:paramName");
        return (User) query.setParameter("paramName",name).getSingleResult();
    }

    public Role getRoleByName(String name) {
        Query query =  em.createQuery("from Role where role =:paramName");
        return (Role) query.setParameter("paramName",name).getSingleResult();
    }

    @Override
    public void addUser(User user) {
        em.persist(user);

    }

    @Override
    public void updateUser(User user) {
        em.merge(user);
    }

    @Override
    public void removeUser(long id) {
        if (getUserById(id)!=null)
            em.remove(getUserById(id));
    }

    @Override
    public User getUserById(long id) {

        return em.find(User.class, id);

    }

    @Override
    public List<User> getAllUser() {
        List<User> users =  em.createQuery("from User").getResultList();

        return  users;

    }

    @Override
    public List<Role> getAllRole() {
        List<Role> roles =  em.createQuery("from Role").getResultList();
        return roles;
    }


    @Override
    public Set<Role> getSetRole(String[] roles) {
        Set<Role> roleSet = new HashSet<>();
        for (String r : roles) {
            roleSet.add(getRoleByName(r));
        }
        return roleSet;
    }
}
