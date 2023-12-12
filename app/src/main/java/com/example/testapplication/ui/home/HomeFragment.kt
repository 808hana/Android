package com.example.testapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

    private val imageHits: MutableList<ImageHit> = mutableListOf()
    private lateinit var adapter: MyListAdapter
    private lateinit var downloader: AndroidDownloader

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        downloader = AndroidDownloader(requireContext())
        setupListView()
        initRetrofitAndFetchData()
        return root
    }

    private fun setupListView() {
        adapter = MyListAdapter(requireContext(), imageHits, downloader)
        binding.listViewHome.adapter = adapter
    }
    private fun initRetrofitAndFetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://pixabay.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(api_interface::class.java)
        val call = apiService.searchImages("41183002-bf3427640d13d18680465e50d", "yellow+flowers", "photo")

        call.enqueue(object : Callback<PixabayResponse> {
            override fun onResponse(call: Call<PixabayResponse>, response: Response<PixabayResponse>) {
                if (response.isSuccessful) {
                    updateListView(response.body())
                } else {
                }
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                // Handle failure, e.g., show an error message
            }
        })
    }


    private fun updateListView(newPixabayResponse: PixabayResponse?) {
        newPixabayResponse?.hits?.let { newHits ->
            imageHits.addAll(newHits)
            if (binding.listViewHome.adapter == null) {
                val adapter = MyListAdapter(requireContext(), imageHits, downloader)
                binding.listViewHome.adapter = adapter
            } else {
                (binding.listViewHome.adapter as? MyListAdapter)?.apply {
                    notifyDataSetChanged()
                }
            }
            /*imageHits.clear()
            imageHits.addAll(newHits)
            adapter.notifyDataSetChanged()*/
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}