package com.example.loginapp

import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loginapp.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.ktx.Firebase
import java.util.regex.Pattern


class SignUpFragment : Fragment() {


    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        binding.signIn.setOnClickListener {
            findNavController().navigate(R.id.action_signUpFragment_to_login2)
        }

        binding.signUp.setOnClickListener {
            val name = binding.name.text.toString().trim()
            val email = binding.emailReg.text.toString().trim()
            val password = binding.passReg.text.toString().trim()


            if (name.length > 1) {
                if (email.isValidEmail()) {
                    if (isValidPassword(password)) {
                        registerUser(name, email, password)
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Password must contain atleast one character from [a-z],[A-Z],[0-9],[#,!,@,$]",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireContext(), "Invaild email pattern", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Name cannot be less than length 1",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        auth = Firebase.auth

        return binding.root
    }

    val EMAIL_ADDRESS_PATTERN: Pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    fun String.isValidEmail() =
        !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun registerUser(name: String, email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                findNavController().navigate(R.id.action_signUpFragment_to_dashBoardFragment)
                it.result.user?.updateProfile(userProfileChangeRequest { displayName = name })
                Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT)
                    .show()
            }
            .addOnFailureListener {
                findNavController().navigate(R.id.action_signUpFragment_self)
                Toast.makeText(requireContext(), "Registration unsuccessful", Toast.LENGTH_SHORT)
                    .show()
            }

    }

    fun isValidPassword(password: String?): Boolean {
        val passwordREGEX = Pattern.compile(
            "^" +
                    "(?=.*[0-9])" +         //at least 1 digit
                    "(?=.*[a-z])" +         //at least 1 lower case letter
                    "(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{8,}" +               //at least 8 characters
                    "$"
        );
        return passwordREGEX.matcher(password).matches()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
