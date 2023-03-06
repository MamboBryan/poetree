package com.mambo.poetree.feature.account

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.icu.util.Calendar
import android.widget.DatePicker
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.toString
import com.mambo.poetree.utils.isValidEmail
import kotlinx.coroutines.flow.collectLatest
import java.util.*

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 18 Feb 2023
 */
@Composable
fun AccountScreen(
    navController: NavController,
) {
    AccountScreenContent(navController = navController)
}

@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
@Composable
fun AccountScreenContent(
    navController: NavController,
    viewModel: AccountViewModel = viewModel()
) {

    val isSettingUp by viewModel.userHasSetup.collectAsState(initial = true)

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var email by rememberSaveable { mutableStateOf("") }

    var username by rememberSaveable { mutableStateOf("") }
    var usernameHasError by rememberSaveable { mutableStateOf(true) }

    var date by rememberSaveable { mutableStateOf<Calendar?>(null) }
    var dateHasError by rememberSaveable { mutableStateOf(true) }

    val options = listOf("Female", "Male", "Other")
    var expanded by remember { mutableStateOf(false) }
    var gender by rememberSaveable { mutableStateOf<String?>(null) }
    var genderHasError by rememberSaveable { mutableStateOf(true) }

    var about by rememberSaveable { mutableStateOf("") }
    var aboutHasError by rememberSaveable { mutableStateOf(true) }

    var isFinishClicked by rememberSaveable { mutableStateOf(false) }

    fun navigateBack() {
        if (isSettingUp)
            (context as Activity).finish()
        else
            navController.popBackStack()
    }

    Scaffold(
        topBar = {
            TopAppBar(backgroundColor = MaterialTheme.colors.surface) {

                IconButton(onClick = {
                    navigateBack()
                }) {
                    val (icon, description) = when (isSettingUp) {
                        true -> Pair(Icons.Filled.Close, "Close Application")
                        false -> Pair(Icons.Filled.ArrowBack, "Navigate Back")
                    }

                    Icon(imageVector = icon, contentDescription = description)
                }


                when (isSettingUp) {
                    true -> {
                        Row(
                            modifier = Modifier.weight(1f),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = PoetreeApp().name(),
                                fontWeight = FontWeight.Bold
                            )
                        }

                        IconButton(onClick = {

                        }) {
                            Icon(
                                imageVector = Icons.Filled.HelpOutline,
                                contentDescription = "Help"
                            )
                        }
                    }
                    false -> {
                        Text(text = "Account")
                    }
                }

            }
        }
    ) {

        Column(
            modifier = Modifier
                .padding(it)
                .padding(16.dp)
                .fillMaxSize(),
        ) {

            if (isSettingUp) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "Setup Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = TextUnit(24f, TextUnitType.Sp)
                )

                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = "Enter details to finish account setup"
                )
            }

            if (isSettingUp.not()) {
                TextField(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                    label = { Text("Email") },
                    value = email,
                    isError = email.length > 1 && email.isValidEmail().not(),
                    onValueChange = { mail -> email = mail },
                )
                if (email.length > 1 && email.isValidEmail().not())
                    Text(
                        text = "Invalid Email Address",
                        color = MaterialTheme.colors.error,
                        style = MaterialTheme.typography.caption,
                        modifier = Modifier.padding(start = 16.dp)
                    )
            }

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                value = username,
                onValueChange = { name ->
                    username = name
                    usernameHasError = username.isBlank()
                },
                label = { Text("Username") },
                singleLine = true,
                isError = username.length > 1 && username.isBlank(),
                placeholder = { Text("Username") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )

            TextField(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                readOnly = true,
                value = date?.time.toString(pattern = "EEE dd MMM yyyy"),
                onValueChange = { },
                label = { Text("Date Of Birth") },
                singleLine = true,
                isError = dateHasError && isFinishClicked,
                trailingIcon = {
                    IconButton(onClick = {
                        openDateDialog(context) { c ->
                            date = c
                            dateHasError = date == null
                        }
                    }) {
                        Icon(imageVector = Icons.Filled.CalendarMonth, "Pick Date")
                    }
                },
                interactionSource = remember { MutableInteractionSource() }.also { source ->
                    LaunchedEffect(source) {
                        source.interactions.collectLatest { interaction ->
                            if (interaction is PressInteraction.Release)
                                openDateDialog(context) { c ->
                                    date = c
                                    dateHasError = date == null
                                }
                        }
                    }
                }
            )

            ExposedDropdownMenuBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    readOnly = true,
                    value = gender ?: "",
                    onValueChange = { },
                    label = { Text("Gender") },
                    isError = genderHasError && isFinishClicked,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach { selectionOption ->
                        DropdownMenuItem(
                            onClick = {
                                gender = selectionOption
                                expanded = false
                                genderHasError = when (gender == null) {
                                    true -> true
                                    false -> gender!!.isBlank()
                                }

                            }
                        ) {
                            Text(text = selectionOption)
                        }
                    }
                }
            }

            TextField(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                value = about,
                onValueChange = { s ->
                    about = s
                    aboutHasError = about.isBlank()
                },
                label = {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(modifier = Modifier.padding(top = 6.dp), text = "About")
                    }
                },
                singleLine = true,
                isError = about.length > 1 && about.isBlank() && about.length > 125,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
            if (about.length > 1 && about.isBlank() && about.length > 125)
                Text(
                    text = "Cannot be blank or more than 125 characters",
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                )

            Button(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                enabled = usernameHasError.not() && dateHasError.not() && genderHasError.not() && aboutHasError.not(),
                onClick = {
                    isFinishClicked = true
                    viewModel.update(
                        username = username,
                        dateOfBirth = date?.time ?: Date(),
                        gender = gender ?: "",
                        about = about,
                        email = email
                    ) { isSuccessful -> if (isSuccessful && isSettingUp.not()) navController.popBackStack() }
                }
            ) {
                Text(modifier = Modifier.padding(vertical = 4.dp), text = "finish".uppercase())
            }

            if (isSettingUp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextButton(onClick = { viewModel.signOut() }) {
                        Text(text = "Sign Out")
                    }
                }

        }
    }

}

private fun openDateDialog(context: Context, block: (calendar: Calendar) -> Unit) {
    val today = Calendar.getInstance()

    val year = today.get(Calendar.YEAR)
    val month = today.get(Calendar.MONTH)
    val day = today.get(Calendar.DAY_OF_MONTH)

    val maximumDate = Calendar.getInstance()
    maximumDate.set(Calendar.YEAR, year - 15)

    val dialog = DatePickerDialog(
        context, { _: DatePicker, mYear: Int, mMonth: Int, mDay: Int ->

            val selectedDate = Calendar.getInstance()

            selectedDate.set(Calendar.YEAR, mYear)
            selectedDate.set(Calendar.MONTH, mMonth)
            selectedDate.set(Calendar.DAY_OF_MONTH, mDay)

            block.invoke(selectedDate)

        }, year, month, day
    )
    dialog.datePicker.maxDate = maximumDate.timeInMillis
    dialog.show()
}


@Preview
@Composable
fun AccountScreenPreview() {
    val context = LocalContext.current
    AccountScreen(navController = NavController(context = context))
}