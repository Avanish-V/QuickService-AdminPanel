package Network.Rate

import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class RateCardRepoImpl(private val httpClient: HttpClient) : RateCardRepository {

    override suspend fun createRateCard(rateCardDataModel: RateCardDataModel): Flow<UiState<Boolean>> = callbackFlow {

        trySend(UiState.LOADING)

        try {
            val response = httpClient.post("/rateCard/createRateCard"){
                contentType(ContentType.Application.Json)
                setBody(rateCardDataModel)
            }

            if (response.status.value in 200..299){
                trySend(UiState.SUCCESS(true))
            }else{
                trySend(UiState.SUCCESS(false))
            }
        }catch (e:Exception){
            trySend(UiState.ERROR(e))
        }

        awaitClose{
            close()
        }
    }

    override suspend fun getRateCardList(applianceCategory: String): Flow<UiState<List<RateCardDataModel>>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            trySend(UiState.SUCCESS(httpClient.get("/rateCard/$applianceCategory").body()))

        }catch (e:Exception){

            trySend(UiState.ERROR(e))

        }

        awaitClose {
            close()
        }

    }



}