package com.ladysparks.ttaenggrang.ui.store

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.base.BaseFragment
import com.ladysparks.ttaenggrang.data.remote.RetrofitUtil
import com.ladysparks.ttaenggrang.databinding.FragmentStoreTeacherBinding
import com.ladysparks.ttaenggrang.databinding.FragmentStudentsBinding
import kotlinx.coroutines.launch
import android.app.Dialog
import androidx.appcompat.app.AlertDialog
import com.ladysparks.ttaenggrang.databinding.DialogItemTeacherRegisterBinding
import com.ladysparks.ttaenggrang.util.showToast
import com.ladysparks.ttaenggrang.data.model.request.StoreRegisterRequest


class StoreTeacherFragment : BaseFragment<FragmentStoreTeacherBinding>(FragmentStoreTeacherBinding::bind, R.layout.fragment_store_teacher) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTeacherItemList()
        binding.apply {
            binding.btnRegisterItem.setOnClickListener{
                requestRegisterItem()
            }
        }
    }

    // 아이템 등록하기 다이얼로그
    private fun requestRegisterItem() {
        val dialogBinding = DialogItemTeacherRegisterBinding.inflate(layoutInflater)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(dialogBinding.root)
            .create()

        dialogBinding.btnCancel.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnClose.setOnClickListener { dialog.dismiss() }
        dialogBinding.btnRegister.setOnClickListener {

            val itemName = dialogBinding.textLayoutContent1.editText?.text.toString().trim()
            val itemDescription = dialogBinding.textLayoutContent2.editText?.text.toString().trim()
            val itemPrice = dialogBinding.textLayoutContent3.editText?.text.toString().trim().toIntOrNull()
            val itemCount = dialogBinding.textLayoutContent4.editText?.text.toString().trim().toIntOrNull()
            // 만들어야 함

            if (itemName.isEmpty()) {
                showToast("상품명은 필수 입력 정보입니다")
                return@setOnClickListener
            }

            if (itemPrice == null || itemPrice < 0) {
                showToast("금액은 0 이상의 숫자여야 합니다")
                return@setOnClickListener
            }

            if (itemCount == null || itemCount < 0) {
                showToast("수량은 0 이상의 숫자여야 합니다")
                return@setOnClickListener
            }

            val itemRegister = StoreRegisterRequest(
                name = itemName,
                description = itemDescription,
                price = itemPrice,
                quantity = itemCount
            )

            registerThisItem(itemRegister)

            dialog.dismiss()
        }

        dialog.show()
    }


    private fun getTeacherItemList() {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.storeService.getStudentItemList()
            }.onSuccess {
                Log.d("test", "등록된 아이템 조회 성공${it}")
                val registeredItems = it.data?: emptyList()
                // 등록된 아이템 수
                binding.textCountRegisteredItem.text = "${registeredItems.size}개"
                if (registeredItems.isNotEmpty()) {
                    binding.textNullRegisteredItem.visibility = View.GONE
                    binding.recyclerRegistedItem.visibility = View.VISIBLE
                } else {
                    binding.textNullRegisteredItem.visibility = View.VISIBLE
                    binding.recyclerRegistedItem.visibility = View.GONE
                }
            }.onFailure { throwable ->
                Log.e("test", "Failed to fetch registeredItems", throwable)

            }
        }
    }

    private fun registerThisItem(itemRegister: StoreRegisterRequest) {
        lifecycleScope.launch {
            runCatching {
//                Log.d("Registered Item", "${itemRegister}")
                RetrofitUtil.storeService.registerItem(itemRegister)
            }.onSuccess {
                Log.d("Registered Item", "상품 등록 성공 ${it}")
                showToast("상품이 성공적으로 등록되었습니다")
                getTeacherItemList()
            }.onFailure { throwable ->
                Log.e("Registered Item", "item register failure", throwable)
            }
        }
    }

}