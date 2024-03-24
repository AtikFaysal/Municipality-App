package com.pourosova.data.core.model.apiresponse.beneficiary

data class UpdatePhotoApiResponse(
    val `data`: UpdatePhoto?,
    val status: Boolean?
)

data class UpdatePhoto(
    val image_url: String?
)