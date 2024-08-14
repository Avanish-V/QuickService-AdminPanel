package Network.Category.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer

@Serializable
data class CategoryDataModel(
    @SerialName("categoryTitle") val categoryTitle: String,
    @SerialName("categoryId") val categoryId: String,
    @SerialName("imageUrl") val imageUrl: String,
    @SerialName("type") val type: String? = null,

)