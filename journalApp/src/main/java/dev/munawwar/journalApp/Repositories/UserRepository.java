package dev.munawwar.journalApp.Repositories;

import dev.munawwar.journalApp.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, ObjectId> {

    User findByUserName(String Uname);

}
