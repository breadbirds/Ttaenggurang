package com.ladysparks.ttaenggrang.domain.student.service;

import com.ladysparks.ttaenggrang.domain.bank.dto.BankAccountDTO;
import com.ladysparks.ttaenggrang.domain.bank.entity.BankAccount;
import com.ladysparks.ttaenggrang.domain.bank.mapper.BankAccountMapper;
import com.ladysparks.ttaenggrang.domain.bank.repository.BankAccountRepository;
import com.ladysparks.ttaenggrang.domain.nation.entity.Nation;
import com.ladysparks.ttaenggrang.domain.student.dto.SavingsAchievementDTO;
import com.ladysparks.ttaenggrang.domain.student.dto.StudentLoginRequestDTO;
import com.ladysparks.ttaenggrang.domain.student.dto.StudentLoginResponseDTO;
import com.ladysparks.ttaenggrang.domain.student.dto.StudentResponseDTO;
import com.ladysparks.ttaenggrang.domain.teacher.dto.MultipleStudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.teacher.dto.SingleStudentCreateDTO;
import com.ladysparks.ttaenggrang.domain.student.entity.Student;
import com.ladysparks.ttaenggrang.domain.teacher.entity.Teacher;
import com.ladysparks.ttaenggrang.domain.student.repository.StudentRepository;
import com.ladysparks.ttaenggrang.domain.teacher.repository.TeacherRepository;
import com.ladysparks.ttaenggrang.domain.teacher.service.TeacherService;
import com.ladysparks.ttaenggrang.domain.weekly_report.service.InvestmentService;
import com.ladysparks.ttaenggrang.global.config.JwtTokenProvider;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import com.ladysparks.ttaenggrang.global.utill.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.boot.model.naming.IllegalIdentifierException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;
    private final InvestmentService investmentService;
    private final PasswordEncoder passwordEncoder;
    private final BankAccountRepository bankAccountRepository; // ✅ 추가
    private final JwtTokenProvider jwtTokenProvider;
    private final SecurityUtil securityUtil;

    public Long getCurrentStudentId() {
        String username = securityUtil.getCurrentUser();
        return studentRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 학생을 찾을 수 없습니다."))
                .getId();
    }

    // ✅ 프로필 이미지 URL 업데이트 메서드
    @Transactional
    public void updateProfileImage(Long studentId, String profileImageUrl) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("학생을 찾을 수 없습니다."));

        student.setProfileImageUrl(profileImageUrl);  // ✅ 이미지 URL 저장
        studentRepository.save(student);
    }


    // ✅ 계좌 번호 생성 메서드
    private String generateAccountNumber() {
        return "110-" + (int) (Math.random() * 1_000_000_000);
    }

    // 여러 학생 계정 생성
    @Transactional
    public List<StudentResponseDTO> createStudentAccounts(Long teacherId, MultipleStudentCreateDTO multipleStudentCreateDTO) {
        // 1️⃣ 교사 확인
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("교사를 찾을 수 없습니다."));

        List<StudentResponseDTO> createdStudents = new ArrayList<>();

        // 2️⃣ 학생 계정 자동 생성
        for (int i = 1; i <= multipleStudentCreateDTO.getStudentCount(); i++) {
            String username = multipleStudentCreateDTO.getBaseId() + i;
            String password = multipleStudentCreateDTO.getBaseId() + i;

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
                    .nation(teacher.getNation())
                    .build();

            studentRepository.save(student); // ✅ **저장된 bankAccount를 참조하는 상태에서 저장**

            // 6️⃣ 생성된 계정 리스트에 추가
            createdStudents.add(new StudentResponseDTO(username));
        }

        return createdStudents;
    }

    // 단일 학생 계정 생성
    @Transactional
    public StudentResponseDTO createStudent(Long teacherId, SingleStudentCreateDTO studentCreateDTO) {
        // 1. 교사 확인
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new IllegalArgumentException("교사를 찾을 수 없습니다."));

        String username = studentCreateDTO.getUsername();
        String password = studentCreateDTO.getPassword();

        // 2. 중복 계정 확인
        if (studentRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 학생 계정입니다: " + username);
        }

        // 3. 은행 계좌 생성 및 저장
        BankAccountDTO bankAccountDTO = BankAccountDTO.builder()
                .accountNumber(generateAccountNumber())
                .balance(0)
                .build();

        BankAccount bankAccount = BankAccountMapper.INSTANCE.toEntity(bankAccountDTO);
        bankAccount = bankAccountRepository.save(bankAccount);

        // 4. 학생 계정 생성 (은행 계좌 연결)
        Student student = Student.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .teacher(teacher)
                .bankAccount(bankAccount)
                .nation(teacher.getNation())
                .build();

        studentRepository.save(student);

        // 5. 생성된 학생 정보 반환
        return new StudentResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getName(),
                null,  // 프로필 이미지 초기값
                teacher,
                bankAccount,
                null  // 토큰 값은 로그인 후 부여
        );
    }

    // 학생 로그인
    public StudentLoginResponseDTO loginStudent(StudentLoginRequestDTO studentLoginRequestDTO) {

        // 1. 학생 ID 확인
        Student student = studentRepository.findByUsername(studentLoginRequestDTO.getUsername())
                .orElseThrow(() -> new IllegalIdentifierException("아이디를 찾을 수 없습니다."));

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(studentLoginRequestDTO.getPassword(), student.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. JMT 토큰 생성
        String token = jwtTokenProvider.createToken(student.getUsername());

        // 응답을 위한 DTO 생성
        return new StudentLoginResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getName(),
                student.getProfileImageUrl(),
                student.getTeacher(),  // teacher 정보 추가
                student.getBankAccount(),  // bankAccount 정보 추가
                token
        );
    }

    // 직업 [해당 직업을 가진 전체 학생 목록 조회]
    public ApiResponse<List<StudentResponseDTO>> getStudentsByJobIdAndTeacher(Long teacherId, Long jobId) {
        List<Student> students = studentRepository.findByTeacherIdAndJobId(teacherId, jobId);

        if (students.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "해당 직업을 가진 학생이 없습니다.", null);
        }

        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getUsername(),
                        student.getName(),
                        student.getProfileImageUrl(),
                        student.getTeacher(),
                        student.getBankAccount(),
                        jwtTokenProvider.createToken(student.getUsername()) // ✅ JWT 토큰 포함
                ))
                .collect(Collectors.toList());

        return ApiResponse.success("직업을 가진 학생 목록 조회 성공", responseDTOs);
    }

    // ✅ 교사 ID로 우리반 학생 전체 조회
    public ApiResponse<List<StudentResponseDTO>> getMyClassStudents(Long teacherId) {
        List<Student> students = studentRepository.findAllByTeacherId(teacherId);

        if (students.isEmpty()) {
            return ApiResponse.error(404, "우리반 학생이 없습니다.", null);
        }

        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    // ✅ JWT 토큰 생성 (학생의 username 기반)
                    String token = jwtTokenProvider.createToken(student.getUsername());

                    // 학생 정보 DTO로 변환
                    return new StudentResponseDTO(
                            student.getId(),
                            student.getUsername(),
                            student.getName(),
                            student.getProfileImageUrl(),
                            student.getTeacher(),
                            student.getBankAccount(),
                            token
                    );
                })
                .collect(Collectors.toList());

        return ApiResponse.success("우리반 학생 목록 조회 성공", responseDTOs);
    }

    // ✅ 교사 ID와 학생 ID로 우리반 학생 상세 조회
    public ApiResponse<StudentResponseDTO> getStudentById(Long teacherId, Long studentId) {

        // 1️⃣ 학생 조회 (해당 교사의 반에 속한 학생인지 확인)
        Optional<Student> optionalStudent = studentRepository.findByIdAndTeacherId(studentId, teacherId);

        if (optionalStudent.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND.value(), "해당 학생을 찾을 수 없습니다.", null);
        }

        Student student = optionalStudent.get();

        // 2️⃣ JWT 토큰 생성 (학생의 username 기반)
        String token = jwtTokenProvider.createToken(student.getUsername()); // ✅ JWT 생성

        // 3️⃣ 학생 정보를 DTO로 변환하여 반환 (Base64 인코딩 포함)
        StudentResponseDTO responseDTO = new StudentResponseDTO(
                student.getId(),
                student.getUsername(),
                student.getName(),
                student.getProfileImageUrl(),
                student.getTeacher(),
                student.getBankAccount(),
                token
        );

        return ApiResponse.success("학생 정보 조회 성공", responseDTO);
    }

    // ✅ 교사 이메일로 ID 조회
    public Long getTeacherIdByEmail(String email) {
        return teacherRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("해당 이메일을 가진 교사를 찾을 수 없습니다."))
                .getId();
    }

    public ApiResponse<List<StudentResponseDTO>> getAllStudents() {
        List<Student> students = studentRepository.findAll();

        if (students.isEmpty()) {
            return ApiResponse.error(404, "등록된 학생이 없습니다.", null);
        }

        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> new StudentResponseDTO(
                        student.getId(),
                        student.getUsername(),
                        student.getName(),
                        student.getProfileImageUrl(),
                        student.getTeacher(),
                        student.getBankAccount(),
                        jwtTokenProvider.createToken(student.getUsername())
                ))
                .collect(Collectors.toList());

        return ApiResponse.success("학생 목록 조회 성공", responseDTOs);
    }

    public Long getNationIdById(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 학생이 존재하지 않습니다."));

        return Optional.ofNullable(student.getTeacher().getNation())
                .map(Nation::getId)
                .orElseThrow(() -> new IllegalArgumentException("해당 학생은 국가 정보가 등록되어 있지 않습니다."));
    }

    public Long findBankAccountIdById(Long studentId) {
        return studentRepository.findBankAccountIdById(studentId);
    }

    // ✅ 교사 ID로 우리반 학생 전체 조회
    public List<StudentResponseDTO> findAllByTeacherId(Long teacherId) {
        List<Student> students = studentRepository.findAllByTeacherId(teacherId);

        if (students.isEmpty()) {
            throw new IllegalArgumentException("우리반 학생이 없습니다.");
        }

        List<StudentResponseDTO> responseDTOs = students.stream()
                .map(student -> {
                    // ✅ JWT 토큰 생성 (학생의 username 기반)
                    String token = jwtTokenProvider.createToken(student.getUsername());

                    // 학생 정보 DTO로 변환
                    return new StudentResponseDTO(
                            student.getId(),
                            student.getUsername(),
                            student.getName(),
                            student.getProfileImageUrl(),
                            student.getTeacher(),
                            student.getBankAccount(),
                            token
                    );
                })
                .collect(Collectors.toList());

        return responseDTOs;
    }

    public Long findTeacherIdByStudentId(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 ID를 가진 학생이 존재하지 않습니다."));
        return student.getTeacher().getId();
    }

    /**
     * 특정 학생의 저축 목표 달성률을 계산
     */
    public SavingsAchievementDTO calculateSavingsAchievementRate() {
        // 학생 정보 조회
        Long studentId = getCurrentStudentId();
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new NotFoundException("해당 ID의 학생이 존재하지 않습니다."));

        // 학생의 국가 정보 조회
        Nation nation = student.getNation();
        if (nation == null) {
            throw new NotFoundException("해당 학생의 국가 정보가 존재하지 않습니다.");
        }

        SavingsAchievementDTO savingsAchievementDTO = SavingsAchievementDTO.builder()
                .studentId(studentId)
                .build();

        int savingsGoalAmount = nation.getSavingsGoalAmount(); // 국가에서 설정한 목표 저축 금액

        if (savingsGoalAmount == 0) {
            savingsAchievementDTO.setSavingsAchievementRate(0.0); // 목표 저축 금액이 0이면 달성률도 0
        } else {
            int bankBalance = student.getBankAccount().getBalance(); // 학생의 은행 잔고
            int investmentValue = investmentService.getCurrentInvestmentValue(studentId); // 현재 투자 평가액

            // 내 총 자산 계산
            int totalAssets = bankBalance + investmentValue;

            // 목표 달성률 계산
            savingsAchievementDTO.setSavingsAchievementRate(((double) totalAssets / savingsGoalAmount) * 100);
        }

        return savingsAchievementDTO;
    }

}
