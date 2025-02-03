package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.user.dto.StudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;

//    // 학생 계정 생성 (토큰 문제 해결 후 다시 사용하기)
//    public List<StudentResponseDTO> createStudentAccounts(Long teacher_id, StudentCreateDTO studentCreateDTO) {
//        // 1. 교사 ID 확인
//        Teacher teacher = teacherRepository.findById(teacher_id)
//                .orElseThrow(() -> new IllegalArgumentException("교사를 찾을 수 없습니다."));
//
//        List<StudentResponseDTO> createStudents = new ArrayList<>();
//
//        // 2. 학생 계정 자동 생성
//        for (int i = 1; i <= studentCreateDTO.getStudentCount(); i++) {
//            String username = studentCreateDTO.getBaseId() + i;  // 베이스ID + 숫자
//            String password = studentCreateDTO.getBaseId() + i;  // 비밀번호는 username과 동일
//
//            // 3. 중복 확인
//            Optional<Student> existingStudent = studentRepository.findByUsername(username);
//            if (existingStudent.isPresent()) {
//                throw new IllegalArgumentException("이미 존재하는 학생 계정: " + username);
//            }
//
//            // 4. 학생 계정 저장
//            Student student = Student.builder()
//                    .username(username)
//                    .password(passwordEncoder.encode(password))  // 비밀번호 암호화
//                    .teacher(teacher)
//                    .build();
//            studentRepository.save(student);
//
//            // 5. 생성된 계정 리스트에 추가
//            createStudents.add(new StudentResponseDTO(username, password));
//        }
//
//        return createStudents;  // 생성된 계정 반환
//    }

    // 학생 계정 생성 (로그인 없이 진행)
    public List<StudentResponseDTO> createStudentAccounts(StudentCreateDTO studentCreateDTO) {
        List<StudentResponseDTO> createdStudents = new ArrayList<>();

        // 학생 계정 자동 생성
        for (int i = 1; i <= studentCreateDTO.getStudentCount(); i++) {
            String username = studentCreateDTO.getBaseId() + i;
            String password = studentCreateDTO.getBaseId() + i;

            // 중복 확인
            Optional<Student> existingStudent = studentRepository.findByUsername(username);
            if (existingStudent.isPresent()) {
                throw new IllegalIdentifierException("이미 존재하는 학생 계정: " + username);
            }

            // 학생 계정 저장
            Student student = Student.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .createdAt(new Timestamp(System.currentTimeMillis()))
                    .build();

            System.out.println("💡 저장 전 Student 객체: " + student);
            studentRepository.save(student);

            // 생성된 계정 리스트에 추가
            createdStudents.add(new StudentResponseDTO(username, password));
        }
        return createdStudents;  // 생성된 계정 반환
    }


}
