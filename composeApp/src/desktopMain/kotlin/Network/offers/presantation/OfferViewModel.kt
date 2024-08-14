package Network.offers.presantation

import Network.offers.data.OfferDataModel
import Network.offers.domain.OfferRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class OfferViewModel(private val offerRepository: OfferRepository) : ViewModel() {

    private val _state: MutableStateFlow<ResultState> = MutableStateFlow(ResultState())
    val state: MutableStateFlow<ResultState> = _state

   suspend fun createOffer(offerDataModel: OfferDataModel) = offerRepository.createOffer(offerDataModel)

    suspend fun updateStatus(status:Boolean,id:String) = offerRepository.updateStatus(status,id)

    suspend fun deleteOffer(id:String) = offerRepository.deleteOffer(id)

   fun getOfferList(){

       viewModelScope.launch {

           offerRepository.getOffersList().collect{

             when(it){
                 is UiState.LOADING->{
                     _state.value = ResultState(isLoading = true)
                 }
                 is UiState.SUCCESS->{
                     _state.value = ResultState(data = it.response)
                 }
                 is UiState.ERROR->{
                     _state.value = ResultState(error = it.throwable.message.toString())
                 }
             }
           }
       }

   }


}

data class ResultState(
    val isLoading : Boolean = false,
    val data :List<OfferDataModel?> = emptyList(),
    val error : String = ""
)