package com.pourosova.data.feature.login

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.widget.EditText
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import com.pourosova.data.BuildConfig
import com.pourosova.data.R
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.core.common.extfun.clickWithDebounce
import com.pourosova.data.core.common.extfun.getTextFromEt
import com.pourosova.data.core.common.extfun.navigateDestination
import com.pourosova.data.core.domain.apiusecase.auth.LoginApiUseCase
import com.pourosova.data.core.domain.base.LoginIoResult
import com.pourosova.data.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(){
    private val viewModel by viewModels<LoginViewModel>()
    override fun viewBindingLayout(): FragmentLoginBinding = FragmentLoginBinding.inflate(layoutInflater)

    @SuppressLint("SetTextI18n")
    override fun initializeView(savedInstanceState: Bundle?) {
        binding.phoneNoET.onTextChange()
        binding.passwordET.onTextChange()

        bindUiEvent()
        onClickListener()

        viewModel.loginError bindTo ::bindUiError

    }

    private fun bindUiEvent(){
        viewModel.uiEvent.execute { event->
            when(event){
                is LoginUiEvent.Loading -> binding.root.progressbarView(event.loading)
                is LoginUiEvent.LoginApiError -> {
                    binding.loginFailedTv.isVisible = true
                    binding.loginFailedTv.text = event.message
                }
                is LoginUiEvent.ProfileApiError -> binding.root.dataEmptyLottieView { viewModel.action(LoginUiAction.FetchProfile) }
                LoginUiEvent.ProfileApiSuccess -> navigateDestination(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
            }
        }
    }

    private fun bindUiError(error : LoginIoResult){
        when(error){
            LoginIoResult.InvalidPassword -> {
                binding.passwordErrorTv.isVisible = true
                binding.passwordErrorTv.text = getString(R.string.error_enter_min_password)
            }
            LoginIoResult.InvalidEmail -> {
                binding.phoneErrorTv.isVisible = true
                binding.phoneErrorTv.text = getString(R.string.error_invalid_email)
            }
        }
    }

    private fun onClickListener(){
        binding.loginBtn.clickWithDebounce {
            binding.loginFailedTv.isVisible = false
            binding.phoneErrorTv.isVisible = false
            binding.passwordErrorTv.isVisible = false
            viewModel.action(LoginUiAction.PerformLoginApiAction(
                LoginApiUseCase.Params(
                    email = binding.phoneNoET.getTextFromEt(),
                    password = binding.passwordET.getTextFromEt()
                )
            ))
        }

        binding.passVisibleIv.clickWithDebounce { showHidePassword(true) }

        binding.passInVisibleIv.clickWithDebounce { showHidePassword(false) }
    }

    private fun showHidePassword(isShow : Boolean){
        binding.passVisibleIv.isVisible = !isShow
        binding.passInVisibleIv.isVisible = isShow
        binding.passwordET.transformationMethod = if(isShow) PasswordTransformationMethod() else null
    }

    private fun EditText.onTextChange(){
        this.doAfterTextChanged{
            when(this.id){
                binding.phoneNoET.id -> binding.phoneErrorTv.isVisible = false
                binding.passwordET.id -> binding.passwordErrorTv.isVisible = false
            }
        }
    }
}