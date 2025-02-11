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

class StoreTeacherFragment : BaseFragment<FragmentStoreTeacherBinding>(FragmentStoreTeacherBinding::bind, R.layout.fragment_store_teacher) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getTeacherItemList()
    }

    private fun getTeacherItemList() {
        lifecycleScope.launch {
            runCatching {
                RetrofitUtil.storeService.getTeacherItemList()
            }.onSuccess {
                Log.d("Success", "${it}")
                val registeredItems = it.data?: emptyList()
                if (registeredItems.isNotEmpty()) {
                    binding.textNullRegisteredItem.visibility = View.GONE
                    binding.recyclerRegistedItem.visibility = View.VISIBLE
                } else {
                    binding.textNullRegisteredItem.visibility = View.VISIBLE
                    binding.recyclerRegistedItem.visibility = View.GONE
                }
            }.onFailure { throwable ->
                Log.e("API Error", "Failed to fetch registeredItems", throwable)

            }
        }
    }
}