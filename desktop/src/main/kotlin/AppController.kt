
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.utils.DialogData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun showDialog(data: DialogData) {
    AppController.showDialog(data = data)
}

fun dismissDialog() {
    AppController.hideDialog()
}

fun startLoading() {
    AppController.showLoading()
}

fun stopLoading() {
    AppController.hideLoading()
}

object AppController {

    private val preferences = UserPreferences()

    private val VISIBLE = MutableStateFlow(true)
    val windowIsVisible get() = VISIBLE

    private val DIALOG = MutableStateFlow<DialogData?>(null)
    val dialog get() : StateFlow<DialogData?> = DIALOG

    internal fun showLoading(){
        preferences.startLoading()
    }

    internal fun hideLoading(){
        preferences.stopLoading()
    }

    internal fun showWindow(){
        VISIBLE.value = true
    }

    internal fun hideWindow(){
        VISIBLE.value = false
    }

    internal fun showDialog(data: DialogData){
        DIALOG.value = data
    }

    internal fun hideDialog(){
        DIALOG.value = null
    }

}