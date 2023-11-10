package com.nimble.nimblesurveys.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.nimble.nimblesurveys.BuildConfig
import com.nimble.nimblesurveys.R
import com.nimble.nimblesurveys.data.remote.service.SessionManager
import com.nimble.nimblesurveys.databinding.FragmentLoginBinding
import com.nimble.nimblesurveys.model.user.LoginRequest
import com.nimble.nimblesurveys.utils.Constants
import com.nimble.nimblesurveys.utils.Resource
import com.nimble.nimblesurveys.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: TokenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentLoginBinding.inflate(layoutInflater)
        val view = binding.root

        binding.loginButton.setOnClickListener { onLoginPressed() }

        viewModel.loginResponse.observe(viewLifecycleOwner) {

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    context?.let { it1 ->
                        val sessionManager = SessionManager(it1)
                        it.data?.attributes?.let {
                            viewModel.saveSessionToken(
                                sessionManager,
                                it
                            )
                        }
                    }
                    binding.loading.visibility = View.GONE
                    goToActivity(HomeActivity::class.java)
                }

                Resource.Status.ERROR -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    binding.loading.visibility = View.GONE
                }

                Resource.Status.LOADING -> {
                    binding.loading.visibility = View.VISIBLE
                }
            }
        }

        return view
    }

    private fun goToActivity(activityToOpen: Class<out Activity?>) {
        val intent = Intent(context, activityToOpen)
        startActivity(intent)
    }

    private fun onLoginPressed() {

        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        if (!isEmailValid(email)) {
            return
        } else if (password.isNotEmpty()) {
            val loginRequest = LoginRequest(
                Constants.password,
                email,
                password,
                BuildConfig.CLIENT_ID,
                BuildConfig.CLIENT_SECRET
            )
            context?.let { viewModel.login(it, loginRequest) }
        } else {
            binding.passwordEditText.error = getString(R.string.please_enter_password)
        }

    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.emailEditText.error = null
            true
        } else {
            binding.emailEditText.error = getString(R.string.enter_valid_email)
            false
        }
    }

}