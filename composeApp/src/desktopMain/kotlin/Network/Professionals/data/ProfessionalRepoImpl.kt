package Network.Professionals.data

import Network.Professionals.domain.ProfessionalRepository
import com.example.Model.ProfessionalById
import com.example.Model.ProfessionalDataModel
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class ProfessionalRepoImpl(private val httpClient: HttpClient):ProfessionalRepository {

    override suspend fun getProfessionals(): Flow<UiState<List<ProfessionalDataModel>>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            trySend(UiState.SUCCESS(httpClient.get("/professionals").body()))

        }catch (e:Exception){
            trySend(UiState.ERROR(e))
        }

        awaitClose {
            close()
        }
    }

    override suspend fun getProfessionById(professionalId: String): Flow<UiState<ProfessionalById>> = callbackFlow {
        trySend(UiState.LOADING)

        try {
            trySend(UiState.SUCCESS(httpClient.get("/professionals/professionalById/$professionalId").body()))
        }catch (e:Exception){
            trySend(UiState.ERROR(e))
        }
        awaitClose {
            close()
        }
    }


}