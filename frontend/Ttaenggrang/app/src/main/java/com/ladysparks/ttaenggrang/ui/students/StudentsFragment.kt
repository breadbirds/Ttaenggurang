package com.ladysparks.ttaenggrang.ui.students

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.base.BaseTableAdapter
import com.ladysparks.ttaenggrang.ui.component.BaseTwoButtonDialog
import com.ladysparks.ttaenggrang.data.model.request.StudentSingleCreateRequest
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.FragmentStudentsBinding
import com.ladysparks.ttaenggrang.ui.component.BaseTableRowModel
import com.ladysparks.ttaenggrang.util.showToast
import kotlinx.coroutines.launch

class StudentsFragment : BaseFragment<FragmentStudentsBinding>(
    FragmentStudentsBinding::bind,
    R.layout.fragment_students
) {
    private lateinit var studentsViewModel: StudentsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // viewModel
        studentsViewModel = ViewModelProvider(this).get(StudentsViewModel::class.java)

        // LifeData
        fetchStudentList()

        initEvent()
        initDat()

        binding.recyclerStudents.visibility = View.VISIBLE
    }

    private fun fetchStudentList() {
        val studentHeader = listOf("번호", "이름", "직업(월급)", "아이디", "비밀번호")

        // 행 클릭 이벤트 여부 설정 (필요하면 추가, 필요 없으면 null)
        val isRowClickable = true // 🔥 필요 없으면 false로 설정

        // 어댑터 초기화 (처음에는 빈 리스트)
        val adapter = if (isRowClickable) {
            // 클릭 이벤트를 사용하는 경우, 아래 {} 안에 작성 : Intent, Toast, ShowDialog 등....
            BaseTableAdapter(studentHeader, emptyList()) { rowIndex, rowData ->
                Log.d("TAG", "Row 클릭됨: $rowIndex, 데이터: $rowData")
                showToast("${rowIndex} : ${rowData[2]}")
            }
        } else {
            // 행 클릭 비활성화
            BaseTableAdapter(studentHeader, emptyList(), null)
        }

        binding.recyclerStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerStudents.adapter = adapter

        studentsViewModel.studentList.observe(viewLifecycleOwner) { studentList ->

            val studentList = studentList.mapIndexed { index, student ->
                BaseTableRowModel(
                    listOf(
                        (index + 1).toString(),  // 번호
                        student.username,        // 이름 (원래는 student.name 이었겠지만 username 사용)
                        student.teacher.name,    // 직업 (여기서는 teacher.name 사용)
                        student.username,        // 아이디
                        student.teacher.password  // 비밀번호 대신 학교명 (데이터에 비밀번호 없음)
                    )
                )
            }

            // 데이터 변동 사항이 생길 경우 업데이트
            adapter.updateData(studentHeader, studentList)

            // RecyclerView 표시
            binding.recyclerStudents.visibility = View.VISIBLE
        }

        // ViewModel에서 데이터 가져오기
        studentsViewModel.fetchStudentList()
    }


    private fun initDat() {

    }

    private fun initEvent() {

        binding.btnCreateStudent.setOnClickListener {
            val dialog = BaseTwoButtonDialog(
                context = requireContext(),
                title = "학생 정보를 추가합니다",
                message = "",
                positiveButtonText = "여러 학생 추가",
                negativeButtonText = "신규 학생 추가",
                statusImageResId = R.drawable.ic_students,
                showCloseButton = true,
                onPositiveClick = {
                    showToast("확인 버튼")
                },
                onNegativeClick = {
                    showSingleStudentAddDialog()
                },
                onCloseClick = {
                    showToast("학생 등록 취소")
                }
            )

            // 다이얼로그 표시
            dialog.show()
        }


        binding.btnTabStudentInfo.setOnClickListener {
            //
            selectTab(true)
            // if 조건 추가. 데이터가 없으면 recyclerview 가리고, 텍스트만 보이도록
            binding.recyclerStudents.visibility = View.VISIBLE
        }

        binding.btnTabFincialStatus.setOnClickListener {
            // if 조건 추가. 데이터가 없으면 recyclerview 가리고, 텍스트만 보이도록
            binding.recyclerStudents.visibility = View.VISIBLE
            selectTab(false)
            sampleDataFinance()
        }
    }

    private fun showSingleStudentAddDialog() {
        // 수정 : 국가 설립일, 단위, 학급인원 추가 되어야 함
        // 학급이원이 변경된 경우 수정 요청 api 날려야함. 후순위

        val dialogView = LayoutInflater.from(requireContext())
            .inflate(R.layout.dialog_student_registration, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .create()

        val name = dialogView.findViewById<EditText>(R.id.editAddName)
        val job = dialogView.findViewById<EditText>(R.id.editJob)
        val id = dialogView.findViewById<EditText>(R.id.editId)
        val password = dialogView.findViewById<EditText>(R.id.editPassword)


        val btnClose = dialogView.findViewById<ImageButton>(R.id.btnClose)
        val btnConfirm = dialogView.findViewById<AppCompatButton>(R.id.btnStudentRegistration)

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnConfirm.setOnClickListener {
            // 이름, 직업, 아이디, 비번 모두 있으며
            if (true) {
            }

            // 단순 데이터 추가
            val user =
                StudentSingleCreateRequest(username = "김쌉쌉2", password = "1234", profileImage = "")
            lifecycleScope.launch {
                runCatching {
                    // 서버로부터 알림 데이터 요청
                    RetrofitUtil.teacherService.singleCreate(user)
                }.onSuccess { data ->
                    showToast("회원추가 완료 ! ${data}")
                }.onFailure { exception ->
                    showToast("회원추가 실패 ! ${exception}")
                    Log.e("AlarmViewModel", "Error fetching alarms ${exception}")
                }
            }



            dialog.dismiss()  // 다이얼로그 닫기
        }

        dialog.show()


    }


    private fun sampleDataFinance() {
        // 컬럼 개수가 5개 일 때 사용 방법
        val header2 = listOf("번호", "이름", "계좌 잔액", "은행 가입상품", "보유 주식")
        val data2 = listOf(
            BaseTableRowModel(listOf("박지성", "user04", "34,000", "3개", "1개")),
            BaseTableRowModel(listOf("손흥민", "user05", "23,999", "1개", "2개"))
        )

        // 컬럼 개수가 5개인 테이블
        val adapter2 = BaseTableAdapter(header2, data2)
        binding.recyclerStudents.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerStudents.adapter = adapter2
    }

    // Tab 변경 이벤트
    private fun selectTab(isStudentInfo: Boolean) {
        val context = context ?: return

        if (isStudentInfo) {
            // "학생 정보" 활성화
            binding.tvStudentInfo.setTextAppearance(R.style.heading4)
            binding.tvStudentInfo.setTextColor(ContextCompat.getColor(context, R.color.mainOrange))
            binding.underlineStudentInfo.visibility = View.VISIBLE

            // "재정 상태" 비활성화
            binding.tvFinancialStatus.setTextAppearance(R.style.body2)
            binding.tvFinancialStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.lightGray
                )
            )
            binding.underlineFinancialStatus.visibility = View.INVISIBLE
        } else {
            // 학생 정보 비활성화
            binding.tvStudentInfo.setTextAppearance(R.style.body2)
            binding.tvStudentInfo.setTextColor(ContextCompat.getColor(context, R.color.lightGray))
            binding.underlineStudentInfo.visibility = View.INVISIBLE

            // 재정 상태 활성화
            binding.tvFinancialStatus.setTextAppearance(R.style.heading4)
            binding.tvFinancialStatus.setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.mainOrange
                )
            )
            binding.underlineFinancialStatus.visibility = View.VISIBLE
        }
    }


}
