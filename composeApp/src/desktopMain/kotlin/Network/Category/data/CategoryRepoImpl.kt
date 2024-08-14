package Network.Category.data

import Network.Category.domain.CategoryRepository
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.utils.EmptyContent.contentType
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.util.InternalAPI
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.net.http.HttpHeaders

class CategoryRepoImpl(private val httpClient: HttpClient) : CategoryRepository {

    override suspend fun getCategory(): List<CategoryDataModel> {
        return httpClient.get("/category").body()
    }
    @OptIn(InternalAPI::class)
    override suspend fun createCategory(title: String, serviceTAG: String, imageBytes: ByteArray): Flow<UiState<Boolean>> = callbackFlow {


        trySend(UiState.LOADING)

        val formData = formData {
            append("title", title)
            append("serviceTAG", serviceTAG)
            append("image", imageBytes, Headers.build {
                append(
                    io.ktor.http.HttpHeaders.ContentDisposition,
                    "form-data; name=\"image\"; filename=\"$title.jpg\""
                )
                append(io.ktor.http.HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
            })
        }

      val response =  httpClient.post("/category") {
            setBody(MultiPartFormDataContent(formData)) // Updated from `body` to `setBody`
      }

        when(response.status.value){
            in 200..299->{
                trySend(UiState.SUCCESS(true))
            }
        }


        awaitClose {
            close()
        }


    }



}