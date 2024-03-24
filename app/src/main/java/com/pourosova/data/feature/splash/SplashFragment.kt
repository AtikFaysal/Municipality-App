package com.pourosova.data.feature.splash

import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.core.common.extfun.navigateDestination
import com.pourosova.data.databinding.FragmentSplashBinding
import com.pourosova.data.sharedpref.SharedPrefHelper
import com.pourosova.data.sharedpref.SpKey
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    private val viewModel by viewModels<SplashViewModel>()

    @Inject
    lateinit var sharedPrefHelper : SharedPrefHelper
    override fun viewBindingLayout() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
        if(sharedPrefHelper.getBoolean(SpKey.loginStatus))
            viewModel.action(ProfileUiAction.PerformProfileApiAction)
        else navigateDestination(SplashFragmentDirections.actionSplashFragmentToLoginFragment())

        bindUiEvent()
    }

    private fun bindUiEvent(){
        viewModel.uiEvent.execute {event->
            when(event){
                is ProfileUiEvent.Loading -> binding.progressBar.isVisible = event.loading
                is ProfileUiEvent.ProfileApiError -> binding.root.dataEmptyLottieView { viewModel.action(
                    ProfileUiAction.PerformProfileApiAction
                ) }
                ProfileUiEvent.NavigateToHome -> navigateDestination(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
                is ProfileUiEvent.NavigateToLogin -> navigateDestination(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
            }
        }
    }
}