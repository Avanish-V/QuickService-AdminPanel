import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {


    Window(
        onCloseRequest = ::exitApplication,
        title = "QuickService-Admin Panel",
    ) {
        App()
    }
}