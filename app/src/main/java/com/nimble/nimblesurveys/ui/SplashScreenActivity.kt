package com.nimble.nimblesurveys.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.nimble.nimblesurveys.data.remote.service.SessionManager
import com.nimble.nimblesurveys.databinding.ActivitySplashscreenBinding
import com.nimble.nimblesurveys.model.user.TokenData
import com.nimble.nimblesurveys.utils.Resource
import com.nimble.nimblesurveys.viewmodel.TokenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity() {

    private val viewModel: TokenViewModel by viewModels()

    private lateinit var binding: ActivitySplashscreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.refreshResponse.observe(this) {
            handleRefresh(it)
        }

        if (viewModel.isLogged(this)) {
            if (viewModel.isTokenExpired(this)) {
                val sessionManager = SessionManager(this)
                val refreshRequest = viewModel.createRefreshRequest(sessionManager)
                viewModel.refresh(refreshRequest)
            } else {
                // TODO GO TO HOME SCREEN
            }
        } else {
            // TODO GO TO LOGIN SCREEN
        }
    }

    private fun handleRefresh(tokenResponse: Resource<TokenData>) {
        when (tokenResponse.status) {
            Resource.Status.SUCCESS -> {
                val sessionManager = SessionManager(this)
                tokenResponse.data?.attributes?.let {
                    viewModel.saveSessionToken(
                        sessionManager,
                        it
                    )
                }
                // TODO GO TO HOME SCREEN
            }

            Resource.Status.ERROR -> {
                Toast.makeText(this, tokenResponse.message, Toast.LENGTH_LONG).show()
                // TODO GO TO LOGIN SCREEN
            }

            Resource.Status.LOADING -> {
                // NO NEED FOR LOADING ANIMATION IN SPLASH SCREEN
            }
        }
    }
}