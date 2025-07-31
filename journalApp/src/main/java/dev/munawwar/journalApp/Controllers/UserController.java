package dev.munawwar.journalApp.Controllers;

import dev.munawwar.journalApp.Entity.User;
import dev.munawwar.journalApp.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/User")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @PostMapping
    public String saveUser(@RequestBody User user){
        return userService.saveUser(user);
    }

    @GetMapping("{userName}")
    public User getByUserName(@PathVariable String userName){
        return userService.findByUserName(userName);
    }

    @PutMapping("{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user,@PathVariable String userName){
        User userInDB = userService.findByUserName(userName);
        if(userInDB != null){
             userInDB.setUserName(user.getUserName());
             userInDB.setPassWord(user.getPassWord());
             userService.saveUser(userInDB);
             return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
