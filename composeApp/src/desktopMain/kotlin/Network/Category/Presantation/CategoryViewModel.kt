package Network.Category.Presantation

import Network.Category.data.CategoryDataModel
import Network.Category.domain.CategoryRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CategoryViewModel(private val categoryRepository: CategoryRepository) : ViewModel(){

    private val _category : MutableStateFlow<UiState<List<CategoryDataModel>>> = MutableStateFlow(UiState.LOADING)
    val category = _category.asStateFlow()

    fun getCategory(){

        viewModelScope.launch {

            _category.value = UiState.LOADING

            try {

             val response = categoryRepository.getCategory()
             _category.value = UiState.SUCCESS(response)

            }catch (e:Exception){

                _category.value = UiState.ERROR(e)


            }


        }
    }

    suspend fun createCategory(title: String, serviceTAG: String, byteArray: ByteArray) = categoryRepository.createCategory(
        title = title,
        serviceTAG = serviceTAG,
        imageBytes = byteArray
    )

}