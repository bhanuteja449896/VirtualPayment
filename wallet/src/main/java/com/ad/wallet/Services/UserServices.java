package com.ad.wallet.Services;

import com.ad.wallet.Entity.Credentials;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Repository.UserRepository;
import com.ad.wallet.vo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Login addUser(User user){
        Login login = new Login();
        User userData = userRepository.findByMobile(user.getMobile());
        if(userData!=null){
            login.setDesc("User already exists");
            login.setRc("01");
            return login;
        }
        userRepository.save(user);
        login.setDesc("User added succesfully into Database");
        login.setRc("00");
        return login;
    }

    public Credentials findCredentials(String mobile){
        User user = userRepository.findByMobile(mobile);
        if(user!=null){
            Credentials credentials = new Credentials();
            credentials.setPassword(user.getPassword());
            credentials.setMobile(user.getMobile());
            return credentials;
        }
        return null;
    }

    public User findUserData(String mobile){
        User user = userRepository.findByMobile(mobile);
        return user;
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

}
