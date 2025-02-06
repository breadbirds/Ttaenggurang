package com.ladysparks.ttaenggrang.base

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.ImageView
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
    private val statusImageResId: Int? = null,
    private val showCloseButton: Boolean = true, // 닫기 버튼 표시 여부
    private val onPositiveClick: () -> Unit,
    private val onNegativeClick: () -> Unit,
    private val onCloseClick: (() -> Unit)? = null // 닫기 버튼 클릭 이벤트
) : Dialog(context) {

    init {
        setContentView(R.layout.dialog_base_confirm_cancel)

        val imgStatus = findViewById<ImageView>(R.id.imgDialogStatus)
        val btnClose = findViewById<AppCompatButton>(R.id.btnDialogClose)
        val textTitle = findViewById<TextView>(R.id.textDialogTitle)
        val textMessage = findViewById<TextView>(R.id.textDialogContent)
        val btnPositive = findViewById<AppCompatButton>(R.id.btnDialogConfirm)
        val btnNegative = findViewById<AppCompatButton>(R.id.btDialogCancel)

        // 상태 이미지 설정 (null이면 숨김)
        if (statusImageResId != null) {
            imgStatus.setImageResource(statusImageResId)
            imgStatus.visibility = View.VISIBLE
        } else {
            imgStatus.visibility = View.GONE
        }

        //  닫기 버튼 설정
        btnClose.visibility = if (showCloseButton) View.VISIBLE else View.GONE
        btnClose.setOnClickListener {
            onCloseClick?.invoke()
            dismiss()
        }

        // 다이얼로그 텍스트 설정
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
