package com.mambo.poetree.android.presentation.screens.auth

import androidx.compose.animation.*
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.android.R
import com.mambo.poetree.android.presentation.composables.LoadingDialog
import com.mambo.poetree.utils.isValidEmail
import com.mambo.poetree.utils.isValidPassword
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

enum class Section {
    STARTED, SIGN_IN, SIGN_UP
}

@OptIn(ExperimentalUnitApi::class)
@Destination
@Composable
fun AuthScreen(
    navController: DestinationsNavigator,
    viewModel: AuthViewModel = viewModel()
) {

    var section by remember { mutableStateOf(Section.STARTED) }

    val (title, message, action) = when (section) {
        Section.STARTED -> Triple("", "", "")
        Section.SIGN_IN -> Triple("Welcome \nBack", "Oh no, you don't have an account?", "Sign Up")
        Section.SIGN_UP -> Triple("Create \nAccount", "Wait, ain't you a veteran?", "Sign In")
    }

    var email by rememberSaveable { mutableStateOf("") }

    var password by rememberSaveable { mutableStateOf("") }
    var passwordIsVisible by rememberSaveable { mutableStateOf(false) }

    var confirmPassword by rememberSaveable { mutableStateOf("") }
    var confirmPassWordIsVisible by rememberSaveable { mutableStateOf(false) }

    val isEnabled = when (section) {
        Section.SIGN_UP -> email.isValidEmail() and password.isValidPassword() and (password == confirmPassword)
        Section.SIGN_IN -> email.isValidEmail() and password.isValidPassword()
        else -> false
    }

    AnimatedVisibility(
        visible = viewModel.isLoading
    ) {
        LoadingDialog()
    }

//    if (viewModel.error != null) {
//        PoetreeDialog(title = "Error", message = viewModel.error ?: "", onDismiss = {
//            viewModel.error = null
//        })
//    }
//
//    if (viewModel.success != null) {
//        PoetreeDialog(title = "Success", message = viewModel.success ?: "", onDismiss = {
//            viewModel.success = null
//        })
//    }


    AnimatedVisibility(
        visible = section == Section.STARTED,
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
        ) {

            Text(
                modifier = Modifier.padding(top = 24.dp),
                text = PoetreeApp().name(),
                style = MaterialTheme.typography.h3,
                fontWeight = FontWeight.Bold,
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = PoetreeApp().dummyPoem(),
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = PoetreeApp().dummyPoet(),
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.SemiBold,
                )
            }

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    section = Section.SIGN_IN
                }) {
                Text(modifier = Modifier.padding(vertical = 4.dp), text = "Get Started")
            }

        }
    }

    AnimatedVisibility(
        visible = section != Section.STARTED,
    ) {
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
                        visible = section == Section.SIGN_UP,
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
                            when (section) {
                                Section.STARTED -> {}
                                Section.SIGN_IN -> viewModel.signIn(
                                    email = email,
                                    password = password
                                )
                                Section.SIGN_UP -> viewModel.signUp(
                                    email = email,
                                    password = password
                                )
                            }
                        }) {
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = (if (section == Section.SIGN_IN) "Sign in" else "sign up").uppercase()
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
                        section = when (section) {
                            Section.SIGN_IN -> Section.SIGN_UP
                            else -> Section.SIGN_IN
                        }
                        password = ""
                        confirmPassword = ""
                    }) {
                    Text(modifier = Modifier.padding(4.dp), text = action)
                }
            }
        }
    }

}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(navController = EmptyDestinationsNavigator, viewModel = viewModel())
}