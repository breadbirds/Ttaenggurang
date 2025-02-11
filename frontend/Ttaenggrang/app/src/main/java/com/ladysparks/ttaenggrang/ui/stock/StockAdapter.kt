package com.ladysparks.ttaenggrang.ui.home

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ladysparks.ttaenggrang.R
import com.ladysparks.ttaenggrang.data.model.dto.AlarmDto
import com.ladysparks.ttaenggrang.data.model.dto.StockDto
import com.ladysparks.ttaenggrang.util.DataUtil
import org.w3c.dom.Text

class StockAdapter(
    private var list: List<StockDto>
) : RecyclerView.Adapter<StockAdapter.StockViewHolder>() {

    inner class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.textStockName)
        private val price = itemView.findViewById<TextView>(R.id.textStockPrice)
        private val changeRate = itemView.findViewById<TextView>(R.id.textStockChangeRate)

        fun bind(item: StockDto) {
            title.text = item.name
            price.text = item.price_per.toString()
            changeRate.text = "${item.changeRate}%"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_stock_list, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun updateData(newList: List<StockDto>) {
        list = newList
        notifyDataSetChanged()
    }
}
