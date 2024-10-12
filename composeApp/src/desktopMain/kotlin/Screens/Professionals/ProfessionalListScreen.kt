package Screens.Professionals

import Navigation.MainNavRoutes
import Network.Products.data.ProductDataModel
import Network.Professionals.presantation.ProfessionalsViewModel
import Screens.Orders.LoadingScreen
import Screens.Orders.SearchField
import Screens.Orders.SingleComponentForHeader
import Screens.Orders.SingleComponentWithStatus
import SharedViewModel.SharedProfessionalViewModel
import UI.Blue
import UI.DarkSky
import UI.LightBlue
import UI.Red
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import com.example.Model.ProfessionalDataModel
import kotlinx.coroutines.flow.asStateFlow
import org.koin.compose.koinInject

@Composable
fun ProfessionalListScreen(navHostController: NavHostController) {

    val professionalsViewModel = koinInject<ProfessionalsViewModel>()
    val sharedProfessionalViewModel = koinInject<SharedProfessionalViewModel>()



    LaunchedEffect(Unit){
        professionalsViewModel.getProfessionals()
    }

    val resultState = professionalsViewModel.professionalsResultState.collectAsState()

    var searchText by remember { mutableStateOf("") }

    Scaffold (
        topBar = {
            TopAppBar(
                modifier = Modifier.height(100.dp),
                title = { Text("Professionals") },
                backgroundColor = Color.White,
                elevation = 0.5.dp,
                actions = {
                    SearchField(
                        text = searchText,
                        onValueChange = {
                            searchText = it
                        },
                        palceholder = "Search" ,
                        trailingIcon = {

                        },
                        onSearchClick = {

                        }
                    )
                }
            )
        }
    ){

        if (resultState.value.isLoading){
            LoadingScreen()
        }
        else if (resultState.value.error.isNotBlank()){
            Text(resultState.value.error)
        }
        else if (resultState.value.data.isEmpty()){
            Text("No Professionals")
        }
        else{

            Column {

                Row(modifier = Modifier.fillMaxWidth().height(60.dp), verticalAlignment = Alignment.CenterVertically) {

                    SingleComponentForHeader(text = "Photo", modifier = Modifier.weight(1f))
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                    SingleComponentForHeader(text = "Name", modifier = Modifier.weight(1f))
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                    SingleComponentForHeader(text = "ID", modifier = Modifier.weight(1f))
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                    SingleComponentForHeader(text = "Mobile", modifier = Modifier.weight(1f))
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                    SingleComponentForHeader(text = "Email", modifier = Modifier.weight(1f))
                    Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
                    SingleComponentForHeader(text = "Status", modifier = Modifier.weight(1f))
                }

                Divider(modifier = Modifier.fillMaxWidth())

                LazyColumn {
                    items(resultState.value.data){
                        if (it != null) {
                            ProfessionalSingleItem(
                                data = it,
                                onClick = {
                                    navHostController.navigate(MainNavRoutes.Professionals.ProfessionalDetail.routes).apply {
                                        sharedProfessionalViewModel.parseProfessionalData(it)
                                    }
                                }

                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfessionalSingleItem(data: ProfessionalDataModel, onClick:()->Unit) {

    Column(modifier = Modifier.clickable { onClick() }) {
        Row (modifier = Modifier.fillMaxWidth().height(100.dp), verticalAlignment = Alignment.CenterVertically){

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                AsyncImage(
                    modifier = Modifier.padding(start = 24.dp).size(60.dp).clip(
                        CircleShape
                    ),
                    model = data.photoUrl,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    clipToBounds = true
                )

            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))

            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text = data.professionalName, maxLines = 1, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text =data.professionalId, maxLines = 1, overflow = TextOverflow.Clip)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text = data.mobile,)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(modifier = Modifier.padding(start = 24.dp), text =data.email, overflow = TextOverflow.Ellipsis, maxLines = 1)

            }
            Divider(modifier = Modifier.fillMaxHeight().width(1.dp))
            Box(Modifier.weight(1f), contentAlignment = Alignment.CenterStart){

                Text(
                    modifier = Modifier.padding(start = 24.dp).background(color = LightBlue, shape = RoundedCornerShape(5.dp)).padding(horizontal = 20.dp, vertical = 4.dp),
                    text =data.accountStatus.status,
                    color = Blue,
                    fontWeight = FontWeight.SemiBold,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,

                )

            }

        }
        Divider(modifier = Modifier.fillMaxWidth())
    }


}