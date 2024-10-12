package Screens.Dashboard

import Navigation.MainNavRoutes
import Network.Orders.data.OrdersDataModel
import Network.Orders.presantation.OrdersViewModel
import Screens.Orders.OrdersSingleRow
import Screens.Orders.SingleComponent
import Screens.Orders.SingleComponentForHeader
import Screens.Orders.SingleComponentWithStatus
import SharedViewModel.SharedOrderViewModel
import UI.BarChartSample
import UI.Blue
import UI.DarkSky
import UI.DonutChartSample
import UI.LightBlue
import UI.LightYellow
import UI.LineChartSample
import UI.Red
import UI.Yellow
import UI.filterMenu
import UI.filterMenu2
import UI.orderFilterList
import UI.revenueFilter
import UI.secondary_color
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.ProgressIndicatorDefaults
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import domain.usecase.UiState
import me.sample.library.resources.Res
import me.sample.library.resources.box_open
import me.sample.library.resources.growth
import me.sample.library.resources.rupee
import me.sample.library.resources.trending
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject
import utils.mostBookedList

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    val viewModel = koinInject<OrdersViewModel>()

    var isLoading by remember {
        mutableStateOf(false)
    }


    LaunchedEffect(Unit){
        viewModel.getAllOrders()
    }
    val orderState by viewModel.allOrders.collectAsState()

    var ordersList by remember { mutableStateOf(emptyList<OrdersDataModel>()) }

    when (orderState) {
        is UiState.LOADING -> {
            isLoading = true
        }

        is UiState.ERROR -> {
            val error = (orderState as UiState.ERROR).throwable
            Text("Error loading products: ${error.message}")
            isLoading = false
        }

        is UiState.SUCCESS -> {
            isLoading = false
            val products = (orderState as UiState.SUCCESS).response
            ordersList = products

        }
    }


    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {

        item {

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){

                Text("Dashboard", fontWeight = FontWeight.Bold, fontSize = 18.sp)

                filterMenu2(
                    listData = revenueFilter,
                    onSelect = {

                    }
                )

            }
        }

        item{

            Row (modifier = Modifier.fillMaxWidth()){

                Box (
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ){

                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                            Column (verticalArrangement = Arrangement.spacedBy(20.dp)){

                                Text(
                                    text = "Today Orders",
                                    fontWeight = FontWeight.Thin
                                )

                                Text(
                                    text = "${ordersList.count()}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )

                            }

                            Box (
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        color = LightYellow,
                                        shape = RoundedCornerShape(24.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ){

                                Image(
                                    modifier = Modifier.size(32.dp).shadow(elevation = 10.dp, spotColor = Yellow, ambientColor = Yellow),
                                    painter = painterResource(Res.drawable.box_open),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Yellow)
                                )

                            }

                        }


                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

//                            Row (modifier = Modifier.width(110.dp), horizontalArrangement = Arrangement.SpaceBetween){
//                                Text(text = "Assigned:",)
//                                Text(text = "${ordersList.count { it.status == "Assigned" }}")
//                            }
//
//                            Row (modifier = Modifier.width(110.dp), horizontalArrangement = Arrangement.SpaceBetween){
//                                Text(text = "Completed:", color = Blue)
//                                Text(text = "${ordersList.count { it.status == "Completed" }}", color = Blue)
//                            }
//                            Row (modifier = Modifier.width(110.dp),horizontalArrangement = Arrangement.SpaceBetween){
//                                Text(text = "Placed:", color = Yellow)
//                                Text(text = "${ordersList.count { it.status == "Placed" }}", color = Yellow)
//                            }
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ){
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Res.drawable.trending),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Red)
                                )
                                Text(text = "4.5% down from yesterday",fontSize = 12.sp)
                            }
                        }

                    }

                }

                Spacer(modifier = Modifier.width(24.dp))

                Box (
                    modifier = Modifier
                        .weight(1f)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ){

                    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {

                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){

                            Column (verticalArrangement = Arrangement.spacedBy(20.dp)){

                                Text(
                                    text = "Today Revenue",
                                    fontWeight = FontWeight.Thin
                                )

                                Text(
                                    text = "₹${ordersList.filter { it.status == "Completed" }.sumOf { it.priceTag.total }}",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 24.sp
                                )

                            }

                            Box (
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(
                                        color = LightBlue,
                                        shape = RoundedCornerShape(24.dp)
                                    ),
                                contentAlignment = Alignment.Center
                            ){

                                Image(
                                    modifier = Modifier.size(30.dp).shadow(elevation = 0.dp, spotColor = Blue, ambientColor = Blue),
                                    painter = painterResource(Res.drawable.rupee),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Blue)
                                )

                            }

                        }


                        Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
