package com.example.testapplication.ui.home

import android.os.Bundle
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
import com.example.testapplication.data.ListItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

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
        //val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")
        val items = listOf(
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
        }
        return root
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}