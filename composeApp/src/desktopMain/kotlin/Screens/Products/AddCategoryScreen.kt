package Screens.Products


import Navigation.MainNavRoutes
import Network.Category.Presantation.CategoryViewModel
import Network.Category.data.CategoryDataModel
import Screens.Orders.LoadingScreen
import UI.Blue
import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.graphic_style
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject

@Composable
fun AddCategoryListScreen(navHostController: NavHostController) {

    val categoryViewModel = koinInject<CategoryViewModel>()

    val scope = rememberCoroutineScope()

    var categoryTitle by remember {
        mutableStateOf("")
    }
    var categoryId by remember {
        mutableStateOf("")
    }

    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    val launcher = rememberFilePickerLauncher(
        type = PickerType.Image,
        mode = PickerMode.Single,
        title = "Pick a Category Image"
    ) { platformFile ->
        scope.launch {
            imageBytes = platformFile?.readBytes()


        }
    }

    val snackbarHostState = SnackbarHostState()

    var isLoading by remember { mutableStateOf(false) }



    Column(modifier = Modifier.background(secondary_color).shadow(elevation = 10.dp, spotColor = Blue, ambientColor = Blue)) {

        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.width(400.dp)
                .background(color = Color.White, shape = RoundedCornerShape(8.dp))
                .padding(vertical = 32.dp, horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .size(120.dp)
                    .drawBehind {
                    // Set dash effect: [dash length, space length]
                    val dashLength = 5f
                    val gapLength = 5f
                    val strokeWidth = 2f
                    val dashEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, gapLength), 0f)

                    // Draw the dashed border
                    drawRoundRect(
                        color = Color.LightGray,
                        size = size,
                        style = Stroke(
                            width = strokeWidth,
                            pathEffect = dashEffect,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        ),
                        cornerRadius = androidx.compose.ui.geometry.CornerRadius(10.dp.toPx()) // optional rounded corners
                    )
                },
                contentAlignment = Alignment.Center
            ) {

                AsyncImage(
                    modifier = Modifier.size(120.dp),
                    model = imageBytes,
                    contentDescription = null
                )

                IconButton(onClick = {
                    launcher.launch()
                }) {

                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(Res.drawable.graphic_style),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(color = Color.LightGray)
                    )

                }
            }


            Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                Text("Category Title")

                CommonTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = categoryTitle,
                    onValueChange = { categoryTitle = it },
                    label = "Title",

                    )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Category Id")
                CommonTextInput(
                    modifier = Modifier.fillMaxWidth(),
                    text = categoryId,
                    onValueChange = { categoryId = it },
                    label = "Category Id",
                )
            }

            Button(
                onClick = {
                    imageBytes?.let {

                        scope.launch {

                             categoryViewModel.createCategory(
                                title = categoryTitle,
                                serviceTAG = categoryId,
                                byteArray = it
                            ).collect{
                                when(it){
                                    is UiState.LOADING->{
                                        isLoading = true
                                        snackbarHostState.showSnackbar(message = "Creating..!")
                                    }
                                    is UiState.ERROR->{
                                        isLoading = false
                                        snackbarHostState.showSnackbar(message = "Something Went Wrong!")
                                    }
                                    is UiState.SUCCESS->{
                                        isLoading = false
                                        snackbarHostState.showSnackbar(message = "Created!")
                                    }
                                }
                             }

                        }

                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
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
                Text("Save")
            }

            SnackbarHost(
                hostState = snackbarHostState
            )
           // if (isLoading) LoadingScreen()


        }

    }


}
