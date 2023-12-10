package com.example.testapplication.ui.home

import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.databinding.FragmentHomeBinding
import com.example.testapplication.R
import com.example.testapplication.data.KittenImage
import com.example.testapplication.data.ListItem
import com.example.testapplication.data.api_interface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private var kittenImages: MutableList<KittenImage> = mutableListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val listView: ListView = binding.listViewHome

        /*val items = listOf(
            ListItem(R.drawable.cat_1, "Item 1"),
            ListItem(R.drawable.cat_2, "Item 2"),
        )

        val adapter = MyListAdapter(requireContext(), items)
        listView.adapter = adapter

        //val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, items)
        //listView.adapter = adapter

        //Handle list item clicks
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = items[position]
            Toast.makeText(requireContext(), "Selected: $selectedItem", Toast.LENGTH_SHORT).show()
        }*/

        // Initialize Retrofit and fetch data
        initRetrofitAndFetchData()
        return root
    }

    private fun initRetrofitAndFetchData() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://mlemapi.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(api_interface::class.java)

        apiService.getKittenImages().enqueue(object : Callback<KittenImage> {
            override fun onResponse(
                call: Call<KittenImage>,
                response: Response<KittenImage>
            ) {
                if (response.isSuccessful) {
                    Log.d("API Success", "Response: ${response.body()}")
                    updateListView(response.body())
                }
                else{
                    Log.e("API Error", "Response Code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<KittenImage>, t: Throwable) {
                Log.e("API Failure", "Error: ${t.message}")
            }

        })
    }

    private fun updateListView(newKittenImage: KittenImage?) {
        newKittenImage?.let {
            // Add the new image to the list
            kittenImages.add(it)

            // Update the list view with the new list
            val adapter = MyListAdapter(requireContext(), kittenImages)
            binding.listViewHome.adapter = adapter
            adapter.notifyDataSetChanged() // Notify the adapter about the data change
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}