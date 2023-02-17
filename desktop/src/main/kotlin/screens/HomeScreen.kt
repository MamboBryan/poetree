package screens

import AppController.showDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.utils.DialogData
import navigation.NavController

@Composable
fun HomeScreen(navigator: NavController) {

    val scope = rememberCoroutineScope()

    fun signOut() {
        val data = DialogData(
            title = "Oh No!",
            description = "We'll miss you and by signing out you'll lose all your saved poems. Are you sure you want to sign out?",
            positiveAction = {
                UserPreferences().signedOut()
            },
            negativeAction = {}
        )
        showDialog(data = data)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Home")
        Button(onClick = ::signOut) {
            Text(text = "Sign Out")
        }
    }
}