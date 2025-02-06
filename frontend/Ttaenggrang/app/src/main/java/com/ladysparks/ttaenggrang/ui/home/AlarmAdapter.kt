package com.ladysparks.ttaenggrang.ui.home

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.data.model.dto.AlarmDto
import com.ladysparks.ttaenggrang.util.DataUtil

class AlarmAdapter(
    private var list: List<AlarmDto>
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.textAlarmTitle)
        private val content = itemView.findViewById<TextView>(R.id.textAlarmContent)
        private val publish = itemView.findViewById<TextView>(R.id.textAlarmPublish)
        private val date = itemView.findViewById<TextView>(R.id.textAlarmDate)

        fun bind(item: AlarmDto) {
            title.text = item.title
            content.text = item.content
            publish.text = item.publisher
            date.text = DataUtil.formatDate(item.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_alarm, parent, false)
        return AlarmViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<AlarmDto>) {
        list = newList
        notifyDataSetChanged()
    }
}
