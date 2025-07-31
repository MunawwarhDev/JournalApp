package dev.munawwar.journalApp.Services;

import dev.munawwar.journalApp.Entity.JournalEntry;
import dev.munawwar.journalApp.Entity.User;
import dev.munawwar.journalApp.Repositories.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepo;

    public List<User> findAll(){
        return userRepo.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id);
    }

    public String saveUser(User user){
        userRepo.save(user);
        return "saved successfully.";

    }

    public User findByUserName(String uName){
        return userRepo.findByUserName(uName);
    }
}
