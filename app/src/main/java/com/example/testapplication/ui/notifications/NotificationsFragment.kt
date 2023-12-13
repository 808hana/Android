package com.example.testapplication.ui.notifications

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.testapplication.databinding.FragmentNotificationsBinding

class NotificationsFragment : Fragment() {

    private var _binding: FragmentNotificationsBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<NotificationsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.updateButton.setOnClickListener {
            showUpdateDialog()
        }

        return root
    }

    private fun showUpdateDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Check for Updates")
        builder.setMessage("No new updates")
        builder.setPositiveButton("Got it") { dialog, which ->
            //viewModel.checkForUpdates()
            dialog.dismiss()
        }
        /*builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }*/

        val dialog = builder.create()
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}