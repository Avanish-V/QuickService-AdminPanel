package Screens.Orders

import Network.Orders.data.OrdersDataModel
import Network.Orders.presantation.OrdersViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import domain.usecase.UiState
import org.koin.compose.koinInject

@Composable
fun OrderListScreen(navHostController: NavHostController) {

    val viewModel = koinInject<OrdersViewModel>()

    LaunchedEffect(Unit){
        viewModel.getAllOrders()
    }

    val orderState by viewModel.allOrders.collectAsState()

    var ordersList by remember { mutableStateOf(emptyList<OrdersDataModel>()) }

    when (orderState) {
        is UiState.LOADING -> {
            CircularProgressIndicator()
        }

        is UiState.ERROR -> {
            val error = (orderState as UiState.ERROR).throwable
            Text("Error loading products: ${error.message}")
        }

        is UiState.SUCCESS -> {
            val products = (orderState as UiState.SUCCESS).response
            ordersList = products

        }
    }

    LazyColumn {

        items(ordersList){

            Text("${it.orderId}")
        }
    }
}