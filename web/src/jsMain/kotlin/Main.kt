import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.dom.Text
import org.jetbrains.compose.web.renderComposable

fun main() {
    renderComposable(rootElementId = "root") {
        Div(
            attrs = {
                style {
                    minWidth("250dp")
                    minHeight("250dp")
                    alignItems(AlignItems.Center)
                    alignContent(AlignContent.Center)
                    flex("wrap")
                    textAlign("center")
                }
            }
        ) {
            Text("Poetree Web")
        }
    }
}

