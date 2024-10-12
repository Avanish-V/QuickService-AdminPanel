package Screens.Products

import Network.Products.data.Description
import Network.Products.data.Rating
import Network.Products.presantation.ProductViewModel
import UI.Blue

import UI.secondary_color
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import domain.usecase.UiState
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerMode
import io.github.vinceglb.filekit.core.PickerType
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import me.sample.library.resources.Res
import me.sample.library.resources.inbox_out
import org.koin.compose.koinInject
import kotlin.random.Random

@Composable
fun AddProductScreen(navHostController: NavHostController) {

    val productViewModel = koinInject<ProductViewModel>()

    val serviceTAG = navHostController.currentBackStackEntry?.savedStateHandle?.get<String>("categoryId")

    val topBarTitle by rememberSaveable{
        mutableStateOf(navHostController.currentBackStackEntry?.savedStateHandle?.get<String>("TITLE"))
    }

    var imageBytes by remember { mutableStateOf<ByteArray?>(null) }

    var descriptionList = remember { mutableStateListOf<Description>() }

    var serviceTitle by remember { mutableStateOf("") }


    var price by remember { mutableStateOf("") }

    var serviceTax by remember { mutableStateOf("") }

    var workType by remember { mutableStateOf("") }

    var serverResponse by remember { mutableStateOf<HttpResponse?>(null) }

    val productResult = productViewModel.state.collectAsState()

    var isLoading by remember { mutableStateOf(false) }

    val snackbarHostState = SnackbarHostState()

    val scope = rememberCoroutineScope()




    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (topBarTitle != null  ) "Edit" else "Add Product") },
                navigationIcon = {
                    IconButton(onClick = { navHostController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "")
                    }
                },
                actions = {

                    Button(
                        onClick = {

                            imageBytes?.let {
                                productViewModel.addProduct(
                                    serviceTitle = serviceTitle,
                                    imageByte = it,
                                    serviceId = generateProductId(),
                                    serviceTAG = serviceTAG.toString(),
                                    workType = workType,
                                    price = price,
                                    tax = serviceTax,
                                    description = descriptionList,
                                    rating = Rating(
                                        rating = "",
                                        count = "",
                                    )
                                )
                            }

                            when (productResult.value) {
                                is UiState.LOADING -> {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("In Progress! Please wait.", duration = SnackbarDuration.Long)
                                    }
                                }

                                is UiState.ERROR -> {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("${serverResponse?.status}", duration = SnackbarDuration.Long)
                                    }
                                }

                                is UiState.SUCCESS -> {

                                    serverResponse = (productResult.value as UiState.SUCCESS<HttpResponse>).response

                                    scope.launch {
                                        snackbarHostState.showSnackbar("Successfully saved!", duration = SnackbarDuration.Long)
                                    }

                                }
                            }

                        },
                        modifier = Modifier.padding(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Blue,
                            contentColor = Color.White
                        )
                    ) {

                        Text("Save Product")

                    }
                },
                backgroundColor = Color.White,
                elevation = 1.dp
            )
        },
        snackbarHost = {
            SnackbarHost(
                snackbar = {
                    Snackbar(snackbarData = it)
                },
                hostState = snackbarHostState
            )
        }
    ) { padding ->


        Row(modifier = Modifier.background(secondary_color).padding(padding)) {


            LazyColumn(
                modifier = Modifier.fillMaxHeight().weight(1f)
                    .background(Color.White, RoundedCornerShape(8.dp)),
                contentPadding = PaddingValues(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                item {
                    Text("Product Details", fontWeight = FontWeight.Bold)
                }

                item { TypeSelection(workType = {workType = it}) }

                item {

                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text(text = "Product Title")
                        CommonTextInput(
                            modifier = Modifier.fillMaxWidth(),
                            text = serviceTitle,
                            onValueChange = { serviceTitle = it },
                            label = "Title",
                        )
                    }

                }

                item { DescriptionSection(listOfDescription = { descriptionList = it }) }


            }


            Column(
                modifier = Modifier.fillMaxHeight()
                    .background(Color.White, RoundedCornerShape(8.dp)).weight(1f).padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {


                ImageSection(imageByte = imageBytes, imageByteArray = { imageBytes = it })

                PriceSection(
                    price = price,
                    onPriceValueChange = { price = it.toString() },
                    serviceTax = serviceTax,
                    onServiceTaxChange = { serviceTax = it.toString() }
                )

            }





        }


    }

}


    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun TypeSelection(workType: (String) -> Unit) {

        var isExpanded by remember {
            mutableStateOf(false)
        }

        var selectedText by remember { mutableStateOf("Work Type") }

        val suggestions = listOf("Maintenance", "Repair")

        workType(selectedText)

        Row {

            ExposedDropdownMenuBox(
                expanded = false,
                onExpandedChange = {
                    isExpanded = !isExpanded
                }
            ) {

                TextField(

                    value = selectedText,
                    onValueChange = { selectedText = it },
                    label = { Text("Select an option") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                    },
                    modifier = Modifier.fillMaxWidth()
                        .border(0.5.dp, color = Color.LightGray, shape = RoundedCornerShape(8.dp)),
                    readOnly = true,
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }

                ) {
                    suggestions.forEach { label ->
                        DropdownMenuItem(
                            onClick = {
                                selectedText = label
                                isExpanded = false

                            }
                        ) {
                            Text(label)
                        }
                    }
                }


            }

        }

    }


    @Composable
    fun CommonTextInput(
        modifier: Modifier = Modifier,
        text: String,
        onValueChange: (String) -> Unit,
        label: String
    ) {

        TextField(
            modifier = modifier.border(
                0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )


        )

    }

    @Composable
    fun TextInputWithIcon(
        modifier: Modifier = Modifier,
        text: String,
        onValueChange: (String) -> Unit,
        label: String,
        leadingIcon: @Composable (() -> Unit)
    ) {


        TextField(
            modifier = modifier.border(
                0.5.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(8.dp)
            ),
            value = text,
            onValueChange = {
                onValueChange(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            leadingIcon = {
                leadingIcon()
            }

        )

    }


    @Composable
    fun DescriptionSection(
        modifier: Modifier = Modifier,
        listOfDescription: (SnapshotStateList<Description>) -> Unit
    ) {
        var title by remember {
            mutableStateOf("")
        }
        var description by remember {
            mutableStateOf("")
        }
        val descriptionList = remember { mutableStateListOf<Description>() }

        listOfDescription(descriptionList)

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Text("Description", fontWeight = FontWeight.Bold)

            TextField(
                modifier = modifier.border(
                    0.5.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ).fillMaxWidth(),
                value = title,
                onValueChange = {
                    title = it

                },
                label = { Text("Title") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )


            )

            TextField(
                modifier = modifier.border(
                    0.5.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ).fillMaxWidth(),
                value = description,
                onValueChange = {
                    description = it

                },
                label = { Text("Description") },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                )


            )

            Button(
                onClick = {
                    if (title.isNotEmpty() && description.isNotEmpty()) {
                        descriptionList.add(Description(title = title, comment = description))
                        title = ""
                        description = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Blue,
                    contentColor = Color.White
                )
            ) {
                Text("Add")
            }
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

                descriptionList.forEachIndexed { index, descriptionItem ->
                    Row(
                        modifier = Modifier.background(
                            color = secondary_color,
                            shape = RoundedCornerShape(6.dp)
                        ), verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f).padding(12.dp)) {
                            Text(text = descriptionItem.title, fontWeight = FontWeight.SemiBold)
                            Text(text = descriptionItem.comment)
                        }
                        Box(contentAlignment = Alignment.TopEnd) {
                            IconButton(onClick = { descriptionList.removeAt(index) }) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }
            }


        }
    }

    @Composable
    fun PriceSection(
        price: String,
        onPriceValueChange: (String) -> Unit,
        serviceTax: String,
        onServiceTaxChange: (String) -> Unit
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {

            Text("Price", fontWeight = FontWeight.Bold)

            Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                Text(text = "Actual Price")
                TextInputWithIcon(
                    modifier = Modifier.fillMaxWidth(),
                    text = price,
                    onValueChange = {
                        onPriceValueChange(it)

                    },
                    label = "Actual Price",
                    leadingIcon = {
                        Text("₹", fontWeight = FontWeight.Bold,)
                    },
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {

                Text(text = "Tax")

                TextInputWithIcon(
                    modifier = Modifier.fillMaxWidth(),
                    text = serviceTax,
                    onValueChange = {
                        onServiceTaxChange(it)
                    },
                    label = "Service Tax",
                    leadingIcon = {
                        Text("%", fontWeight = FontWeight.Bold)
                    }
                )
            }




        }

    }


    @Composable
    fun ImageSection(imageByte: ByteArray?, imageByteArray: (ByteArray) -> Unit) {

        val scope = rememberCoroutineScope()

        val imageList = mutableListOf<ByteArray>()

        val launcher = rememberFilePickerLauncher(
            type = PickerType.Image,
            mode = PickerMode.Single,
            title = "Pick Image"
        ) { platformFile ->
            scope.launch {

                platformFile?.readBytes()?.let { imageByteArray(it) }

                if (platformFile != null) {
                    imageList.add(platformFile.readBytes())
                }

            }
        }


        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {

            Text("Product Image", fontWeight = FontWeight.Bold)

            Box(
                Modifier.width(300.dp)
                    .height(150.dp)
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
                if (imageByte == null) {
                    Text("Attach an image", color = Color.LightGray)
                } else {
                    AsyncImage(
                        model = imageByte,
                        contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                }

            }

            Button(onClick = { launcher.launch() }, colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = secondary_color)) {

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        painter = org.jetbrains.compose.resources.painterResource(Res.drawable.inbox_out),
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Black)
                    )
                    Text("Add Image", color = Color.Black)
                }


            }
        }
    }


fun generateProductId(): String {

    val randomValue = Random.nextInt(100, 1000)
    return "AC$randomValue"
}
