package dev.munawwar.journalApp.Services;

import dev.munawwar.journalApp.Entity.JournalEntry;
import dev.munawwar.journalApp.Repositories.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class JournalEntryService {

    //controller ---->  service ------> Repository
    @Autowired
    private JournalEntryRepository journalEntryRepo;     //spring handles implementation

    public void setEntry(JournalEntry journalEntry){
        journalEntryRepo.save(journalEntry);
    }

    public List<JournalEntry> getAll(){
        return journalEntryRepo.findAll();

    }
    public Optional<JournalEntry> getById(ObjectId id){
         return journalEntryRepo.findById(id);
    }

    public void deleteById(ObjectId id){
        journalEntryRepo.deleteById(id);
    }

}
