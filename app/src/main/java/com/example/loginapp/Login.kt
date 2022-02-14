package com.example.loginapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.loginapp.databinding.FragmentLoginBinding


class Login : Fragment() {

    private var _binding : FragmentLoginBinding? = null
    private val binding get()= _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentLoginBinding.inflate(inflater,container,false)

        binding.signIn.setOnClickListener {

        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUpFragment)
        }

        binding.googleButton.setOnClickListener {

        }


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}