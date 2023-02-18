package com.mambo.poetree.android.authentication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.helpers.MobileScreen
import com.mambo.poetree.utils.isValidEmail
import com.mambo.poetree.utils.isValidPassword

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@OptIn(ExperimentalUnitApi::class)
@Composable
fun AuthenticationScreenContent(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {

    var isSigningIn by remember { mutableStateOf(true) }
    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordIsVisible by rememberSaveable { mutableStateOf(false) }

    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassWordIsVisible by rememberSaveable { mutableStateOf(false) }

    val (title, message, action) = when (isSigningIn) {
        true -> Triple("Welcome \nBack", "Opps, don't have an account?", "Sign Up")
        false -> Triple("Create \nAccount", "Wait, ain't you a veteran?", "Sign In")
    }

    val isEnabled = when (isSigningIn) {
        true -> email.isValidEmail() and password.isValidPassword()
        false -> email.isValidEmail() and password.isValidPassword() and (password == confirmPassword)
    }

    fun navigateToNextScreen(hasSetup: Boolean) {
        when (hasSetup) {
            true -> {
                navController.navigate(route = MobileScreen.Feed.route) {
                    popUpTo(MobileScreen.Feed.route)
                }
            }
            false -> {
                navController.navigate(route = MobileScreen.Account.route) {
                    popUpTo(MobileScreen.Account.route)
                }
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = MaterialTheme.shapes.medium,
        ) {
            Column(modifier = Modifier.background(MaterialTheme.colors.primary)) {
                Image(
                    modifier = Modifier
                        .height(48.dp)
                        .width(48.dp),
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = "app icon"
                )
            }
        }
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
                    visible = !isSigningIn,
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
                        if (confirmPassword.length > 1 && password.equals(confirmPassword)
                                .not()
                        )
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
                            true -> {
                                viewModel.signIn(email = email, password = password) {
                                    navigateToNextScreen(hasSetup = it)
                                }
                            }
                            false -> {
                                viewModel.signUp(email = email, password = password) {
                                    navigateToNextScreen(hasSetup = it)
                                }
                            }
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

@Composable
fun AuthenticationScreen(navController: NavController) {
    AuthenticationScreenContent(navController = navController)
}

@Preview
@Composable
fun AuthenticationScreenPreview() {
    val context = LocalContext.current
    AuthenticationScreen(navController = NavController(context = context))
}