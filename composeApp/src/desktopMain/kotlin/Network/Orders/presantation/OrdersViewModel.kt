package Network.Orders.presantation

import Network.Orders.data.OrderRepoImpl
import Network.Orders.data.OrdersDataModel
import Network.Orders.domain.OrderRepository
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.usecase.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class OrdersViewModel(private val orderRepository: OrderRepository) : ViewModel() {

    private val _allOrders: MutableStateFlow<UiState<List<OrdersDataModel>>> = MutableStateFlow(UiState.LOADING)
    val allOrders = _allOrders.asStateFlow()

    private val _orderByProfessional = MutableStateFlow(OrderResultState())
    val orderByProfessional: StateFlow<OrderResultState> get() = _orderByProfessional


    fun getAllOrders(){

        viewModelScope.launch {

            _allOrders.value = UiState.LOADING
            try {
                val response = orderRepository.getOrders()
                _allOrders.value = UiState.SUCCESS(response)
            } catch (e: Exception) {
                _allOrders.value = UiState.ERROR(e)
            }


        }
    }

    fun getOrdersByProfessional(professionalId:String){

        viewModelScope.launch {

            orderRepository.getOrdersByProfessionalId(professionalId)
                .collect{
                    when(it){
                        is  UiState.LOADING->{
                            _orderByProfessional.value = OrderResultState(isLoading = true)
                        }
                        is  UiState.ERROR->{
                            _orderByProfessional.value = OrderResultState(error = it.throwable.message.toString())
                        }
                        is  UiState.SUCCESS->{
                            _orderByProfessional.value = OrderResultState(orderList = it.response)
                        }
                    }
                }
        }
    }



}

data class OrderResultState(
    val isLoading : Boolean = false,
    val orderList : List<OrdersDataModel> = emptyList(),
    val error : String = ""
)