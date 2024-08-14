package Network.Promotion.data

import Network.Promotion.domain.PromotionRepository
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentDisposition
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PromotionRepoImpl(private val httpClient: HttpClient) : PromotionRepository{


    override suspend fun createPromotion(
        byteArray: ByteArray,
        categoryId: String,
        workType: String
    ): Flow<UiState<Boolean>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

           val formData = formData {
               append("categoryId",categoryId)
               append("workType",workType)
               append("imageByteArray",byteArray, Headers.build {
                   append(HttpHeaders.ContentDisposition,"form-data; name = \"image\"; filename = \"$categoryId.jpg\"")
                   append(HttpHeaders.ContentType,ContentType.Image.JPEG.toString())
               })

           }
           val response = httpClient.post("/promotion/createPromotion"){
               setBody(MultiPartFormDataContent(formData))
           }.status

            if (response.value in 200..2002){
                trySend(UiState.SUCCESS(true))
            }

        }catch (e:Exception){

            trySend(UiState.ERROR(e))
        }

        awaitClose { close() }



    }

    override suspend fun getPromotion(): Flow<UiState<List<PromotionDataModel>>> = callbackFlow {

        trySend(UiState.LOADING)

        try {

            trySend(UiState.SUCCESS( httpClient.get("/promotion").body()))

        }catch (e:Exception){

            trySend(UiState.ERROR(e))

        }

        awaitClose { close() }


    }


}