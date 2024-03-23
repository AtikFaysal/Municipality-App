package com.pourosova.data.core.common.dateparser

object DateTimeFormat {
    const val sqlYMDTHMSZ = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val sqlYMD = "yyyy-MM-dd"
    const val sqlYMDHMS = "yyyy-MM-dd HH:mm:ss"
    const val sqlYMDHM = "yyyy-MM-dd HH:mm"
    const val sqlHMS = "HH:mm:ss"
    const val sqlHM = "HH:mm"
    const val sqlhm = "hh:mm"

    const val outputDMy = "dd MMM yyyy"
    const val outputDMY = "dd - MMM - yyyy"
    const val outputYMD = "yyyy-MM-dd" // not to modify this is used to filter trips
    const val outputDM = "dd MMM"
    const val outputYMDHMSA = "dd MMM yyyy hh:mm:ss aa"
    const val outputDMYHMSA = "dd MMM yyyy hh:mm aa"
    const val outputDMYHMSAComma = "dd MMM yyyy, hh:mm aa"
    const val outputYMDHMA = "hh:mm aa, dd MMM yyyy"
    const val outputYMDHMALineBreak = "hh:mm aa\n dd MMM yyyy"
    const val outputYMDHMANoSpace = "hh:mmaa,dd MMMyyyy"
    const val outputDMYHMS = "dd MMM yyyy hh:mm:ss"
    const val outputYMDHMS = "yyyy-MM-dd HH:mm:ss"
    const val outputYMDHM = "yyyy-MM-dd HH:mm"
    const val outputHMSA = "hh:mm:ss aa"
    const val outputHMS = "hh:mm:ss"
    const val outputHMA = "hh:mm aa"

}