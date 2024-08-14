package SharedViewModel

import Network.Orders.data.OrdersDataModel
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import com.example.Model.ProfessionalDataModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedProfessionalViewModel : ViewModel() {

    private val _professionalData : MutableStateFlow<ProfessionalDataModel?> = MutableStateFlow(null)
    val professionalData = _professionalData.asStateFlow()


    fun parseProfessionalData(professionalDataModel: ProfessionalDataModel){
        _professionalData.value = professionalDataModel
    }


}