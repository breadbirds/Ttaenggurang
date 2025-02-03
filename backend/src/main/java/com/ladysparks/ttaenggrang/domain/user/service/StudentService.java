package com.ladysparks.ttaenggrang.domain.user.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.mapper.BankAccountMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import com.ladysparks.ttaenggrang.domain.bank.service.BankAccountService;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentLoginRequestDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentLoginResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.user.entity.Student;
import com.ladysparks.ttaenggrang.domain.user.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.user.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.user.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.global.config.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountService bankAccountService;
    private final BankAccountRepository bankAccountRepository; // ✅ 추가
    private final JwtTokenProvider jwtTokenProvider;

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

    @Transactional
    public List<StudentResponseDTO> createStudentAccounts(Long teacherId, StudentCreateDTO studentCreateDTO) {
        // 1️⃣ 교사 확인
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("교사를 찾을 수 없습니다."));

        List<StudentResponseDTO> createdStudents = new ArrayList<>();

        // 2️⃣ 학생 계정 자동 생성
        for (int i = 1; i <= studentCreateDTO.getStudentCount(); i++) {
            String username = studentCreateDTO.getBaseId() + i;
            String password = studentCreateDTO.getBaseId() + i;

            // 3️⃣ 중복 확인
            if (studentRepository.findByUsername(username).isPresent()) {
                throw new IllegalArgumentException("이미 존재하는 학생 계정: " + username);
            }

            // 4️⃣ BankAccount 생성 및 저장 (중요!!)
            BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                    .accountNumber(generateAccountNumber()) // 계좌 번호 생성
                    .balance(0) // 초기 잔액 0
                    .build();

            // DTO를 Entity로 변환 후 **저장**
            BankAccount bankAccount = BankAccountMapper.INSTANCE.toEntity(bankAccountDTO);
            bankAccount = bankAccountRepository.save(bankAccount); // ✅ **DB에 먼저 저장**

            // 5️⃣ 학생 계정 생성 (은행 계좌 연결)
            Student student = Student.builder()
                    .username(username)
                    .password(passwordEncoder.encode(password))
                    .teacher(teacher)
                    .bankAccount(bankAccount) // ✅ **저장된 계좌 연결**
                    .build();

            studentRepository.save(student); // ✅ **저장된 bankAccount를 참조하는 상태에서 저장**

            // 6️⃣ 생성된 계정 리스트에 추가
            createdStudents.add(new StudentResponseDTO(username, password));
        }

        return createdStudents;
    }

    // ✅ 계좌 번호 생성 메서드
    private String generateAccountNumber() {
        return "110-" + (int) (Math.random() * 1_000_000_000);
    }

    // 학생 로그인
    public StudentLoginResponseDTO loginStudent(StudentLoginRequestDTO studentLoginRequestDTO) {
        Student student = studentRepository.findByUsername(studentLoginRequestDTO.getUsername())
                .orElseThrow(() -> new IllegalIdentifierException("아이디를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(studentLoginRequestDTO.getPassword(), student.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JMT 토큰 생성
        String token = jwtTokenProvider.createToken(student.getUsername());

        // 응답을 위한 DTO 생성
        return new StudentLoginResponseDTO(
                student.getUsername(),
                student.getName(),
                student.getProfileImage() != null && student.getProfileImage().length > 0
                        ? Base64.getEncoder().encodeToString(student.getProfileImage())
                        : null,  // 빈 값 처리 추가
                student.getTeacher(),  // teacher 정보 추가
                student.getBankAccount(),  // bankAccount 정보 추가
                token
        );
    }
}
