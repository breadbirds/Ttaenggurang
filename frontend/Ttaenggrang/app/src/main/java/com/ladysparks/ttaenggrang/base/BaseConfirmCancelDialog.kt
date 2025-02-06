package com.ladysparks.ttaenggrang.base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import com.ladysparks.ttaenggrang.R

class BaseTwoButtonDialog(
    context: Context,
    private val title: String,
    private val message: String,
    private val positiveButtonText: String,
    private val negativeButtonText: String,
    private val showStatusImage: Boolean = true, // ✅ 이미지 표시 여부 설정
    private val showCloseButton: Boolean = true, // ✅ 닫기 버튼 표시 여부 설정
    private val onPositiveClick: () -> Unit,
    private val onNegativeClick: () -> Unit,
    private val onCloseClick: (() -> Unit)? = null // ✅ 닫기 버튼 클릭 이벤트
) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_base_confirm_cancel)

        val imgStatus = findViewById<ImageButton>(R.id.imgDialogStatus)
        val btnClose = findViewById<ImageButton>(R.id.btnDialogClose)
        val textTitle = findViewById<TextView>(R.id.textDialogTitle)
        val textMessage = findViewById<TextView>(R.id.textDialogContent)
        val btnPositive = findViewById<AppCompatButton>(R.id.btnDialogConfirm)
        val btnNegative = findViewById<AppCompatButton>(R.id.btDialogCancel)

        // 1. 다이얼로그 상태 iCon 표시 여부
        imgStatus.visibility = if (showStatusImage) View.VISIBLE else View.GONE

        // 닫기 버튼 표시 여부 및 클릭 리스너 설정
        btnClose.visibility = if (showCloseButton) View.VISIBLE else View.GONE
        btnClose.setOnClickListener {
            onCloseClick?.invoke()  // 닫기 버튼 클릭 리스너 실행
            dismiss()
        }

        // 다이얼로그 타이틀 및 메시지 설정
        textTitle.text = title
        textMessage.text = message

        // 버튼 텍스트 설정
        btnPositive.text = positiveButtonText
        btnNegative.text = negativeButtonText

        // 버튼 클릭 리스너 설정
        btnPositive.setOnClickListener {
            onPositiveClick()
            dismiss()
        }
        btnNegative.setOnClickListener {
            onNegativeClick()
            dismiss()
        }
    }
}