//                            Text(text = "Total Revenue", )
//                            Text(text = "₹${ordersList.sumOf { it.priceTag.total }}",fontWeight = FontWeight.Bold)
//
//                            Spacer(modifier = Modifier.height(10.dp))
                            Row (
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ){
                                Image(
                                    modifier = Modifier.size(24.dp),
                                    painter = painterResource(Res.drawable.growth),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(color = Color.Green)
                                )
                                Text(text = "6% up from yesterday",fontSize = 12.sp)
                            }
                        }


                    }


                }
                Spacer(modifier = Modifier.width(24.dp))
                Column (
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                    , verticalArrangement = Arrangement.Center
                ){


//                 DonutChartSample(
//                     assigned = ordersList.count { it.status == "Assigned" }.toDouble(),
//                     completed = ordersList.count { it.status == "Completed" }.toDouble(),
//                     canceled = ordersList.count { it.status == "Canceled" }.toDouble(),
//                     placed = ordersList.count { it.status == "Placed" }.toDouble()
//
//                 )

                    val placedOrdersCount = ordersList.count { it.status == "Placed" }.toFloat()
                    val AssignedOrdersCount = ordersList.count { it.status == "Assigned" }.toFloat()
                    val CompletedOrdersCount = ordersList.count { it.status == "Completed" }.toFloat()
                    val CanceledOrdersCount = ordersList.count { it.status == "Canceled" }.toFloat()
                    val totalOrdersCount = ordersList.count().toFloat()

                    val progressValue1 = if (totalOrdersCount > 0) placedOrdersCount / totalOrdersCount else 0f
                    val progressValue2 = if (totalOrdersCount > 0) AssignedOrdersCount / totalOrdersCount else 0f
                    val progressValue3 = if (totalOrdersCount > 0) CompletedOrdersCount / totalOrdersCount else 0f
                    val progressValue4 = if (totalOrdersCount > 0) CanceledOrdersCount / totalOrdersCount else 0f

                    val animatedProgress1 by animateFloatAsState(targetValue = progressValue1)
                    val animatedProgress2 by animateFloatAsState(targetValue = progressValue2)
                    val animatedProgress3 by animateFloatAsState(targetValue = progressValue3)
                    val animatedProgress4 by animateFloatAsState(targetValue = progressValue4)

                    Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceEvenly){

                        Column (verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally){
                            Box(contentAlignment = Alignment.Center){

                                CircularProgressIndicator(
                                    modifier = Modifier.size(80.dp),
                                    color = Yellow,
                                    strokeWidth = 10.dp,
                                    backgroundColor = secondary_color,
                                    progress =animatedProgress1,
                                    strokeCap = StrokeCap.Round
                                )

                                Text("${ordersList.count { it.status == "Placed" }}", fontWeight = FontWeight.SemiBold)

                            }

                            Text("Placed")

                        }
                        Column (verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally){

                            Box(contentAlignment = Alignment.Center){

                                CircularProgressIndicator(
                                    modifier = Modifier.size(80.dp),
                                    color = DarkSky,
                                    strokeWidth = 10.dp,
                                    backgroundColor = secondary_color,
                                    progress =animatedProgress2,
                                    strokeCap = StrokeCap.Round
                                )

                                Text("${ordersList.count { it.status == "Assigned" }}", fontWeight = FontWeight.SemiBold)

                            }
                            Text("Assigned")

                        }
                        Column (verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally){

                            Box(contentAlignment = Alignment.Center){

                                CircularProgressIndicator(
                                    modifier = Modifier.size(80.dp),
                                    color = Blue,
                                    strokeWidth = 10.dp,
                                    backgroundColor = secondary_color,
                                    progress =animatedProgress3,
                                    strokeCap = StrokeCap.Round
                                )

                                Text("${ordersList.count { it.status == "Completed" }}", fontWeight = FontWeight.SemiBold)
                            }
                            Text("Completed")

                        }
                        Column (verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.CenterHorizontally){

                            Box(contentAlignment = Alignment.Center){

                                CircularProgressIndicator(
                                    modifier = Modifier.size(80.dp),
                                    color = Red,
                                    strokeWidth = 10.dp,
                                    backgroundColor = secondary_color,
                                    progress =animatedProgress4,
                                    strokeCap = StrokeCap.Round
                                )

                                Text("${ordersList.count { it.status == "Canceled" }}", fontWeight = FontWeight.SemiBold)

                            }

                            Text("Canceled")
                        }


                    }


