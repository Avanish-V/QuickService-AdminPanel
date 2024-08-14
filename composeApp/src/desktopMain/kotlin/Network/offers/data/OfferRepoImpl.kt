package Network.offers.data

import Network.offers.domain.OfferRepository
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class
OfferRepoImpl(private val httpClient: HttpClient) : OfferRepository {


    override suspend fun createOffer(offerDataModel: OfferDataModel): Flow<UiState<Boolean>> =
        callbackFlow {

            trySend(UiState.LOADING)

            try {

                val result = httpClient.post("/offer/create"){
                    contentType(io.ktor.http.ContentType.Application.Json)
                    setBody(offerDataModel)
                }
                if (result.status.value in 200..299) {
                    trySend(UiState.SUCCESS(true))
                } else {
                    trySend(UiState.ERROR(Exception("HTTP error: ${result.status.value}")))
                }


            } catch (e: Exception) {

                trySend(UiState.ERROR(e))
            }
            awaitClose {
                close()
            }

        }

    override suspend fun getOffersList(): Flow<UiState<List<OfferDataModel>>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            trySend(UiState.SUCCESS(httpClient.get("/offer/getOffer").body()))

        }catch (e:Exception){

            trySend(UiState.ERROR(e))

        }

        awaitClose {
            close()
        }

    }

    override suspend fun updateStatus(status: Boolean,id:String): Flow<UiState<Boolean>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            val result = httpClient.patch("/offer/updateStatus/$id/$status")
            if (result.status.value in 200..299){
                trySend(UiState.SUCCESS(true))
            }

        }catch (e:Exception){

            trySend(UiState.ERROR(e))
        }

        awaitClose {
            close()
        }
    }

    override suspend fun deleteOffer(id: String): Flow<UiState<Boolean>> = callbackFlow {
        trySend(UiState.LOADING)

        try {
            val result = httpClient.delete("/offer/delete/$id").status
            if (result.value in 200..202){
                trySend(UiState.SUCCESS(true))
            }

        }catch (e:Exception){

           trySend(UiState.ERROR(e))
        }
        awaitClose {
            close()
        }
    }


}