package screens.account

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mambo.poetree.PoetreeApp
import com.mambo.poetree.data.local.preferences.UserPreferences
import com.mambo.poetree.data.remote.NetworkResult
import com.mambo.poetree.data.remote.SetupRequest
import com.mambo.poetree.data.remote.UserUpdateRequest
import com.mambo.poetree.data.repositories.UserRepository
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import com.mambo.poetree.utils.isValidEmail
import kotlinx.coroutines.launch
import showDialog
import startLoading
import stopLoading
import utils.extensions.toDate
import utils.extensions.toString
import java.util.*

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Sat 07 Jan 2023
 */

@Composable
fun AccountScreen() {

    val userHasSetup by UserPreferences().isUserSetup.collectAsState(initial = true)

    AccountScreenContent(isSettingUp = userHasSetup.not())

}

@OptIn(ExperimentalUnitApi::class)
@Composable
fun AccountScreenContent(isSettingUp: Boolean) {

    val scope = rememberCoroutineScope()
    val repository = UserRepository()

    var email by rememberSaveable { mutableStateOf("") }

    var username by rememberSaveable { mutableStateOf("") }
    var usernameHasError by rememberSaveable { mutableStateOf(true) }

    var date by rememberSaveable { mutableStateOf<Date?>(null) }
    var dateText by rememberSaveable { mutableStateOf("") }
    var dateHasError by rememberSaveable { mutableStateOf(true) }

    var expanded by remember { mutableStateOf(false) }
    var gender by rememberSaveable { mutableStateOf<String?>(null) }
    var genderHasError by rememberSaveable { mutableStateOf(true) }

    var about by rememberSaveable { mutableStateOf("") }
    var aboutHasError by rememberSaveable { mutableStateOf(true) }

    var isFinishClicked by rememberSaveable { mutableStateOf(false) }

    fun updateDate(s: String) {

        val str = s.replace(" ", "")
        if (str.isBlank()) return

        val isInvalidLength = str.length > 10
        val hasLetter = str.any { it.isLetter() }

        if (isInvalidLength or hasLetter) return

        val regex = "\\d{2}/\\d{2}/\\d{4}".toRegex()

        if (str.length == 10 && str.matches(regex)) {
            dateText = str
            date = str.toDate("dd/MM/yyyy")
        } else {
            dateText = str
        }

        dateHasError = date == null

    }

    fun updateGender(s: String) {
        gender = s
        genderHasError = false
        expanded = expanded.not()
    }

    fun handleResponse(response: NetworkResult<String>) {
        val (type, title, message) = when (response.isSuccessful) {
            true -> Triple(DialogType.SUCCESS, "Success", "Account updated successfully!")
            false -> Triple(DialogType.ERROR, "Error", response.message)
        }
        showDialog(
            data = DialogData(
                type = type,
                title = title,
                description = message,
            )
        )
    }

    fun setup(request: SetupRequest) {
        scope.launch {
            startLoading()
            val response = repository.setup(request = request)
            stopLoading()
            handleResponse(response)
        }
    }

    fun update(request: UserUpdateRequest) {
        scope.launch {
            startLoading()
            val response = repository.updateUser(request = request)
            stopLoading()
            handleResponse(response)
        }
    }

    fun update() {
        scope.launch {
            when (isSettingUp) {
                true -> {
                    val setupRequest = SetupRequest(
                        username = username,
                        dateOfBirth = date.toString("dd-MM-yyyy"),
                        gender = if (gender.equals("male", false)) 0 else 1,
                        bio = about
                    )

                    setup(request = setupRequest)
                }
                false -> {
                    val updateRequest = UserUpdateRequest(
                        username = username,
                        email = email,
                        dateOfBirth = date.toString("dd-MM-yyyy"),
                        gender = if (gender.equals("male", false)) 0 else 1,
                        bio = about
                    )

                    update(request = updateRequest)
                }
            }
        }
    }

    fun signOut() {
        UserPreferences().signOut()
    }

    fun navigateBack() {

//        if (isSettingUp)
//            (context as Activity).finish()
//        else
//            navController.popBackStack()
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center

        ) {
            Column(
                modifier = Modifier
                    .padding(it)
                    .padding(16.dp)
                    .widthIn(200.dp, 400.dp),
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
                            .padding(vertical = 8.dp)
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
                    value = dateText,
                    label = { Text("Date Of Birth") },
                    placeholder = { Text("dd/MM/yyyy") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = dateHasError && isFinishClicked,
                    trailingIcon = {
                        Icon(imageVector = Icons.Filled.CalendarToday, "Pick Date")
                    },
                    onValueChange = { s -> updateDate(s) }
                )

                Row(modifier = Modifier.fillMaxWidth()) {
                    TextField(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth(),
                        readOnly = true,
                        value = gender ?: "",
                        onValueChange = { },
                        label = { Text("Gender") },
                        singleLine = true,
                        isError = genderHasError && isFinishClicked,
                        trailingIcon = {
                            IconButton(onClick = {
                                expanded = expanded.not()
                            }) {
                                Icon(
                                    imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                                    "Expand gender menu"
                                )
                            }
                        },
                    )
                    Box(modifier = Modifier.weight(1f)) {
                        IconButton(onClick = { expanded = expanded.not() }) {
                            Icon(
                                if (expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp,
                                "Show gender menu"
                            )
                        }
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            DropdownMenuItem(onClick = {
                                updateGender("Female")
                            }) {
                                Text(text = "Female")
                            }
                            DropdownMenuItem(onClick = {
                                updateGender("Male")
                            }) {
                                Text(text = "Male")
                            }
                            DropdownMenuItem(onClick = {
                                updateGender("Other")
                            }) {
                                Text(text = "Other")
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
                if ((about.length > 1 && about.isBlank()) && about.length > 125)
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
                        update()
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 4.dp),
                        text = (if (isSettingUp) "finish" else "update").uppercase()
                    )
                }

                if (isSettingUp)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextButton(onClick = { signOut() }) {
                            Text(text = "Sign Out")
                        }
                    }

            }
        }
    }
}