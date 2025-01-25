package com.ad.wallet.Repository;

import com.ad.wallet.Entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    // Custom queries can be added here, e.g., findByName(String name);
}