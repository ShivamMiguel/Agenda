package com.spring.agendalive.service;


import com.spring.agendalive.models.AgendaLiveModel;
import com.spring.agendalive.repository.LiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LiveService {

    @Autowired
    LiveRepository liveRepository;

    public Page<AgendaLiveModel> findAll(Pageable pageable, String flag){
        if(flag != null && flag.equals("next")){
            return liveRepository.findByLiveDateAfterOrderByLiveDateAsc(LocalDateTime.now(), pageable);
        }else if(flag != null && flag.equals("previous")){
            return liveRepository.findByLiveDateBeforeOrderByLiveDateDesc(LocalDateTime.now(), pageable);
        }else{
            return liveRepository.findAll(pageable);
        }
    }

    public Optional<AgendaLiveModel> findById(String id){
        return liveRepository.findById(id);
    }

    public AgendaLiveModel save(AgendaLiveModel agendaLiveModel){
        return liveRepository.save(agendaLiveModel);
    }

    public void delete(AgendaLiveModel agendaLiveModel){
        liveRepository.delete(agendaLiveModel);
    }
}
