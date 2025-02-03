package com.ladysparks.ttaenggrang.domain.user.repository;

import com.ladysparks.ttaenggrang.domain.user.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface JobRespository extends JpaRepository<Job, Long> {
    Optional<Job> findByJobName(String jobName);
}
