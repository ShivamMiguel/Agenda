package com.spring.agendalive.repository;


import com.spring.agendalive.models.AgendaLiveModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LiveRepository extends JpaRepository<AgendaLiveModel, String> {

    Page<AgendaLiveModel> findByLiveDateAfterOrderByLiveDateAsc(LocalDateTime date, Pageable pageable);
    Page<AgendaLiveModel> findByLiveDateBeforeOrderByLiveDateDesc(LocalDateTime date, Pageable pageable);
}
