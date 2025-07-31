package dev.munawwar.journalApp.Controllers;

import dev.munawwar.journalApp.Entity.JournalEntry;
import dev.munawwar.journalApp.Services.JournalEntryService;
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

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll(){
        List<JournalEntry> list = entryService.getAll();
        if(list.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(list,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<JournalEntry> setEntry(@RequestBody JournalEntry myEntry){
        try {
            myEntry.setDate(LocalDateTime.now());
            entryService.setEntry(myEntry);
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

    @DeleteMapping("/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId){
        Optional<JournalEntry> Id = entryService.getById(myId);
        if(Id.isPresent()){
            entryService.deleteById(myId);
            return new ResponseEntity<>("Successfully deleted",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId id, @RequestBody JournalEntry newEntry){
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
