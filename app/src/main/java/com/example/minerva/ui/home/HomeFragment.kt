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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private lateinit var connectionLiveData: InternetConnectivity

    private var mainList: MutableList<Article> = arrayListOf()
    private var searchResultList: MutableList<Article> = arrayListOf()

    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.shimmerHome.visibility = View.VISIBLE
        binding.shimmerHome.startShimmer()
        connectionLiveData = InternetConnectivity(requireContext())


        newsAdapter = NewsAdapter(arrayListOf()) {
            if (it != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isFavourite = true
                    viewModel.insertIntoFavourite(it)
                }
            }
        }

        binding.searchHomeSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    searchResultList.clear()
                    searchResultList.addAll(
                        mainList.filter {
                            it.title!!.lowercase().contains(query.lowercase())
                        }
                    )
                    newsAdapter.changeData(searchResultList)
                } else {
                    newsAdapter.changeData(mainList)
                }
                searchResultList.clear()

                return true

            }

            override fun onQueryTextChange(query: String?): Boolean {
                if (query!!.isNotEmpty()) {
                    searchResultList.clear()
                    searchResultList.addAll(
                        mainList.filter {
                            it.title!!.lowercase().contains(query.lowercase())
                        }
                    )
                    newsAdapter.changeData(searchResultList)
                } else {
                    newsAdapter.changeData(mainList)
                }
                searchResultList.clear()

                return true
            }


        })
        newsAdapter = NewsAdapter(arrayListOf()) {
            if (it != null) {
                viewLifecycleOwner.lifecycleScope.launch {
                    it.isFavourite = true
                    viewModel.insertIntoFavourite(it)
                }
            }
        }


        initRecycler()

        connectionLiveData.observe(viewLifecycleOwner) { isAvailable ->
            when (isAvailable) {
                true -> viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.result
                        .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                        .distinctUntilChanged()
                        .collect { data ->
                            mainList.clear()
                            mainList.addAll(data.articles.subList(0, data.articles.size))
                            viewModel.getLocalArticles().asLiveData().observe(viewLifecycleOwner) {
                                displayResult(it)

                            }
                        }
                }

                false -> {
                    viewModel.getLocalArticles().asLiveData().observe(viewLifecycleOwner) {
                        newsAdapter.changeData(it)
                    }
                }
            }
        }

        return root
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

        binding.shimmerHome.stopShimmer()
        binding.shimmerHome.visibility = View.GONE
        binding.scrollviewHome.visibility = View.VISIBLE
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