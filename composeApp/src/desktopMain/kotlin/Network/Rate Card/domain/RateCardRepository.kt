package Network.Rate

import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow

interface RateCardRepository {


    suspend fun createRateCard(rateCardDataModel: RateCardDataModel) : Flow<UiState<Boolean>>


    suspend fun getRateCardList(applianceCategory:String) : Flow<UiState<List<RateCardDataModel>>>


}