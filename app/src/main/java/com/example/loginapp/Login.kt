package com.example.loginapp

import android.R.attr
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.loginapp.databinding.FragmentLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class Login : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()



        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        auth = Firebase.auth

        binding.signIn.setOnClickListener {

            val email=binding.email.text.toString().trim()
            val password=binding.password.text.toString().trim()

            loginUser(email,password)
        }

        binding.signUp.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_signUpFragment)
        }

        binding.googleButton.setOnClickListener {
            signIn()
        }


        return binding.root
    }

    private fun loginUser(email: String, password: String) {

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(OnCompleteListener<AuthResult?> { task ->
                if (task.isSuccessful) {
                   findNavController().navigate(R.id.action_login_to_dashBoardFragment)
                    Toast.makeText(requireContext(),"Login Successful",Toast.LENGTH_SHORT).show()
                } else {
                   findNavController().navigate(R.id.action_login_to_login)
                    Toast.makeText(requireContext(),"Wrong credentials",Toast.LENGTH_SHORT).show()
                }
            })

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account: GoogleSignInAccount = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account)
                binding.progressBar.visibility=View.VISIBLE
            } catch (e: ApiException) {
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener(requireActivity()) { authResult ->
                updateUI(auth.currentUser)
            }
            .addOnFailureListener(requireActivity()) { e ->
                Toast.makeText(
                    requireContext(), "Authentication failed.",
                    Toast.LENGTH_SHORT
                ).show()
            }

        binding.progressBar.visibility-View.GONE
    }

    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            findNavController().navigate(R.id.action_login_to_dashBoardFragment)
        } else {
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "LoginActivity"
        private const val RC_SIGN_IN = 9001
    }
}