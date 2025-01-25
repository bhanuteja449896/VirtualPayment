package com.ad.wallet.Services;

import com.ad.wallet.Entity.User;
import com.ad.wallet.Repository.UserRepository;
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

    public String addUser(User user){
        userRepository.save(user);
        return "User added into the database";
    }

}
