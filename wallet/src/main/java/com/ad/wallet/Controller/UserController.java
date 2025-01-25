package com.ad.wallet.Controller;


import com.ad.wallet.Entity.User;
import com.ad.wallet.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/")
public class UserController {

    @Autowired
    private UserServices userServices;

    @GetMapping("all")
    public List<User> getAllUsers(){
        return userServices.getAllUsers();
    }

    @PostMapping("add")
    public String addUser(@RequestBody User user){
        return userServices.addUser(user);
    }

}
