package UI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import me.sample.library.resources.Res
import me.sample.library.resources.filter_order
import org.jetbrains.compose.resources.painterResource


val orderFilterList = listOf<String>(
    "Completed",
    "Canceled",
    "Today",
    "This Week"
)
val revenueFilter = listOf<String>(
    "Today",
    "All Days",
    "This Week",
    "Last 28 days"
)

@Composable
fun filterMenu(listData : List<String>,onSelect:(String)->Unit) {

    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {

        Button(
            modifier = Modifier
                .height(38.dp)
                .padding(end = 24.dp)
                .shadow(
                    elevation = 15.dp,
                    spotColor = Blue,
                    ambientColor = Blue
                )
            ,
            onClick = {
                expanded = true
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Blue,
                contentColor = Color.White
            )
        ){
            Image(
                painter = painterResource(Res.drawable.filter_order),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(text = "Filter", color = Color.White)
        }
        DropdownMenu( expanded = expanded, onDismissRequest = { expanded = false }) {

            listData.forEach { it->
                DropdownMenuItem(
                    onClick = { onSelect(it) },
                ){
                    Text(it)
                }
                Divider()
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun filterMenu2(listData : List<String>,onSelect:(String)->Unit) {

    var isExpanded by remember {
        mutableStateOf(false)
    }

    var selectedText by remember { mutableStateOf("Today") }

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
                //label = { Text("Select an option") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
                },
                modifier = Modifier.width(160.dp),
                readOnly = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.LightGray
                )
            )
            ExposedDropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }

            ) {
                listData.forEach { label ->
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