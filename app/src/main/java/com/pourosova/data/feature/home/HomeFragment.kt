package com.pourosova.data.feature.home

import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.pourosova.data.R
import com.pourosova.data.base.BaseFragment
import com.pourosova.data.core.common.extfun.clickWithDebounce
import com.pourosova.data.core.common.extfun.navigateDestination
import com.pourosova.data.core.model.apientity.utils.DistrictApiEntity
import com.pourosova.data.core.model.apientity.utils.DivisionApiEntity
import com.pourosova.data.core.model.apientity.utils.MunicipalityApiEntity
import com.pourosova.data.core.model.apientity.utils.SubDistrictApiEntity
import com.pourosova.data.core.ui.createDropDownAdapter
import com.pourosova.data.databinding.FragmentHomeBinding
import com.pourosova.data.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private val viewModel by viewModels<HomeVIewModel>()
    override fun viewBindingLayout(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun initializeView(savedInstanceState: Bundle?) {
        binding.toolbar.toolbarTitleTv.text = getString(R.string.title_home)
        uiStateObserver()

        binding.divisionAtv.onItemSelected()
        binding.districtAtv.onItemSelected()
        binding.subDistrictAtv.onItemSelected()
        binding.municipalityAtv.onItemSelected()
        bindUiEvent()

        binding.logoutTv.clickWithDebounce {
            viewModel.action(UiAction.Logout)
        }

        binding.seeBeneficiaryTv.clickWithDebounce {
            navigateDestination(HomeFragmentDirections.actionHomeFragmentToBeneficiaryListFragment(viewModel.municipalityId))
        }
    }

    private fun uiStateObserver(){
        viewModel.uiState.execute { state->
            when(state){
                is UiState.District -> setDistrictAdapter(state.items)
                is UiState.Division -> setDivisionAdapter(state.items)
                is UiState.Error -> showMessage(state.message)
                is UiState.Loading -> binding.progressBar.isVisible = state.loading
                is UiState.Municipality -> setMunicipalityAdapter(state.items)
                is UiState.SubDistrict -> setSubDistrictAdapter(state.items)
            }
        }
    }

    private fun bindUiEvent(){
        viewModel.uiEvent.execute { event->
            when(event){
                UiEvent.NavigateToLogin -> navigateDestination(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }
        }
    }

    private fun setDivisionAdapter(items : List<DivisionApiEntity>){
        val adapter = createDropDownAdapter(requireActivity(), items)
        binding.divisionAtv.setText(items[0].name)
        binding.divisionAtv.setAdapter(adapter)
        viewModel.divisionId = items[0].id
    }

    private fun setDistrictAdapter(items : List<DistrictApiEntity>){
        val adapter = createDropDownAdapter(requireActivity(), items)
        binding.districtAtv.setText(items[0].name)
        binding.districtAtv.setAdapter(adapter)
        viewModel.districtId = items[0].id
    }

    private fun setSubDistrictAdapter(items : List<SubDistrictApiEntity>){
        val adapter = createDropDownAdapter(requireActivity(), items)
        binding.subDistrictAtv.setText(items[0].name)
        binding.subDistrictAtv.setAdapter(adapter)
        viewModel.subDistrictId = items[0].id
    }

    private fun setMunicipalityAdapter(items : List<MunicipalityApiEntity>){
        val adapter = createDropDownAdapter(requireActivity(), items)
        binding.municipalityAtv.setText(items[0].name)
        binding.municipalityAtv.setAdapter(adapter)
        viewModel.municipalityId = items[0].id
    }

    private fun AutoCompleteTextView.onItemSelected(){
        this.setOnItemClickListener { _, _, position, _ ->
            when(this.id){
                binding.divisionAtv.id-> {
                    val divisionId = (this.adapter.getItem(position) as DivisionApiEntity).id
                    viewModel.action(UiAction.FetchDistrict(divisionId))
                    viewModel.divisionId = divisionId
                }
                binding.districtAtv.id -> {
                    val districtId = (this.adapter.getItem(position) as DistrictApiEntity).id
                    viewModel.action(UiAction.FetchSubDistrict(districtId))
                    viewModel.districtId = districtId
                }

                binding.subDistrictAtv.id -> {
                    val subDistrictId = (this.adapter.getItem(position) as SubDistrictApiEntity).id
                    viewModel.action(UiAction.FetchMunicipality(subDistrictId))
                    viewModel.subDistrictId = subDistrictId
                }

                binding.municipalityAtv.id -> {
                    val municipalityId = (this.adapter.getItem(position) as MunicipalityApiEntity).id
                    viewModel.municipalityId = municipalityId
                }
            }
        }
    }
}