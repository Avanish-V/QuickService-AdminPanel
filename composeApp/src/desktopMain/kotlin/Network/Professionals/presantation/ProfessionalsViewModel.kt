package Network.Professionals.presantation

import Network.Professionals.domain.ProfessionalRepository
import Network.Promotion.data.PromotionDataModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Model.ProfessionalById
import com.example.Model.ProfessionalDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProfessionalsViewModel(private val professionalRepository: ProfessionalRepository):ViewModel() {

    private val _professionalsResultState:MutableStateFlow<ProfessionalsResultState> = MutableStateFlow(ProfessionalsResultState())
    val professionalsResultState : MutableStateFlow<ProfessionalsResultState> = _professionalsResultState

    private val _professionalsByIdResultState:MutableStateFlow<ProfessionalsByIdResultState> = MutableStateFlow(ProfessionalsByIdResultState())
    val professionalsByIdResultState : MutableStateFlow<ProfessionalsByIdResultState> = _professionalsByIdResultState

    fun getProfessionals(){
        viewModelScope.launch {
            professionalRepository.getProfessionals().collect{
                when(it){
                    is UiState.LOADING->{
                      _professionalsResultState.value = ProfessionalsResultState(isLoading = true)
                    }
                    is UiState.SUCCESS->{
                        _professionalsResultState.value = ProfessionalsResultState(data = it.response)
                    }
                    is UiState.ERROR->{
                        _professionalsResultState.value = ProfessionalsResultState(error = it.throwable.message.toString())
                    }
                }
            }
        }
    }

    fun getProfessionalsById(professionalId:String){
        viewModelScope.launch {
            professionalRepository.getProfessionById(professionalId).collect{
                when(it){
                    is UiState.LOADING->{
                        _professionalsByIdResultState.value = ProfessionalsByIdResultState(isLoading = true)
                    }
                    is UiState.SUCCESS->{
                        _professionalsByIdResultState.value = ProfessionalsByIdResultState(data = it.response)
                    }
                    is UiState.ERROR->{
                        _professionalsByIdResultState.value = ProfessionalsByIdResultState(error = it.throwable.message.toString())
                    }
                }
            }
        }
    }



}


data class ProfessionalsResultState(
    val isLoading : Boolean = false,
    val data :List<ProfessionalDataModel?> = emptyList(),
    val error : String = ""
)

data class ProfessionalsByIdResultState(
    val isLoading : Boolean = false,
    val data : ProfessionalById? = null,
    val error : String = ""
)