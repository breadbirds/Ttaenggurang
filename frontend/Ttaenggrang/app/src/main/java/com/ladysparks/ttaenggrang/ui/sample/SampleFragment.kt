package com.ladysparks.ttaenggrang.ui.sample

import android.os.Bundle
import android.view.View
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentStudentsBinding


// 기분 구조는 아래와 같이 사용합니다. BaseFragment + 해당 Fragment Binding 해서 사용 + onViewCreated 만 있으면 됨.
class StudentsFragment  : BaseFragment<FragmentStudentsBinding>(FragmentStudentsBinding::bind, R.layout.fragment_students) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}