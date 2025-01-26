package com.ad.wallet.Controller;


import com.ad.wallet.Entity.Credentials;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Services.UserServices;
import com.ad.wallet.vo.Login;
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
    public Login addUser(@RequestBody User user){
        return userServices.addUser(user);
    }

    @GetMapping("login/{mobile}/{password}")
    public Login loginByMobile(@PathVariable("mobile") String mobile, @PathVariable("password") String password){
        Login login = new Login();
        Credentials credentials = userServices.findCredentials(mobile);
        if(credentials==null){
            login.setDesc("No user found");
            login.setRc("02");
            return login;
        }
        else{
            if(credentials.getPassword().equals(password)){
                login.setDesc("login successful");
                login.setRc("00");
                return login;
            }
            login.setDesc("Incorrect password");
            login.setRc("01");
            return login;
        }
    }

    @GetMapping("{mobile}/data")
    public User getUserData(@PathVariable("mobile") String mobile){
        return userServices.findUserData(mobile);
    }

    @GetMapping("{mobile}/balance")
    public String getUserBalance(@PathVariable("mobile") String mobile){
        return "Balance : "+userServices.findUserData(mobile).getBalance();
    }



}
