package Network.Rate

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class RateCardViewModel(private val rateCardRepository: RateCardRepository):ViewModel() {

   private val _rateCardState : MutableStateFlow<RateCardResultState> = MutableStateFlow(RateCardResultState())
   val rateCardState : MutableStateFlow<RateCardResultState> = _rateCardState

   suspend fun createRateCard(rateCardDataModel: RateCardDataModel) = rateCardRepository.createRateCard(rateCardDataModel)

   fun getRateCard(applianceCategory:String){
      viewModelScope.launch {
         rateCardRepository.getRateCardList(applianceCategory).collect{
            when(it){
               is UiState.LOADING->{
                  _rateCardState.value = RateCardResultState(isLoading = true)
               }
               is UiState.SUCCESS->{
                  _rateCardState.value = RateCardResultState(rateCardList = it.response)
               }
               is UiState.ERROR->{
                  _rateCardState.value = RateCardResultState(error = it.throwable.message.toString())
               }
            }
         }
      }
   }

}

data class RateCardResultState(

   val isLoading : Boolean = false,
   val rateCardList : List<RateCardDataModel> = emptyList(),
   val error : String = ""

)