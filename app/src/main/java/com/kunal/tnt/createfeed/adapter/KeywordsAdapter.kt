package com.kunal.tnt.createfeed.adapter

import com.kunal.tnt.R
import com.kunal.tnt.common.adapters.BaseRecyclerViewAdapter
import com.kunal.tnt.createfeed.data.Data
import com.kunal.tnt.databinding.ItemKeywordsBinding

class KeywordsAdapter(private val keywordsList: List<String>) :
    BaseRecyclerViewAdapter<Data, ItemKeywordsBinding>() {


    override fun getLayout() = R.layout.item_keywords

    override fun onBindViewHolder(
        holder: Companion.BaseViewHolder<ItemKeywordsBinding>,
        position: Int
    ) {
        holder.binding.keyword = keywordsList[position]
    }

}