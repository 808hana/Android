package com.example.testapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.PixabayResponse
import com.example.testapplication.api.api_interface
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initRetrofitAndFetchData()
        return root
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
                }
                else{
                    /*val defaultImage = ImageHit(
                        id = -1,  // -1 or another distinctive value to indicate a default image
                        imageURL = "https://img.freepik.com/free-photo/gray-kitty-with-monochrome-wall-her_23-2148955126.jpg?t=st=1702217947~exp=1702218547~hmac=873d4b8be07bcf3883fb36dc6b2cbfd50099b5f5925a1fe90d23ff29d03c92bd",  // Replace with your default image URL
                        type = "photo"
                    )
                    imageHits.clear()
                    imageHits.add(defaultImage)
                    updateListViewWithDefaultImage()*/
                }
            }

            override fun onFailure(call: Call<PixabayResponse>, t: Throwable) {
                // Handle failure
            }
        })
    }

    private fun updateListView(newPixabayResponse: PixabayResponse?) {
        newPixabayResponse?.hits?.let { newHits ->
            // Assuming 'imageHits' is a mutable list of ImageHit objects that your adapter uses
            imageHits.addAll(newHits)

            // Update the adapter with the new list
            if (binding.listViewHome.adapter == null) {
                // If the adapter hasn't been initialized yet, create and set a new adapter
                val adapter = MyListAdapter(requireContext(), imageHits)
                binding.listViewHome.adapter = adapter
            } else {
                // If the adapter is already initialized, refresh the adapter's data
                (binding.listViewHome.adapter as? MyListAdapter)?.apply {
                    notifyDataSetChanged()
                }
            }
        }
    }

    private fun updateListViewWithDefaultImage() {
        // Update the adapter with the new list
        if (binding.listViewHome.adapter == null) {
            // If the adapter hasn't been initialized yet, create and set a new adapter
            val adapter = MyListAdapter(requireContext(), imageHits)
            binding.listViewHome.adapter = adapter
        } else {
            // If the adapter is already initialized, refresh the adapter's data
            (binding.listViewHome.adapter as? MyListAdapter)?.apply {
                notifyDataSetChanged()
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}