package com.bangkit.catloris.ui.article

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.catloris.api.ApiNewConfig
import com.bangkit.catloris.databinding.FragmentArticleBinding
import com.bangkit.catloris.helper.ArticleAdapter
import com.bangkit.catloris.responses.ArticlesItem
import kotlinx.coroutines.launch
import java.util.Locale


class ArticleFragment : Fragment() {
    private var _binding: FragmentArticleBinding? = null
    private val binding get() = _binding!!

    private lateinit var articleAdapter: ArticleAdapter
    private var articleList = listOf<ArticlesItem>()

    private val SPEECH_REQUEST_CODE = 100


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArticleBinding.inflate(inflater, container, false)

        binding.voiceSearchBtn.setOnClickListener {
            startVoiceSpeech()
        }

        binding.articleSearch.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { filterArticles(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { filterArticles(it) }
                return true
            }

        })

        return binding.root
    }

    private fun startVoiceSpeech() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        }

        try {
            startActivityForResult(intent, SPEECH_REQUEST_CODE)
        } catch (e: Exception) {
            Toast.makeText(context, "Your device does not support speech input", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            if (!result.isNullOrEmpty()) {
                binding.articleSearch.setQuery(result[0], false)
            }
        }
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

                articleList = articles.filter {
                    it.title != "[Removed]"
                }
                articleAdapter = ArticleAdapter(requireContext(), articleList)
                binding.articleRecyclerView.adapter = articleAdapter
            } catch (e: Exception) {
                binding.articleProgressbar.visibility = View.GONE
                e.printStackTrace()
                Toast.makeText(requireContext(), "Article Failed to Load", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun filterArticles(query: String) {
        val filteredList = articleList.filter {
            it.title?.contains(query, ignoreCase = true) == true && it.title != "[Removed]"
        }
        articleAdapter.updateArticles(filteredList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}