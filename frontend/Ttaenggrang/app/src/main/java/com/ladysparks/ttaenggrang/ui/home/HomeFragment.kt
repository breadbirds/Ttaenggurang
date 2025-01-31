package com.ladysparks.ttaenggrang.ui.home

import android.Manifest
import android.os.Bundle
import android.view.View
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.databinding.FragmentHomeBinding


//class HomeFragment : Fragment() {
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 초기화 기타 기능 작성
        initData()
    }





    private fun initData() {
        // 화면이 변경될 때 표시할 데이터/API 를 해당 함수에서 호출합니다.
    }


}