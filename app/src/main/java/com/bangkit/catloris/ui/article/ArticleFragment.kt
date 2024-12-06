package com.bangkit.catloris.ui.article

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catloris.R
import com.bangkit.catloris.api.ApiNewConfig
import com.bangkit.catloris.databinding.FragmentArticleBinding
import com.bangkit.catloris.helper.ArticleAdapter
import com.bangkit.catloris.responses.ArticlesItem
import kotlinx.coroutines.launch


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
    private var articleList = listOf<ArticlesItem>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerViewSetup()
        getArticleList()
    }

    private fun recyclerViewSetup() {
        binding.articleRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getArticleList() {
        binding.articleProgressbar.visibility = View.VISIBLE
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val apiService = ApiNewConfig.getApiService()
                val response = apiService.getHealthArticle()

                binding.articleProgressbar.visibility = View.GONE

                val articles = response.articles?.filterNotNull() ?: emptyList()

                articleList = articles
                articleAdapter = ArticleAdapter(requireContext(), articleList)
                binding.articleRecyclerView.adapter = articleAdapter
            } catch (e: Exception) {
                binding.articleProgressbar.visibility = View.GONE
                e.printStackTrace()
                Toast.makeText(requireContext(), "Article Gagal di Load", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}