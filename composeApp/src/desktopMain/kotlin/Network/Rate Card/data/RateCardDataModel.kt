package Network.Rate

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class RateCardDataModel(

    @SerialName("title") val title : String,
    @SerialName("rateId")val rateId : String,
    @SerialName("rate")val rate : Int,
    @SerialName("applianceCategory")val applianceCategory:String
)
