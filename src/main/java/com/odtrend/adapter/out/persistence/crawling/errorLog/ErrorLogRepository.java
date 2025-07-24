package com.odtrend.adapter.out.persistence.crawling.errorLog;

import org.springframework.data.jpa.repository.JpaRepository;

interface ErrorLogRepository extends JpaRepository<ErrorLogEntity, Long> {

}
