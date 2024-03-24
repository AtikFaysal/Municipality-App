package com.pourosova.data.feature.beneficiary

import android.os.Bundle
import android.widget.EditText
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.jatri.common.utils.autoCleared
import com.pourosova.data.R
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.core.common.extfun.clickWithDebounce
import com.pourosova.data.core.common.extfun.getTextFromEt
import com.pourosova.data.core.common.extfun.hideKeyboard
import com.pourosova.data.core.common.extfun.navigateDestination
import com.pourosova.data.core.common.extfun.setClipboard
import com.pourosova.data.core.common.extfun.setUpVerticalRecyclerView
import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchAllBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.FetchBeneficiaryApiUseCase
import com.pourosova.data.databinding.FragmentBeneficiaryListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BeneficiaryListFragment : BaseFragment<FragmentBeneficiaryListBinding>() {

    private val args by navArgs<BeneficiaryListFragmentArgs>()
    private var isSearch = false
    override fun viewBindingLayout(): FragmentBeneficiaryListBinding =
        FragmentBeneficiaryListBinding.inflate(layoutInflater)

    private val viewModel by viewModels<BeneficiaryViewModel>()
    private var adapter by autoCleared<BeneficiaryAdapter>()

    override fun initializeView(savedInstanceState: Bundle?) {
        binding.toolbar.toolbarTitleTv.text = getString(R.string.title_beneficiary_list)
        adapter = BeneficiaryAdapter(
            onClickItem = {
                          navigateDestination(BeneficiaryListFragmentDirections.actionBeneficiaryListFragmentToBeneficiaryProfileFragment(it))
            },
            onCopy = { text ->
                requireContext() setClipboard text
                showToastMessage(getString(R.string.message_text_copied))
            })
        requireContext().setUpVerticalRecyclerView(binding.itemsRv, adapter)

        onClickListener()
        bindUiState()
        viewModel.action(UiAction.FetchAllBeneficiary(FetchAllBeneficiaryApiUseCase.Params(args.id)))

        binding.searchEt.onTextChange()
    }

    override fun onDestroyView() {
        requireActivity().hideKeyboard()
        super.onDestroyView()
    }

    private fun bindUiState() {
        viewModel.uiState.execute { state ->
            when (state) {
                is UiState.Loading -> binding.listCl.progressBarLottieView(state.loading)
                is UiState.ApiError, UiState.DataEmpty -> binding.listCl.dataEmptyLottieView {
                    binding.listCl.dataEmptyView(message = "No data found"){
                        viewModel.action(UiAction.FetchAllBeneficiary(FetchAllBeneficiaryApiUseCase.Params(args.id)))
                    }
                }

                is UiState.ApiSuccess -> {
                    adapter.submitList(state.content)
                    adapter.notifyItemRangeChanged(0, adapter.itemCount)
                }
            }
        }
    }

    private fun onClickListener() {
        binding.searchIv.clickWithDebounce {
            if(binding.searchEt.getTextFromEt().isNotEmpty()){
                binding.searchEt.hideKeyboard()
                viewModel.action(UiAction.FetchBeneficiary(FetchBeneficiaryApiUseCase.Params(
                    municipalityId = args.id,
                    nidOrSerial = binding.searchEt.getTextFromEt()
                )))

                isSearch = true
            }
        }

        binding.toolbar.toolbarBackIv.clickWithDebounce {
            findNavController().popBackStack()
        }
    }

    private fun EditText.onTextChange(){
        this.doAfterTextChanged {
            if(this.getTextFromEt().isEmpty() && isSearch){
                isSearch = false
                viewModel.action(UiAction.FetchAllBeneficiary(FetchAllBeneficiaryApiUseCase.Params(args.id)))
            }
        }
    }
}