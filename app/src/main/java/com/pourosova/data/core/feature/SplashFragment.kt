package com.pourosova.data.core.feature

import android.os.Bundle
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding>() {
    override fun viewBindingLayout() = FragmentSplashBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {

    }
}