//                    Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
//                        Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween){
//                            Text("Placed")
//                            Text("${ordersList.count { it.status == "Placed" }}", fontWeight = FontWeight.SemiBold)
//                        }
//                        LinearProgressIndicator(
//                            modifier = Modifier.height(16.dp),
//                            color = Yellow,
//                            progress =(ordersList.count { it.status == "Placed" }.toFloat()/100f),
//                            strokeCap = StrokeCap.Round
//                        )
//                    }
//                    Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
//                        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
//                            Text("Assigned")
//                            Text("${ordersList.count { it.status == "Assigned" }}", fontWeight = FontWeight.SemiBold)
//                        }
//                        LinearProgressIndicator(
//                            modifier = Modifier.height(16.dp),
//                            color = DarkSky,
//                            progress =(ordersList.count { it.status == "Assigned" }.toFloat()/100f),
//                            strokeCap = StrokeCap.Round
//                        )
//                    }
//                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//                        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
//                            Text("Completed")
//                            Text("${ordersList.count { it.status == "Completed" }}", fontWeight = FontWeight.SemiBold)
//                        }
//                        LinearProgressIndicator(
//                            modifier = Modifier.height(16.dp),
//                            color = Blue,
//                            progress =(ordersList.count { it.status == "Completed" }.toFloat()/100f),
//                            strokeCap = StrokeCap.Round
//                        )
//                    }
//                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
//
//                        Row (modifier = Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.SpaceBetween){
//                            Text("Canceled")
//                            Text("${ordersList.count { it.status == "Canceled" }}", fontWeight = FontWeight.SemiBold)
//                        }
//                        LinearProgressIndicator(
//                            modifier = Modifier.height(16.dp),
//                            color = Red,
//                            progress =(ordersList.count { it.status == "Canceled" }.toFloat()/100f),
//                            strokeCap = StrokeCap.Round
//                        )
//                    }

                }

            }


        }

        item {

            Row (modifier = Modifier.fillMaxWidth()){

                Column (
                    modifier = Modifier
                        .weight(0.6f)
                        .height(440.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ){

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(20.dp)
                    ) {
                        Text(text = "Today Bookings", fontWeight = FontWeight.Bold)
                    }

                    Divider(modifier = Modifier.fillMaxWidth())

                    Row (modifier = Modifier.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){

                        SingleComponentForHeader(text = "Service Product", modifier = Modifier.weight(1f))
                        //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Order Id", modifier = Modifier.weight(1f))
                        //Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Paid(₹)", modifier = Modifier.weight(1f))
                        // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        //SingleComponentForHeader(text = "Date", modifier = Modifier.weight(1f))
                        // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                        SingleComponentForHeader(text = "Status", modifier = Modifier.weight(1f))

                    }

                    Divider(modifier = Modifier.fillMaxWidth())

                    LazyColumn (modifier = Modifier){

                        items(ordersList){

                            Column {
                                Row (modifier = Modifier.clickable {  }.fillMaxWidth().height(72.dp).background(Color.White), verticalAlignment = Alignment.CenterVertically){

                                    SingleComponent(text = it.productInfo.productTitle , modifier = Modifier.weight(1f))
                                    // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                    SingleComponent(text = it.orderId, modifier = Modifier.weight(1f))
                                    // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                    SingleComponent(text = "₹${it.priceTag.total}", modifier = Modifier.weight(1f))
                                    // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                    // Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                                    SingleComponentWithStatus(text = it.status, modifier = Modifier.weight(1f))



                                }
                                Divider(modifier = Modifier.fillMaxWidth())
                            }
                        }
                    }


                }

                Spacer(modifier = Modifier.width(24.dp))

                Column (
                    modifier = Modifier

                        .weight(0.4f)
                        .height(440.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ) {

                    Column {

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.padding(20.dp).height(20.dp)
                        ) {
                            Text(text = "Most Booked", fontWeight = FontWeight.Bold)
                        }

                        Divider()
                        Row(
                            modifier = Modifier.height(42.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            SingleHeader(
                                modifier = Modifier.weight(1f).padding(start = 20.dp),
                                text = "Product"
                            )
                            SingleHeader(
                                modifier = Modifier.weight(1f).padding(start = 20.dp),
                                text = "Category"
                            )
                            SingleHeader(
                                modifier = Modifier.weight(1f).padding(start = 20.dp),
                                text = "Product Id"
                            )

                            SingleHeader(
                                modifier = Modifier.weight(1f).padding(start = 20.dp),
                                text = "Booked"
                            )

                        }
                        Divider()

                        LazyColumn {

                            items(mostBookedList) {
                                Row(
                                    modifier = Modifier.height(52.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    SingleItem(
                                        modifier = Modifier.weight(1f),
                                        text = it.productTitle
                                    )
                                    SingleItem(
                                        modifier = Modifier.weight(1f),
                                        text = it.category
                                    )
                                    SingleItem(
                                        modifier = Modifier.weight(1f),
                                        text = it.productId
                                    )

                                    SingleItem(
                                        modifier = Modifier.weight(1f),
                                        text = it.booked.toString()
                                    )

                                }

                                Divider()
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SingleHeader(modifier: Modifier = Modifier,text: String) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(text = text, fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }


}

@Composable
fun SingleItem(modifier: Modifier = Modifier,text:String) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = text,
            maxLines = 1
        )
    }

}