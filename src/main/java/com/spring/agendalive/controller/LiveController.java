package com.spring.agendalive.controller;


import com.spring.agendalive.models.AgendaLiveModel;
import com.spring.agendalive.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class LiveController {

    @Autowired
    LiveService liveService;

    @GetMapping("/lives")
    public ResponseEntity<Page<AgendaLiveModel>> getAllLives(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
                                                             @RequestParam(required = false) String flag){
        Page<AgendaLiveModel> livePage = liveService.findAll(pageable, flag);
        if(livePage.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<Page<AgendaLiveModel>>(livePage, HttpStatus.OK);
        }
    }

    @GetMapping("/lives/{id}")
    public ResponseEntity<AgendaLiveModel> getOneLive(@PathVariable(value="id") String id){
        Optional<AgendaLiveModel> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            return new ResponseEntity<AgendaLiveModel>(liveO.get(), HttpStatus.OK);
        }
    }

    @PostMapping("/lives")
    public ResponseEntity<AgendaLiveModel> saveLive(@RequestBody @Valid AgendaLiveModel agendaLiveModel) {
        agendaLiveModel.setRegistrationDate(LocalDateTime.now());
        return new ResponseEntity<AgendaLiveModel>(liveService.save(agendaLiveModel), HttpStatus.CREATED);
    }

    @DeleteMapping("/lives/{id}")
    public ResponseEntity<?> deleteLive(@PathVariable(value="id") String id) {
        Optional<AgendaLiveModel> agendaLiveModelO = liveService.findById(id);
        if(!agendaLiveModelO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            liveService.delete(agendaLiveModelO.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @PutMapping("/lives/{id}")
    public ResponseEntity<AgendaLiveModel> updateLive(@PathVariable(value="id") String id,
                                                      @RequestBody @Valid AgendaLiveModel agendaLiveModel) {
        Optional<AgendaLiveModel> liveO = liveService.findById(id);
        if(!liveO.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }else {
            agendaLiveModel.setId(liveO.get().getId());
            return new ResponseEntity<AgendaLiveModel>(liveService.save(agendaLiveModel), HttpStatus.OK);
        }
    }
}
