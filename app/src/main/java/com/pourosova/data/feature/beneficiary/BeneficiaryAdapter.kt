package com.pourosova.data.feature.beneficiary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import com.pourosova.data.core.common.adapter.DataBoundListAdapter
import com.pourosova.data.core.common.extfun.clickWithDebounce
import com.pourosova.data.core.model.apientity.beneficiary.BeneficiaryApiEntity
import com.pourosova.data.databinding.ItemBeneficiaryBinding

class BeneficiaryAdapter (
    private val onClickItem: (ticket : BeneficiaryApiEntity) -> Unit,
    private val onCopy: (text : String) -> Unit,
) : DataBoundListAdapter<BeneficiaryApiEntity, ItemBeneficiaryBinding>(

    diffCallback = object : DiffUtil.ItemCallback<BeneficiaryApiEntity>() {

        override fun areItemsTheSame(oldItem: BeneficiaryApiEntity, newItem: BeneficiaryApiEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: BeneficiaryApiEntity, newItem: BeneficiaryApiEntity): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(parent: ViewGroup): ItemBeneficiaryBinding = ItemBeneficiaryBinding.inflate(
        LayoutInflater.from(parent.context), parent, false)

    override fun bind(binding: ItemBeneficiaryBinding, item: BeneficiaryApiEntity, position: Int) {
        binding.apply {
            serialTv.text = item.serialNo
            nameTv.text = item.name
            phoneTv.text = item.phone
            nidTv.text = item.nidNo
            villageTv.text = "Village: ${item.village}"
            wordNoTv.text = "Word no: ${item.wordNo}"
            fatherOrHusbandNameTv.text = item.fatherOrHusbandName

            root.clickWithDebounce {
                onClickItem.invoke(item)
            }

            serialTv.clickWithDebounce {
                onCopy.invoke(item.serialNo)
            }
        }
    }
}