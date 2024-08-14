package Network.Products.domain

import Network.Products.data.Description
import Network.Products.data.ProductDataModel
import Network.Products.data.Rating
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(productTAG:String):List<ProductDataModel>

    suspend fun addProduct(
        serviceTitle:String,
        imageByte:ByteArray,
        serviceId:String,
        serviceTAG:String,
        workType:String,
        price:String,
        tax:String,
        description:List<Description>,
        rating:Rating
    ):HttpResponse

    suspend fun deleteProduct(id:String):Flow<UiState<Boolean>>


}