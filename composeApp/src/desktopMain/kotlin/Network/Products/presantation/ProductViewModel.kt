package Network.Products.presantation

import Network.Products.data.Description
import Network.Products.data.ProductDataModel
import Network.Products.data.Rating
import Network.Products.domain.ProductRepository
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel(private val productRepository: ProductRepository) : ViewModel() {


    var list = mutableListOf<ProductDataModel>()

    private val _state:MutableStateFlow<UiState<HttpResponse>> = MutableStateFlow(UiState.LOADING)
    val state = _state.asStateFlow()

    private val _productList:MutableStateFlow<UiState<List<ProductDataModel>>> = MutableStateFlow(UiState.LOADING)
    val productList = _productList.asStateFlow()

//    private val _productResultState = MutableStateFlow(ProductResultState())
//    val productResultState: StateFlow<ProductResultState> = _productResultState

    fun getProductList(serviceId:String){
        viewModelScope.launch {
            _productList.value = UiState.LOADING
            try {
                 list = productRepository.getProducts(serviceId).toMutableList()
                _productList.value = UiState.SUCCESS(list)

            }catch (e:Exception){
                _productList.value = UiState.ERROR(e)
            }
        }
    }

    fun addProduct(
        serviceTitle:String,
        imageByte:ByteArray,
        serviceId:String,
        serviceTAG:String,
        workType:String,
        price:String,
        tax:String,
        description:List<Description>,
        rating:Rating
    ){

        viewModelScope.launch {

            _state.value = UiState.LOADING
            try {
                val response = productRepository.addProduct(
                    serviceTitle = serviceTitle,
                    imageByte = imageByte,
                    serviceId = serviceId,
                    serviceTAG = serviceTAG,
                    workType = workType,
                    price = price,
                    tax = tax,
                    description = description,
                    rating = rating
                )
                _state.value = UiState.SUCCESS(response)
            }catch (e:Exception){
                _state.value = UiState.ERROR(e)
            }


        }


    }

    suspend fun deleteProduct(id:String) = productRepository.deleteProduct(id)

    fun removeFromListByID(dataModel: ProductDataModel){

        list.remove(dataModel)

    }

}

