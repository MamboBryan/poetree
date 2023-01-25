package composables.section

import AppController.hideLoading
import AppController.showDialog
import AppController.showLoading
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mambo.poetree.data.remote.NetworkResult
import com.mambo.poetree.data.repositories.AuthRepository
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import com.mambo.poetree.utils.isValidEmail
import com.mambo.poetree.utils.isValidPassword
import kotlinx.coroutines.launch

data class AuthNavigator(
    val navigateToSetup : () -> Unit,
    val navigateToHome : () -> Unit
)

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AuthSection(navigator: AuthNavigator) {

    val scope = rememberCoroutineScope()

    var isSigningIn by rememberSaveable { mutableStateOf(true) }

    val (title, message, action) = when (isSigningIn) {
        true -> Triple("Welcome \nBack", "Oh no, you don't have an account?", "Sign Up")
        false -> Triple("Create \nAccount", "Wait, ain't you a veteran?", "Sign In")
    }

    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordIsVisible by rememberSaveable { mutableStateOf(false) }

    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassWordIsVisible by rememberSaveable { mutableStateOf(false) }

    val isEnabled = when (isSigningIn) {
        false -> email.isValidEmail() and password.isValidPassword() and (password == confirmPassword)
        true -> email.isValidEmail() and password.isValidPassword()
    }

    fun handleResponse(response: NetworkResult<Boolean>) {
        val type = if (response.isSuccessful) DialogType.SUCCESS else DialogType.ERROR
        val dialogTitle = if (response.isSuccessful) "Success" else "Error"
        val dialogMessage = when {
            !response.isSuccessful -> response.message
            isSigningIn -> "Signed In Successfully"
            else -> "Signed Up Successfully"
        }

        showDialog(data = DialogData(type = type, title = dialogTitle, description = dialogMessage))
        if (response.isSuccessful){
            when (response.data) {
                true -> navigator.navigateToHome.invoke()
                else -> navigator.navigateToSetup.invoke()
            }
        }

    }

    fun signIn() {
        scope.launch {
            showLoading()
            val response = AuthRepository().signIn(email, password)
            hideLoading()
            handleResponse(response)
        }
    }

    fun signUp() {
        scope.launch {
            showLoading()
            val response = AuthRepository().signUp(email, password)
            hideLoading()
            handleResponse(response)
        }
    }

    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.h4,
                    fontSize = TextUnit(24f, TextUnitType.Sp)
                )
                TextField(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    label = { Text("Email") },
                    value = email,
                    isError = email.length > 1 && email.isValidEmail().not(),
                    onValueChange = { email = it },
                )
                if (email.length > 1 && email.isValidEmail().not())
                    Text(
                        text = "Invalid Email Address",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    singleLine = true,
                    isError = password.length > 1 && password.isValidPassword().not(),
                    placeholder = { Text("********") },
                    visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordIsVisible) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff

                        val description =
                            if (passwordIsVisible) "Hide password" else "Show password"

                        IconButton(onClick = { passwordIsVisible = !passwordIsVisible }) {
                            Icon(imageVector = image, description)
                        }
                    }
                )
                if (password.length > 1 && password.isValidPassword().not())
                    Text(
                        text = "Invalid Password",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )

                AnimatedVisibility(
                    visible = isSigningIn.not(),
                ) {
                    Column {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp),
                            value = confirmPassword,
                            onValueChange = { confirmPassword = it },
                            label = { Text("Confirm Password") },
                            singleLine = true,
                            isError = confirmPassword.length > 1 && password.equals(
                                confirmPassword
                            )
                                .not(),
                            placeholder = { Text("********") },
                            visualTransformation = if (confirmPassWordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                            trailingIcon = {
                                val image =
                                    if (confirmPassWordIsVisible) Icons.Filled.Visibility
                                    else Icons.Filled.VisibilityOff

                                val description =
                                    if (confirmPassWordIsVisible) "Hide password" else "Show password"

                                IconButton(onClick = {
                                    confirmPassWordIsVisible = !confirmPassWordIsVisible
                                }) {
                                    Icon(imageVector = image, description)
                                }
                            }
                        )
                        if (confirmPassword.length > 1 && (password == confirmPassword).not())
                            Text(
                                text = "Invalid Password (Should be a minimum of 8 characters and contain A-Za-z0-9)",
                                color = MaterialTheme.colors.error,
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                    }

                }

                Button(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth(),
                    enabled = isEnabled,
                    onClick = {
                        when (isSigningIn) {
                            true -> signIn()
                            false -> signUp()
                        }
                    }) {
                    Text(
                        modifier = Modifier.padding(4.dp),
                        text = (if (isSigningIn) "Sign in" else "sign up").uppercase()
                    )
                }
            }

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(message)
            TextButton(
                modifier = Modifier.padding(start = 16.dp),
                onClick = {
                    isSigningIn = isSigningIn.not()
                    password = ""
                    confirmPassword = ""
                }) {
                Text(modifier = Modifier.padding(4.dp), text = action)
            }
        }
    }

}

@Preview
@Composable
fun AuthSectionPreview() {
    AuthSection(AuthNavigator({}, {}))
}