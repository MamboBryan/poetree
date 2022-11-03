import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow

object AppController {

    var isLoading = MutableStateFlow<Boolean>(false)

    var dialog by mutableStateOf<Pair<String, String>?>(null)

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun showDialog(title: String, message: String) {
        dialog = Pair(title, message)
    }

    fun hideDialog() {
        dialog = null
    }

}