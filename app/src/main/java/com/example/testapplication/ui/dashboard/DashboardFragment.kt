package com.example.testapplication.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.data.AndroidDownloader
import com.example.testapplication.data.ImageHit
import com.example.testapplication.data.SQLiteManager
import com.example.testapplication.databinding.FragmentDashboardBinding
import com.example.testapplication.ui.home.HomeViewModel
import com.example.testapplication.ui.home.MyListAdapter

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: MyListAdapter
    private lateinit var downloader: AndroidDownloader

    private val viewModel: DashboardViewModel by viewModels {
        DashboardFactory(SQLiteManager(requireContext()))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        downloader = AndroidDownloader(requireContext())
        setupListView()
        viewModel.loadFavoriteImages()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.favoriteImages.observe(viewLifecycleOwner, Observer { newImageHits ->
            (binding.listViewFavorite.adapter as? MyListAdapter)?.updateData(newImageHits.toMutableList())
        })
    }
    private fun setupListView() {
        val dbHelper = SQLiteManager(requireContext())
        adapter = MyListAdapter(requireContext(), mutableListOf(), downloader, dbHelper)
        binding.listViewFavorite.adapter = adapter
    }

    private fun updateListView(imageHits: List<ImageHit>) {
        adapter.updateData(imageHits)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}