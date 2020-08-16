package com.kunal.tnt.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.kunal.tnt.R
import com.kunal.tnt.databinding.FragmentCategoriesBinding
import dagger.android.support.DaggerFragment

class CategoriesFragment : DaggerFragment() {

    lateinit var binding: FragmentCategoriesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding.root
    }
}