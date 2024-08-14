package Network.Promotion.presantation

import Network.Promotion.data.PromotionDataModel
import Network.Promotion.domain.PromotionRepository
import Network.offers.data.OfferDataModel
import androidx.lifecycle.DEFAULT_ARGS_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.swing.text.View

class PromotionViewModel(private val promotionRepository: PromotionRepository) : ViewModel() {

    private val _promotionDataState:MutableStateFlow<ResultState> = MutableStateFlow(ResultState())
    val promotionDataState : MutableStateFlow<ResultState> = _promotionDataState

    suspend fun createPromotion(imageByteArray: ByteArray,category:String,workType:String) = promotionRepository.createPromotion(imageByteArray,category,workType)

    fun getPromotion(){
        viewModelScope.launch {
            promotionRepository.getPromotion().collect{
                when(it){
                    is UiState.LOADING->{
                        _promotionDataState.value = ResultState(isLoading =  true)
                    }
                    is UiState.SUCCESS->{
                        _promotionDataState.value = ResultState(data = it.response)
                    }
                    is UiState.ERROR->{
                        _promotionDataState.value = ResultState(error = it.throwable.message.toString())
                    }
                }
            }
        }

    }

}
data class ResultState(
    val isLoading : Boolean = false,
    val data :List<PromotionDataModel?> = emptyList(),
    val error : String = ""
)