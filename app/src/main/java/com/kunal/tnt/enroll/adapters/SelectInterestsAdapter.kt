package com.kunal.tnt.enroll.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.kunal.tnt.R
import com.kunal.tnt.databinding.ItemSelectInterestsBinding


class SelectInterestsAdapter(
    private val context: Context,
    private val rentalsList: ArrayList<String>
) :
    RecyclerView.Adapter<SelectInterestsAdapter.ViewHolder>() {

    val inflater = LayoutInflater.from(context)
    lateinit var binding: ItemSelectInterestsBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(inflater, R.layout.item_select_interests, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return rentalsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(val binding: ItemSelectInterestsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(pos: Int) {

            binding.data = rentalsList[pos]

        }
    }


}