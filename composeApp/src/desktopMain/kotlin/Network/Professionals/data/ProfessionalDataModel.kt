package com.example.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfessionalDataModel(

    @SerialName("professionalId")val professionalId : String,
    @SerialName("professionalName")val professionalName : String,
    @SerialName("photoUrl")val photoUrl : String,

    @SerialName("mobile")val mobile : String,
    @SerialName("email")val email : String,
    @SerialName("adharNumber")val adharNumber : String,
    @SerialName("address")val address : String,
    @SerialName("profession")val profession : String,
    @SerialName("accountStatus")val accountStatus: AccountStatus

)
@Serializable
data class AccountStatus(
    @SerialName("active")val active : Boolean,
    @SerialName("status")val status : String
)

enum class Status{
    Pending,
    Active,
    Terminated
}

@Serializable
data class ProfessionalById(

    @SerialName("professionalId")val professionalId : String,
    @SerialName("professionalName")val professionalName : String,
    @SerialName("photoUrl")val photoUrl : String,

)