package ro.StartGym.Respository;

import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import ro.StartGym.Model.User;

import java.util.List;

@Repository
public class UserRespository {

    @PersistenceContext
    EntityManager entityManager;

    public List<User> findAll(){
        return entityManager.createNativeQuery("select * from user", User.class).getResultList();
    }

    @Transactional
    public void save(String email, String password){
        User user = new User(email,password);
        entityManager.persist(user);
    }
}
