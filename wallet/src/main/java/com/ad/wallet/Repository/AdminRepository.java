package com.ad.wallet.Repository;

import com.ad.wallet.Entity.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  AdminRepository extends MongoRepository<Admin,String> {
    Admin findByMobile(String mobile);
}
