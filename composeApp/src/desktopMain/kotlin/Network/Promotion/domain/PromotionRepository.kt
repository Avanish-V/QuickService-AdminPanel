package Network.Promotion.domain

import Network.Promotion.data.PromotionDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow

interface PromotionRepository {

    suspend fun createPromotion(byteArray: ByteArray,categoryId:String,workType:String):Flow<UiState<Boolean>>

    suspend fun getPromotion():Flow<UiState<List<PromotionDataModel>>>
}