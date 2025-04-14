package com.odtrend.adapter.out.persistence.stopWord;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StopWordRepository extends JpaRepository<StopWordEntity, Long> {

    @Query("select s.name from STOPWORD s")
    List<String> findAllNames();
}
