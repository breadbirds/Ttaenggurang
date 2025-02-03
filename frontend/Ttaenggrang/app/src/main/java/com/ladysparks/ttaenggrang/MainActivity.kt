package com.ladysparks.ttaenggrang

import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.ladysparks.ttaenggrang.databinding.ActivityMainBinding
import com.ladysparks.ttaenggrang.ui.bank.BankFragment
import com.ladysparks.ttaenggrang.ui.home.HomeTeacherFragment
import com.ladysparks.ttaenggrang.ui.stock.StockFragment
import com.ladysparks.ttaenggrang.util.showToast


class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            window.insetsController?.hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
        }

        // 기본 프래그먼트 로드
        replaceFragment(HomeTeacherFragment())
        binding.navigationView.setCheckedItem(R.id.navHome) // 기본 선택

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            menuItem.isChecked = true  // 선택한 아이템 활성화
            when (menuItem.itemId) {
                R.id.navHome -> replaceFragment(HomeTeacherFragment())
                R.id.navStudents -> showToast("학생 관리 페이지 준비중")
                R.id.navCountryInfo -> showToast("국가 정보 페이지 준비중")
                R.id.navRevenue -> showToast("국세청 페이지 준비중")
                R.id.navBank -> replaceFragment(BankFragment())
                R.id.navStock -> replaceFragment(StockFragment())
                R.id.navShop -> showToast("상점 페이지 준비중")
            }
            true
        }
    }


    // Fragment 이동을 위한 함수
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}