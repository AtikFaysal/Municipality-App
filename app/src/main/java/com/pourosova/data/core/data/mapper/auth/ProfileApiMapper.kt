package com.pourosova.data.core.data.mapper.auth

import com.pourosova.data.core.data.mapper.Mapper
import com.pourosova.data.core.model.apientity.auth.ProfileApiEntity
import com.pourosova.data.core.model.apiresponse.auth.ProfileApiResponse
import com.pourosova.data.sharedpref.SharedPrefHelper
import com.pourosova.data.sharedpref.SpKey
import javax.inject.Inject

class ProfileApiMapper @Inject constructor() : Mapper<ProfileApiResponse, ProfileApiEntity> {
    override fun mapFromApiResponse(type: ProfileApiResponse): ProfileApiEntity {
        return ProfileApiEntity(
            email = type.data?.email ?: "",
            name = type.data?.name ?: "",
            phone = type.data?.phone ?: "",
            profilePic = type.data?.profile_pic ?: ""
        )
    }
}


class CacheProfile@Inject constructor(
    private val sharedPrefHelper: SharedPrefHelper
){
    fun cacheProfile(profileData : ProfileApiEntity){
        with(profileData){
            sharedPrefHelper.putString(SpKey.ProfileData.name, name)
            sharedPrefHelper.putString(SpKey.ProfileData.avatar, profilePic)
            sharedPrefHelper.putString(SpKey.ProfileData.email, email)
            sharedPrefHelper.putString(SpKey.ProfileData.userPhone, phone)
        }
    }
}