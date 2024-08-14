package UI

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun CustomTextInput(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeHolder : String,

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
        placeholder = { Text(text = placeHolder, color = Color.LightGray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),


    )

}

@Composable
fun PasswordTextInput(
    modifier: Modifier = Modifier,
    text: String,
    onValueChange: (String) -> Unit,
    placeHolder : String,

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
        placeholder = { Text(text = placeHolder, color = Color.LightGray) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    )

}