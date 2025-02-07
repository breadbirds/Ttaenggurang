package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.JobCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Job;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.repository.JobRespository;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.global.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRespository jobRespository;
    private final StudentRepository studentRepository;

    public ApiResponse<JobCreateDTO> createJob(JobCreateDTO jobCreateDTO) {

        // 1. 직업 중복 체크
        Optional<Job> existingJob = jobRespository.findByJobName(jobCreateDTO.getJobName());
        if (existingJob.isPresent()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "직업 이름이 이미 존재합니다.", null);
        }

        // 2. 학생 조회
        Optional<Student> studentOptional = studentRepository.findById(jobCreateDTO.getStudentId());
        if (studentOptional.isEmpty()) {
            return ApiResponse.error(HttpStatus.BAD_REQUEST.value(), "해당 학생을 찾을 수 없습니다.", null);
        }
        Student student = studentOptional.get();

        // 3. 직업 저장 (학생과 연결)
        Job job = Job.builder()
                .jobName(jobCreateDTO.getJobName())
                .jobDescription(jobCreateDTO.getJobDescription())
                .baseSalary(jobCreateDTO.getBaseSalary())
                .maxPeople(jobCreateDTO.getMaxPeople())
                .salaryDate(new Timestamp(System.currentTimeMillis()))
                .student(student)  // ✅ 학생 연결
                .build();

        Job savedJob = jobRespository.save(job);

        // 4. 응답 데이터 생성
        JobCreateDTO responseDTO = new JobCreateDTO(
                savedJob.getId(),
                savedJob.getJobName(),
                savedJob.getJobDescription(),
                savedJob.getBaseSalary(),
                savedJob.getMaxPeople(),
                savedJob.getStudent().getId() // ✅ student_id로 변경
        );

        return ApiResponse.created("직업 등록이 완료되었습니다.", responseDTO);
    }
}
