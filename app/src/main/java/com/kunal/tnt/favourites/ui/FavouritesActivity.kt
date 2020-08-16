package com.kunal.tnt.favourites.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.favourites.adapters.FavouritesAdapter
import com.kunal.tnt.favourites.models.Favourites
import com.kunal.tnt.favourites.viewmodel.FavouritesViewModel
import com.kunal.tnt.home.data.Feed
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_favourites.*
import javax.inject.Inject

class FavouritesActivity : DaggerAppCompatActivity() {


    @Inject
    lateinit var favouritesAdapter: FavouritesAdapter

    @Inject
    lateinit var viewModelProvidersFactory: ViewModelProvidersFactory

    lateinit var binding: FragmentHomeBinding
 
    private val favouritesViewModel by lazy {
        ViewModelProvider(this, viewModelProvidersFactory)[FavouritesViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourites)

        rvFavourites.adapter = favouritesAdapter
        rvFavourites.layoutManager = LinearLayoutManager(this)

        favouritesViewModel.allFavourites.observe(this, Observer { allFavourites ->
            // Update the cached copy of the words in the adapter.
            allFavourites?.let { favouritesAdapter.addItems(it) }
        })

        favouritesAdapter.bookmarkListener = { item,pos ->
            favouritesViewModel.unBook(item.id)
            favouritesAdapter.notifyItemChanged(pos)
        }
    }
}