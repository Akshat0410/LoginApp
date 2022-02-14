package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.loginapp.databinding.FragmentAuthBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)

        binding.login.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_login)
        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_authFragment_to_signUpFragment)
        }

        auth= Firebase.auth

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            findNavController().navigate(R.id.action_authFragment_to_dashBoardFragment)
        } else {
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
