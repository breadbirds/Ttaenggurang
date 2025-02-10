package com.ladysparks.ttaenggrang.domain.vote.repository;

import com.ladysparks.ttaenggrang.domain.vote.entity.Vote;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    // 진행 중인 투표가 있는지 확인하는 메서드 (IN_PROGRESS) 상태 조회
    Optional<Vote> findByStatus(VoteStatus status);
}
