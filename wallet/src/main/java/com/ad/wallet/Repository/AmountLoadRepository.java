package com.ad.wallet.Repository;

import com.ad.wallet.Entity.AmountLoad;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AmountLoadRepository extends MongoRepository<AmountLoad, String> {
    @Query("{'admin': ?0}")
    List<AmountLoad> findByAdmin(String mobile);
    List<AmountLoad> findByMobile(String mobile);
}
