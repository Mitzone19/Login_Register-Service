package ro.StartGym.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ro.StartGym.Exceptions.UserException;
import ro.StartGym.Model.User;
import ro.StartGym.Respository.UserRespository;

import java.util.List;


@Service
public class UserService {
    @Autowired
    UserRespository userRespository;

    private List<User> listAllUsers(){
        return userRespository.findAll();
    }
    private void insertUser(String username, String password){
        userRespository.save(username,password);
    }

    public void validateAndRegister(String username,String password1,String password2) throws UserException {
        boolean userFound = false;
        List<User> userList = listAllUsers();
        for (User user:userList){
            if (user.getUsername().equals(username)){
                userFound = true;
            }
        }

        if (userFound){
            throw new UserException("USER EXISTENT");
        } else if (!password1.equals(password2)){
            throw new UserException("PAROLA1 NU ESTE EGALA CU PAROLA2");
        } else if (username.length()<=3 || password1.length()<=3){
            throw new UserException("LUNGIMEA EMAILULUI/PAROLEI TREBUIE SA FIE MAI MARE DE 3 CARACTERE");
        } else{
            insertUser(username, password1);
        }
    }
    public void loginUser(String username, String password) throws UserException{
        List<User> userList = listAllUsers();
        boolean found = false;
        for (User user : userList){
            if (user.getUsername().equals(username) && user.getPassword().equals(password)){
                found = true;
            }
        }
        if (!found) {
            throw new UserException("EMAIL/PAROLA INCORECTE");
        }

    }

    public int returnIdForLoggedUser(String username){
        List<User> userList = listAllUsers();
        for (User user:userList){
            if (user.getUsername().equals(username)){
                return user.getId();
            }
        }
        return 0;
    }

}
