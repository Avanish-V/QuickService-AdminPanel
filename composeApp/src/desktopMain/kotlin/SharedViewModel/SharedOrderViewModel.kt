package SharedViewModel

import Network.Orders.data.OrdersDataModel
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SharedOrderViewModel : ViewModel() {

    private val _ordersData : MutableStateFlow<OrdersDataModel?> = MutableStateFlow(null)
    val ordersData = _ordersData.asStateFlow()


    fun passOrderData(ordersDataModel: OrdersDataModel){
        _ordersData.value = ordersDataModel
    }


}