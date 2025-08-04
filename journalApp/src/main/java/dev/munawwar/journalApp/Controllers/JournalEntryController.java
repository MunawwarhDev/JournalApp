package dev.munawwar.journalApp.Controllers;

import dev.munawwar.journalApp.Entity.JournalEntry;
import dev.munawwar.journalApp.Entity.User;
import dev.munawwar.journalApp.Services.JournalEntryService;
import dev.munawwar.journalApp.Services.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("Journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService entryService;

    @Autowired
    private UserService userService;

    @GetMapping("{userName}")
    public ResponseEntity<List<JournalEntry>> getJournalEntriesOfUser(@PathVariable String userName){
        User user = userService.findByUserName(userName);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<JournalEntry> allEntries = user.getJournalEntries();
        if(allEntries == null || allEntries.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(allEntries,HttpStatus.OK);

    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> setEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName){
        try {
            entryService.setEntry(myEntry,userName);
            return new ResponseEntity<>(myEntry, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{id}")          //localhost:8080/Journal/id?id=2 for request parameter
    public ResponseEntity<JournalEntry> getById(@PathVariable ObjectId id){
        Optional<JournalEntry> entry = entryService.getById(id);
        if(entry.isPresent()){
            return new ResponseEntity<>(entry.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{myId}/{userName}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId, @PathVariable String userName){
        Optional<JournalEntry> entry = entryService.getById(myId);
        if(entry.isPresent()){
            entryService.deleteById(myId,userName);
            return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PutMapping("id/{id}/{userName}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id,
                                        @RequestBody JournalEntry newEntry,
                                        @PathVariable String userName){
       JournalEntry old = entryService.getById(id).orElse(null);
       if(old != null) {
           old.setContent(newEntry.getContent() != null && !newEntry.getContent().equals("") ? newEntry.getContent() : old.getContent());
           old.setTitle(newEntry.getTitle() != null && !newEntry.getTitle().equals("") ? newEntry.getTitle() : old.getTitle());
           entryService.setEntry(old);
           return new ResponseEntity<>(old,HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


}
