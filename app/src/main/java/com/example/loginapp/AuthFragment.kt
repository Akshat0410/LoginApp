package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.loginapp.databinding.FragmentAuthBinding


class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

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


        return binding.root
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}
