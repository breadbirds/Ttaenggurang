package com.ladysparks.ttaenggrang.domain.vote.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ladysparks.ttaenggrang.domain.student.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteMode;
import com.ladysparks.ttaenggrang.domain.vote.entity.VoteStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
@JsonIgnoreProperties(value={"id", "voteStatus", "students"}, allowGetters=true)
public class VoteCreateDTO {
    private Long id;
    private String title;
    private Timestamp startDate;
    private Timestamp endDate;
    private VoteMode voteMode;  // 투표 모드 (학생 모드 / 선택 모드)
    private VoteStatus voteStatus;

    private List<StudentResponseDTO> students;  // 우리반 학생 리스트 (voteMode)가 학생 모드인 경우
}
