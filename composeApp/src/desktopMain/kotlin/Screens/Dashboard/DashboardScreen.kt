package Screens.Dashboard

import UI.BarChartSample
import UI.Blue
import UI.DonutChartSample
import UI.LightBlue
import UI.LightYellow
import UI.Red
import UI.Yellow
import UI.filterMenu
import UI.filterMenu2
import UI.orderFilterList
import UI.revenueFilter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import me.sample.library.resources.Res
import me.sample.library.resources.box_open
import me.sample.library.resources.growth
import me.sample.library.resources.rupee
import me.sample.library.resources.trending
import org.jetbrains.compose.resources.painterResource
import utils.mostBookedList

@Composable
fun DashboardScreen(modifier: Modifier = Modifier) {

    LazyColumn(verticalArrangement = Arrangement.spacedBy(24.dp)) {

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
                                    text = "210",
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

                        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.CenterEnd) {
                            filterMenu2(
                                listData = revenueFilter,
                                onSelect = {

                                }
                            )
                        }

                        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {

                            Row (modifier = Modifier.width(110.dp), horizontalArrangement = Arrangement.SpaceBetween){
                                Text(text = "Orders:",)
                                Text(text = "60")
                            }

                            Row (modifier = Modifier.width(110.dp), horizontalArrangement = Arrangement.SpaceBetween){
                                Text(text = "Completed:", color = Blue)
                                Text(text = "56", color = Blue)
                            }
                            Row (modifier = Modifier.width(110.dp),horizontalArrangement = Arrangement.SpaceBetween){
                                Text(text = "Pending:", color = Yellow)
                                Text(text = "56", color = Yellow)
                            }
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
                                    text = "₹5623",
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
                                    modifier = Modifier.size(30.dp).shadow(elevation = 10.dp, spotColor = Blue, ambientColor = Blue),
                                    painter = painterResource(Res.drawable.rupee),
                                    contentDescription = null,
                                    colorFilter = ColorFilter.tint(Blue)
                                )

                            }

                        }

                        Box(modifier = Modifier.fillMaxWidth(),contentAlignment = Alignment.CenterEnd) {
                            filterMenu2(
                                listData = revenueFilter,
                                onSelect = {

                                }
                            )
                        }

                        Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                            Text(text = "Total Revenue", )
                            Text(text = "₹5623",fontWeight = FontWeight.Bold)

                            Spacer(modifier = Modifier.height(10.dp))
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
                        .height(250.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ){

                 DonutChartSample()

                }

            }


        }

        item {

            Row (modifier = Modifier.fillMaxWidth()){

                Column (
                    modifier = Modifier
                        .weight(0.45f)
                        .height(400.dp)
                        .background(Color.White, shape = RoundedCornerShape(12.dp))
                        .shadow(
                            elevation = 5.dp,
                            spotColor = Color.White,
                            ambientColor = Color.White
                        )
                ){

                    BarChartSample()
                }
                Spacer(modifier = Modifier.width(24.dp))
                Column (
                    modifier = Modifier
                        .weight(0.65f)
                        .height(400.dp)
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
                                text = "Price (₹)"
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
                                        text = "₹${it.price}"
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