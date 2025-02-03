package com.ladysparks.ttaenggrang.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.data.model.dto.AlarmDTO

class AlarmAdapter(
    private var list: List<AlarmDTO>
) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    inner class AlarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.textAlarmTitle)
        private val date = itemView.findViewById<TextView>(R.id.textAlarmDate)
        private val content = itemView.findViewById<TextView>(R.id.textAlarmContent)
        private val publish = itemView.findViewById<TextView>(R.id.textAlarmPublish)

        fun bind(item: AlarmDTO) {
//            title.text = item.title
//            date.text = item.content
//            content.text = item.content
//            publish.text = item.publish
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

    fun updateData(newList: Boolean) {
//        list = newList
        notifyDataSetChanged()
    }
}
