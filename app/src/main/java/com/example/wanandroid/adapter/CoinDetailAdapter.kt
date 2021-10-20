package com.example.wanandroid.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wanandroid.R
import com.example.wanandroid.entity.CoinDetail
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoinDetailAdapter(val list: ArrayList<CoinDetail>) :
    RecyclerView.Adapter<CoinDetailAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val reason: TextView = view.findViewById(R.id.reason)
        val amount: TextView = view.findViewById(R.id.amount)
        val date: TextView = view.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoinDetailAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coin_detail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoinDetailAdapter.ViewHolder, position: Int) {
        val dNow = Date()
        val ft = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
        ft.format(dNow)
        val time = list[position].date
        val res = Date(time)
        holder.date.text = ft.format(res)

        var start = -1
        val length = list[position].desc.length
        for (i in 0 until length) {
            if (list[position].desc[i] == '+') {
                start = i
            }
        }
        val amount = list[position].desc.substring(start, length)
        holder.amount.text = amount
        holder.reason.text = list[position].reason

    }

    override fun getItemCount(): Int = list.size

}