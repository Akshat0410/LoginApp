package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.loginapp.databinding.FragmentDashBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class DashBoardFragment : Fragment() {

    private var _binding: FragmentDashBoardBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashBoardBinding.inflate(inflater, container, false)

        auth=Firebase.auth
        setProfileData()
        return binding.root
    }

    private fun setProfileData() {
        Glide.with(requireContext())
            .load(auth.currentUser?.photoUrl)
            .placeholder(R.drawable.ic_baseline_person_24)
            .into(binding.profileImage)

        binding.userName.setText(auth.currentUser?.displayName.toString())
    }

    override fun onStart() {
        super.onStart()
        auth=Firebase.auth
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}