package com.example.testapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.PixabayResponse
import com.example.testapplication.api.api_interface
import com.example.testapplication.data.AndroidDownloader
import com.example.testapplication.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyListAdapter
    private lateinit var downloader: AndroidDownloader

    private val viewModel by viewModels<HomeViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        downloader = AndroidDownloader(requireContext())
        setupListView()

        viewModel.searchImages("cat")
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.images.observe(viewLifecycleOwner, Observer { newImageHits ->
            // Update the adapter with the new data
            (binding.listViewHome.adapter as? MyListAdapter)?.updateData(newImageHits.toMutableList())
        })

        binding.ImageSearchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    if (it.isNotEmpty()) {
                        //performSearch(it)
                        viewModel.searchImages(it)
                    }
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun setupListView() {
        adapter = MyListAdapter(requireContext(), mutableListOf(), downloader)
        binding.listViewHome.adapter = adapter
    }

    private fun updateListView(imageHits: List<ImageHit>) {
        // Update the adapter with new data
        adapter.updateData(imageHits)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}