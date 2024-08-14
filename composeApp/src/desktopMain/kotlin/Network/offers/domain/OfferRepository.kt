package Network.offers.domain

import Network.offers.data.OfferDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow

interface OfferRepository {

    suspend fun createOffer(offerDataModel: OfferDataModel):Flow<UiState<Boolean>>

    suspend fun getOffersList():Flow<UiState<List<OfferDataModel>>>

    suspend fun updateStatus(status:Boolean,id:String):Flow<UiState<Boolean>>

    suspend fun deleteOffer(id:String):Flow<UiState<Boolean>>

}