package com.kunal.tnt.favourites.ui

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.kunal.tnt.R
import com.kunal.tnt.common.uils.Utilities
import com.kunal.tnt.common.uils.Utilities.gone
import com.kunal.tnt.common.uils.Utilities.showToast
import com.kunal.tnt.common.uils.Utilities.visible
import com.kunal.tnt.common.viewmodels.ViewModelProvidersFactory
import com.kunal.tnt.databinding.FragmentHomeBinding
import com.kunal.tnt.favourites.adapters.FavouritesAdapter
import com.kunal.tnt.favourites.viewmodel.FavouritesViewModel
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

        favouritesViewModel.allFavourites.observe(this, Observer { allFavourites ->
            if (allFavourites.isNotEmpty()) {
                rvFavourites.visible()
                txtNoData.gone()
                allFavourites?.let { favouritesAdapter.addItems(it) }
            } else {
                txtNoData.visible()
                rvFavourites.gone()
            }

        })

        favouritesAdapter.bookmarkListener = { item, pos ->
            favouritesViewModel.unBook(item.id)
            favouritesAdapter.notifyItemChanged(pos)
            this.showToast(resources.getString(R.string.removed_favourites))
        }
        favouritesAdapter.shareListener = {
            val message =
                "Hey !! Please check this amazing post - \n\n" +
                        "${it.title} \n ${it.description} \n ${it.source} \n ${it.backgroundImage}"
            Utilities.showChooserForLinkShare(this, message)
        }

    }
}