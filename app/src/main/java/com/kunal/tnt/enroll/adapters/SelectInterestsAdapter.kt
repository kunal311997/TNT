package com.kunal.tnt.enroll.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.kunal.tnt.R
import com.kunal.tnt.categories.Categories
import com.kunal.tnt.databinding.ItemSelectInterestsBinding
import javax.inject.Inject


class SelectInterestsAdapter @Inject constructor(
    private var interestsList: List<Categories>
) :
    RecyclerView.Adapter<SelectInterestsAdapter.ViewHolder>() {

    lateinit var binding: ItemSelectInterestsBinding
    var listener: ((view: View, item: Categories, pos: Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_select_interests,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return interestsList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(position)
    }

    inner class ViewHolder(val binding: ItemSelectInterestsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindData(pos: Int) {
            binding.data = interestsList[pos].categoryName
            binding.imgInterest.load(interestsList[pos].imageUrl)
            binding.root.setOnClickListener {
                listener?.invoke(binding.root,interestsList[pos],pos)
            }
        }
    }

    fun addItems(items: List<Categories>) {
        interestsList = items
        notifyDataSetChanged()
    }

}