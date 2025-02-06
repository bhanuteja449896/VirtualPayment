package com.ad.wallet.Controller;

import com.ad.wallet.Entity.Admin;
import com.ad.wallet.Entity.Credentials;
import com.ad.wallet.Services.AdminServices;
import com.ad.wallet.Services.UserServices;
import com.ad.wallet.vo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/")
@CrossOrigin(origins = "*")
public class AdminController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("login/{mobile}/{password}")
    public Login AdminLogin(@PathVariable("mobile") String mobile,@PathVariable("password") String password){
        Login login = new Login();
        Admin admin = adminServices.findAdminData(mobile);
        if(admin==null){
            login.setDesc("No user found");
            login.setRc("02");
            return login;
        }
        else{
            if(admin.getPassword().equals(password)){
                login.setDesc("login successful");
                login.setRc("00");
                return login;
            }
            login.setDesc("Incorrect password");
            login.setRc("01");
            return login;
        }
    }

    @PostMapping("add")
    public Login addAdmin(@RequestBody Admin admin){
        return adminServices.addNewAdmin(admin);
    }




}
