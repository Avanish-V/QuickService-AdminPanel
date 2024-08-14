package Screens.Products


import Navigation.MainNavRoutes
import Network.Category.Presantation.CategoryViewModel
import Network.Category.data.CategoryDataModel
import Screens.Orders.LoadingScreen
import UI.Blue
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.ac
import me.sample.library.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CategoryListScreen(navHostController: NavHostController) {

    val categoryViewModel = koinInject<CategoryViewModel>()

    LaunchedEffect(Unit){
        categoryViewModel.getCategory()
    }

    val result = categoryViewModel.category.collectAsState()
    var categoryList by remember { mutableStateOf(emptyList<CategoryDataModel>()) }

    var isLoading by remember {
        mutableStateOf(false)
    }

    var isPopup by remember {
        mutableStateOf(false)
    }

    when(result.value){

       is UiState.LOADING -> {
            isLoading = true

        }
        is UiState.ERROR -> {
            isLoading = false
        }
        is UiState.SUCCESS -> {
            isLoading = false
            categoryList = (result.value as UiState.SUCCESS<List<CategoryDataModel>>).response
        }

    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Category") },
                actions = {

                    Button(
                        onClick = {
                           isPopup = !isPopup
                        },
                        modifier = Modifier
                            .padding(10.dp)
                            .shadow(
                                elevation = 15.dp,
                                spotColor = Blue,
                                ambientColor = Blue
                            ),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ){
                        Image(
                            imageVector = Icons.Default.Add,
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(Color.White)
                        )
                        Text("Category")
                    }

                },
                backgroundColor = Color.White,
                elevation = 1.dp,
                modifier = Modifier.height(80.dp)
            )
        }
    ) {



        Column (modifier = Modifier.fillMaxSize().background(Color.White)){

            if (isLoading){
                LoadingScreen()
            }else{

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Fixed(8),
                    contentPadding = PaddingValues(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp),
                ){

                    items(categoryList){

                        SingleCategoryCard(
                            categoryDataModel = it,
                            onClick = {
                                navHostController.navigate(MainNavRoutes.Products.ProductScreen.routes).apply {
                                    navHostController.currentBackStackEntry?.savedStateHandle?.set("serviceId",it.categoryId)
                                }
                            }
                        )
                    }
                }
            }

        }

        if (isPopup){

            Popup (
                alignment = Alignment.Center,
                onDismissRequest = {
                    isPopup = false
                },
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ){

                AddCategoryListScreen(navHostController = navHostController)

            }
        }


    }




}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SingleCategoryCard(categoryDataModel: CategoryDataModel,onClick:()->Unit) {

    Card(modifier = Modifier.width(80.dp), onClick = {onClick()}){

        Column (
            modifier= Modifier.padding(vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            AsyncImage(
                modifier = Modifier.size(60.dp),
                model = categoryDataModel.imageUrl,
                contentDescription =  null,
                colorFilter = ColorFilter.tint(Blue)
            )

            Text(categoryDataModel.categoryTitle, style = TextStyle(textAlign = TextAlign.Center))
        }

    }


}