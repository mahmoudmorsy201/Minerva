package com.example.minerva.ui.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.example.minerva.databinding.FragmentFavouriteBinding
import com.example.minerva.util.onQueryTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavouriteFragment : Fragment() {

    private var _binding: FragmentFavouriteBinding? = null
    private val viewModel: FavouriteViewModel by viewModels()
    lateinit var favouriteArticlesAdapter: FavouriteArticlesAdapter

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        favouriteArticlesAdapter = FavouriteArticlesAdapter(arrayListOf(), {
            if (it != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isFavourite = false
                    viewModel.updateFavouriteArticle(it)
                }
            }
        },{
            viewModel.NavigateToDetails(it, binding.root)
        })

        initRecycler()
        displayResult()
        setSearchView()

        return root
    }

    private fun displayResult() {
        viewModel.searchedFavArticles.observe(viewLifecycleOwner) {
            favouriteArticlesAdapter.changeData(it)
        }
    }


    private fun setSearchView() {
        binding.searchFavouriteSearchView.onQueryTextChange {
            viewModel.searchQuery.value = it
        }
    }

    private fun initRecycler() {
        binding.favouriteArticleRecycleView.apply {
            adapter = favouriteArticlesAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}



