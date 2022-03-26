package com.example.minerva.ui.details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.minerva.R
import com.example.minerva.data.model.Article
import com.example.minerva.databinding.DetailsFragmentBinding
import com.example.minerva.util.Constants.ARTICLE_KEY
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.details_fragment) {

    private var _binding: DetailsFragmentBinding? = null

    private val viewModel: DetailsViewModel by viewModels()

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val str = arguments?.getSerializable(ARTICLE_KEY) as Article

        setUI(str)
        return root
    }

    private fun setUI(str: Article) {
        binding.titleDetailsTextView.text = str.title
        binding.sourceDetailsTextView.text = str.source?.name
        binding.dateDetailsTextView.text = str.publishedAt?.substringBeforeLast("T")
        setFavouritebackground(str.isFavourite)

        binding.openSourceDetailsImageButton.setOnClickListener {
            if (str.url.isNotEmpty())
                viewModel.openAtricleSource(str.url)
            else
                Toast.makeText(context, getString(R.string.url_not_available), Toast.LENGTH_SHORT)
                    .show()
            Log.d("TAG", "setUI: ${str.url}")
        }

        binding.addFavouriteDetailsImageView.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                str.isFavourite = !str.isFavourite
                viewModel.insertIntoFavourite(str)
                setFavouritebackground(str.isFavourite)
            }
        }
    }

    private fun setFavouritebackground(favourite: Boolean) {
        if (favourite)
            binding.addFavouriteDetailsImageView.setImageResource(R.drawable.ic_added_favorite_24)
        else
            binding.addFavouriteDetailsImageView.setImageResource(R.drawable.ic_baseline_favorite_border_24)

    }


}