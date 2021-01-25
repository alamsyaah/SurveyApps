package com.project.surveyapps.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.project.surveyapps.R
import com.project.surveyapps.model.MSurvey
import java.util.ArrayList

class SurveyRecyclerAdapter(var mContext: Context, var mData: ArrayList<MSurvey>) :
    RecyclerView.Adapter<SurveyRecyclerAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id = itemView.findViewById<TextView>(R.id.tv_idpertanyaan)
        var pertanyaan = itemView.findViewById<TextView>(R.id.tv_pertanyaan)
        var jawaban_sangat_memuaskan = itemView.findViewById<RadioButton>(R.id.rb_1)
        var jawaban_memuaskan = itemView.findViewById<RadioButton>(R.id.rb_2)
        var jawaban_cukup = itemView.findViewById<RadioButton>(R.id.rb_3)
        var jawaban_kurang_memuaskan = itemView.findViewById<RadioButton>(R.id.rb_4)
        var jawaban_buruk = itemView.findViewById<RadioButton>(R.id.rb_5)
        var jawaban_sangat_buruk = itemView.findViewById<RadioButton>(R.id.rb_6)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_survey, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = mData[position]
        holder.id.text = data.idpertanyaan.toString()
        holder.pertanyaan.text = data.pertanyaan
        holder.jawaban_sangat_memuaskan.setOnClickListener {
            data.idjawaban = "1"
            onItemClickCallback.onItemClicked(mData, position)
        }
        holder.jawaban_memuaskan.setOnClickListener {
            data.idjawaban = "2"
            onItemClickCallback.onItemClicked(mData, position)
        }
        holder.jawaban_cukup.setOnClickListener {
            data.idjawaban = "3"
            onItemClickCallback.onItemClicked(mData, position)
        }
        holder.jawaban_kurang_memuaskan.setOnClickListener {
            data.idjawaban = "4"
            onItemClickCallback.onItemClicked(mData, position)
        }
        holder.jawaban_buruk.setOnClickListener {
            data.idjawaban = "5"
            onItemClickCallback.onItemClicked(mData, position)
        }
        holder.jawaban_sangat_buruk.setOnClickListener {
            data.idjawaban = "6"
            onItemClickCallback.onItemClicked(mData, position)
        }
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ArrayList<MSurvey>, position: Int)
    }
}