
import kotlinx.coroutines.flow.MutableStateFlow

object AppController {

    var isLoading = MutableStateFlow<Boolean>(false)
    var dialog = MutableStateFlow<Pair<String, String>?>(null)

    fun showLoading() {
        isLoading.value = true
    }

    fun hideLoading() {
        isLoading.value = false
    }

    fun showDialog(title: String, message: String) {
        dialog.value = Pair(title, message)
    }

    fun hideDialog() {
        dialog.value = null
    }

}