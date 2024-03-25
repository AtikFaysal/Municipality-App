package com.pourosova.data.feature.updatebeneficiary

import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import co.jatri.common.utils.ImageUtils
import com.github.dhaval2404.imagepicker.ImagePicker
import com.pourosova.data.R
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.core.common.extfun.clickWithDebounce
import com.pourosova.data.core.common.extfun.getTextFromEt
import com.pourosova.data.core.common.extfun.showDatePickerDialog
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdateBeneficiaryApiUseCase
import com.pourosova.data.core.domain.apiusecase.beneficiary.UpdatePhotoApiUseCase
import com.pourosova.data.databinding.FragmentBeneficiaryBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BeneficiaryProfileFragment : BaseFragment<FragmentBeneficiaryBinding>() {
    private val imagePicker = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result != null) {
            lifecycleScope.launch {
                val bitmap = ImageUtils.resizeBitmapImage(ImageUtils.uriToBitmap(result.data?.data, requireContext()), 50f, true)
                bitmap?.let { image->
                    binding.imageIv.setImageBitmap(image)
                    val imageFile = ImageUtils.bitmapToFile(bitmap, requireContext(), "${System.currentTimeMillis()}")
                    val bitmapToBase64 = ImageUtils.bitmapToBase64Converter(image)
                    viewModel.action(UiAction.UpdatePhoto(
                        UpdatePhotoApiUseCase.Params(
                            image = bitmapToBase64,
                            nidOrSerial = args.data.nidNo
                        )
                    ))
                }
            }
        }
    }

    private val args by navArgs<BeneficiaryProfileFragmentArgs>()
    private val viewModel by viewModels<BeneficiaryProfileViewModel>()
    override fun viewBindingLayout(): FragmentBeneficiaryBinding = FragmentBeneficiaryBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
        setDataOnUi()
        binding.toolbar.toolbarTitleTv.text = getString(R.string.title_beneficiary_profile)

        binding.toolbar.toolbarBackIv.clickWithDebounce {
            findNavController().popBackStack()
        }

        binding.addPhotoTv.clickWithDebounce {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .createIntent {
                    imagePicker.launch(it)
                }
        }

        binding.dobEt.clickWithDebounce {
            requireActivity().showDatePickerDialog { selectedDate ->
                binding.dobEt.setText(selectedDate)
            }
        }

        binding.updateBtn.clickWithDebounce {
            if(binding.fatherHusbandNidEt.getTextFromEt().isEmpty()){
                showMessage("Husband/Father Name is required")
                binding.fatherHusbandNidEt.requestFocus()
                return@clickWithDebounce
            }

            if(binding.motherNameEt.getTextFromEt().isEmpty()){
                showMessage("Mother name is required")
                binding.motherNameEt.requestFocus()
                return@clickWithDebounce
            }

            viewModel.action(UiAction.UpdateBeneficiary(
                UpdateBeneficiaryApiUseCase.Params(
                    serial = args.data.serialNo,
                    phone = args.data.phone,
                    name = binding.nameEt.getTextFromEt(),
                    nid = args.data.nidNo,
                    dateOfBirth = binding.dobEt.getTextFromEt().ifEmpty { null },
                    occupation = binding.occupationEt.getTextFromEt().ifEmpty { null },
                   // fatherNid = binding.fatherNidEt.getTextFromEt(),
                    //husbandOrWifeNid = binding.husbandWifeNidEt.getTextFromEt().ifEmpty { null },
                    motherName = binding.motherNameEt.getTextFromEt(),
                    fatherOrHusband = binding.fatherHusbandNidEt.getTextFromEt(),
                   // village = binding.villageEt.getTextFromEt().ifEmpty { null },
                   // wordNo = binding.wordNoEt.getTextFromEt().ifEmpty { null }
                )
            ))
        }

        uiStateObserver()
    }

    private fun setDataOnUi(){
        binding.apply {
            serialTv.text = "Serial: ${args.data.serialNo}"
            nameEt.setText(args.data.name)
            nidEt.setText(args.data.nidNo)
            villageEt.setText(args.data.village)
            wordNoEt.setText(args.data.wordNo)
            phoneEt.setText(args.data.phone)
            motherNameEt.setText(args.data.motherName)
            occupationEt.setText(args.data.occupation)
            fatherNidEt.setText(args.data.fatherNid)
            dobEt.setText(args.data.dob)
            husbandWifeNidEt.setText(args.data.husbandWifeNid)
            fatherHusbandNidEt.setText(args.data.fatherOrHusbandName)
        }
    }

    private fun uiStateObserver(){
        viewModel.uiState.execute { state->
            when(state){
                is UiState.ApiError -> showMessage(state.message)
                is UiState.ApiSuccess -> {
                    showMessage("Beneficiary updated")
                    findNavController().popBackStack()
                }
                is UiState.Loading -> binding.progressBar.isVisible = state.loading
                is UiState.PhotoApiSuccess ->  showMessage("Photo updated")
            }
        }
    }
}