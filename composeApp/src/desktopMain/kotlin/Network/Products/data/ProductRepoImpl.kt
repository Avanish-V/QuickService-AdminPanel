package Network.Products.data

import Network.Products.domain.ProductRepository
import domain.usecase.UiState
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.request.delete
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.request
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.util.InternalAPI
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProductRepoImpl (private val httpClient: HttpClient): ProductRepository {

    override suspend fun getProducts(productTAG: String): List<ProductDataModel> {
        return httpClient.get("/serviceProducts/$productTAG").body()
    }


    override suspend fun addProduct(
        serviceTitle: String,
        imageByte: ByteArray,
        serviceId: String,
        serviceTAG: String,
        workType: String,
        price: String,
        tax: String,
        description: List<Description>,
        rating : Rating
    ): HttpResponse {
        val formData = formData {
            append("productTitle",serviceTitle)
            append("productId",serviceId)
            append("productTAG",serviceTAG)
            append("workType",workType)
            append("price",price)
            append("serviceTax",tax)
            append("description", Json.encodeToString(description))
            append("rating",Json.encodeToString(rating))
            append("productImage", imageByte, Headers.build {
                append(
                    io.ktor.http.HttpHeaders.ContentDisposition,
                    "form-data; name=\"image\"; filename=\"$serviceTitle.jpg\""
                )
                append(io.ktor.http.HttpHeaders.ContentType, ContentType.Image.JPEG.toString())
            })
        }


        return httpClient.post("/serviceProducts/addProduct") {
            setBody(MultiPartFormDataContent(formData)) // Updated from `body` to `setBody`
        }
    }

    override suspend fun deleteProduct(id: String): Flow<UiState<Boolean>> = callbackFlow {

        trySend(UiState.LOADING)

        val response = httpClient.delete("/serviceProducts/$id")

        if (response.status.value in 200..299) {
            trySend(UiState.SUCCESS(true))
        } else {
            trySend(UiState.ERROR(Exception("Failed to delete product")))
        }

        awaitClose {
            close()
        }
    }


}