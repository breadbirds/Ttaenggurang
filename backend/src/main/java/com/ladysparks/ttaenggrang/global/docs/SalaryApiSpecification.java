package com.ladysparks.ttaenggrang.global.docs;

import com.ladysparks.ttaenggrang.domain.vote.dto.VoteCreateDTO;
import com.ladysparks.ttaenggrang.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Salary", description = "주급 관리 기능 API")
public interface SalaryApiSpecification {

    @Operation(summary = "주급 지급 (확인 버튼 클릭 시, 학생 계좌로 지급)", description = """
        💡 교사가 확인 버튼을 클릭하면, 투표 결과에 따라 학생들의 주급이 자동으로 지급됩니다.
        
        - **투표가 진행된 경우**:
          1등(50%), 2등(30%), 3등(20%) 인센티브가 **기본급(baseSalary)**에 추가되어 지급됩니다.
        
        - **투표가 진행되지 않은 경우**:
          모든 학생에게 **기본급(baseSalary)**만 지급됩니다.
        
            ✅ 지급이 완료되면 학생들의 **은행 계좌(balance)**로 주급이 자동 반영됩니다.
        """)
    public ResponseEntity<ApiResponse<String>> distributeSalaries();
}
