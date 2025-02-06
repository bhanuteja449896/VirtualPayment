package com.ad.wallet.Services;

import com.ad.wallet.Entity.Admin;
import com.ad.wallet.Repository.AdminRepository;
import com.ad.wallet.vo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    public Admin findAdminData(String mobile){
        Admin admin = adminRepository.findByMobile(mobile);
        return admin;
    }

    public Login addNewAdmin(Admin admin){
        Login login = new Login();
        Admin adminData = adminRepository.findByMobile(admin.getMobile());
        if(adminData!=null){
            login.setDesc("Admin  already exists");
            login.setRc("01");
            return login;
        }
        adminRepository.save(admin);
        login.setDesc("New Admin  added succesfully into Database");
        login.setRc("00");
        return login;
    }

}
