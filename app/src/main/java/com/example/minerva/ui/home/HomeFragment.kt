package com.example.minerva.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.minerva.R
import com.example.minerva.data.model.NewsDto
import com.example.minerva.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!
    lateinit var newsAdapter: NewsAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        newsAdapter = NewsAdapter(arrayListOf())
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.result
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .distinctUntilChanged()
                .collect { data ->
                    displayResult(data)
                }
        }
        initRecycler()

        return root
    }

    private fun displayResult(news: NewsDto) {
        lifecycle.coroutineScope.launch {
            Glide.with(binding.featuredHomeArticleImageView)
                .load(news.articles[0].urlToImage)
                .into(binding.featuredHomeArticleImageView)
        }
        binding.titleHomeTextView.text = news.articles[0].title
        newsAdapter.changeData(news.articles.subList(1, news.articles.size))
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