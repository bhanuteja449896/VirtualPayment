package com.ad.wallet.Services;

import com.ad.wallet.Entity.Credentials;
import com.ad.wallet.Entity.User;
import com.ad.wallet.Repository.UserRepository;
import com.ad.wallet.vo.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public boolean updateUserBalance(String mobile, String newBalance) {
        // Create a query to find the user by mobile
        Query query = new Query();
        query.addCriteria(Criteria.where("mobile").is(mobile));

        // Create an update object to set the balance
        Update update = new Update();
        update.set("balance", newBalance);

        // Execute the update and check if the user exists
        return mongoTemplate.updateFirst(query, update, User.class).getMatchedCount() > 0;
    }

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

//    public User updateUserBalance(String mobile, String newBalance) {
//        User user = userRepository.findByMobile(mobile);
//        if (user != null) {
//            user.setBalance(newBalance);
//            userRepository.save(user);  // This may cause a new user to be inserted if no matching ID is found
//            return user;
//        }
//        return null;
//    }



}
