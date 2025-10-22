package com.Lostfound.lostfound.Repository;

import com.Lostfound.lostfound.Model.user;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface userRepository extends MongoRepository<user, String> {
    Optional<user> findByEmail(String email);
}
