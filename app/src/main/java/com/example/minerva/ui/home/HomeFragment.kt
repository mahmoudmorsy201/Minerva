package com.example.minerva.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.data.model.NewsDto
import com.example.minerva.databinding.FragmentHomeBinding

import com.example.minerva.util.InternetConnectivity
import com.example.minerva.util.onQueryTextChange
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var connectionLiveData: InternetConnectivity
    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        connectionLiveData = InternetConnectivity(requireContext())


        newsAdapter = NewsAdapter(arrayListOf()) {
            if (it != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isFavourite = true
                    viewModel.insertIntoFavourite(it)
                }
            }
        }



        newsAdapter = NewsAdapter(arrayListOf()) {
            if (it != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isFavourite = true
                    viewModel.insertIntoFavourite(it)
                }
            }
        }


        initRecycler()
        setSearchView()

        connectionLiveData.observe(viewLifecycleOwner) { isAvailable ->
            when (isAvailable) {
                true -> viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.result
                        .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                        .distinctUntilChanged()
                        .collect { data ->

                            viewModel.searchedArticles.observe(viewLifecycleOwner) {
                                if (it.isEmpty()) {
                                    displayResult(data.articles)
                                } else {
                                    displayResult(it)
                                }
                            }
                        }
                }

                false -> {
                    viewModel.searchedArticles.observe(viewLifecycleOwner) {
                        newsAdapter.changeData(it)
                    }
                }
            }
        }
        return root
    }

    private fun setSearchView() {
        binding.searchHomeSearchView.onQueryTextChange {
            viewModel.searchQuery.value = it
        }
    }

    private fun displayResult(news: List<Article>) {
        lifecycle.coroutineScope.launch {
            Glide.with(binding.featuredHomeArticleImageView)
                .load(news[0].urlToImage)
                .placeholder(R.drawable.images)
                .into(binding.featuredHomeArticleImageView)
        }
        binding.titleHomeTextView.text = news[0].title
        newsAdapter.changeData(news.subList(1, news.size))
    }


    private fun initRecycler() {
        binding.articleRecycleView.apply {
            adapter = newsAdapter
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}