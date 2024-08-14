package Network.Products.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ProductDataModel(


    @SerialName("serviceTitle")val serviceTitle:String,
    @SerialName("imageUrl")val imageUrl:String,
    @SerialName("serviceId")val serviceId:String,
    @SerialName("serviceTAG")val serviceTAG:String,
    @SerialName("workType")val workType:String,
    @SerialName("price")val price:Int,
    @SerialName("tax")val tax:Int,
    @SerialName("description")val description : List<Description>,
    @SerialName("rating")val rating : Rating

)

@Serializable
data class Description(
    @SerialName("title")val title :String = "",
    @SerialName("comment")val comment : String = ""
)

@Serializable
data class Rating(
     @SerialName("rating")val rating : String = "0",
     @SerialName("count")val count : String = "0",
     @SerialName("star_1")val star_1 : String = "0",
     @SerialName("star_2")val star_2 : String = "0",
     @SerialName("star_3")val star_3 : String = "0",
     @SerialName("star_4")val star_4 : String = "0",
     @SerialName("star_5")val star_5 : String = "0"
)



