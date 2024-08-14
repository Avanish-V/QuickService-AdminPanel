package Network.Category.domain

import Network.Category.data.CategoryDataModel
import domain.usecase.UiState
import kotlinx.coroutines.flow.Flow
import java.net.http.HttpResponse

interface  CategoryRepository {

    suspend fun getCategory():List<CategoryDataModel>

    suspend fun createCategory(title:String,serviceTAG:String,imageBytes:ByteArray) : Flow<UiState<Boolean>>
}