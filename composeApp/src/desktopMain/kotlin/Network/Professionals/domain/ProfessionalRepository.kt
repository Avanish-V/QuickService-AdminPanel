package Network.Professionals.domain

import com.example.Model.ProfessionalById
import com.example.Model.ProfessionalDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow

interface ProfessionalRepository {

    suspend fun getProfessionals():Flow<UiState<List<ProfessionalDataModel>>>

    suspend fun getProfessionById(professionalId:String):Flow<UiState<ProfessionalById>>
}