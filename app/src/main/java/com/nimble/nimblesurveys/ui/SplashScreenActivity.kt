package com.nimble.nimblesurveys.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
                goToActivity(HomeActivity::class.java)
            }
        } else {
            goToActivity(LoginActivity::class.java)
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
                goToActivity(HomeActivity::class.java)
            }

            Resource.Status.ERROR -> {
                Toast.makeText(this, tokenResponse.message, Toast.LENGTH_LONG).show()
                goToActivity(LoginActivity::class.java)
            }

            Resource.Status.LOADING -> {
                // NO NEED FOR LOADING ANIMATION IN SPLASH SCREEN
            }
        }
    }

    private fun goToActivity(activityToOpen: Class<out Activity?>) {
        val intent = Intent(this, activityToOpen)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent)
    }
}