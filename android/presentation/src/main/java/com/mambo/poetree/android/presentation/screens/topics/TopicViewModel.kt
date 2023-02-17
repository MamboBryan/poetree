package com.mambo.poetree.android.presentation.screens.topics

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mambo.poetree.android.presentation.utils.AppMonitor.hideLoading
import com.mambo.poetree.android.presentation.utils.AppMonitor.showDialog
import com.mambo.poetree.android.presentation.utils.AppMonitor.showLoading
import com.mambo.poetree.data.domain.Topic
import com.mambo.poetree.data.remote.TopicRequest
import com.mambo.poetree.data.repositories.TopicsRepository
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Tue Feb 2023
 */
class TopicViewModel : ViewModel() {

    private val repository = TopicsRepository()

    var colors by mutableStateOf<List<Color>>(value = emptyList())
        private set

    var color by mutableStateOf(Color.Gray)
        private set

    var name by mutableStateOf("")
        private set

    var topic by mutableStateOf<Topic?>(null)
        private set

    init {
        generateRandomColors()
        color = colors.first()
    }

    private fun generateRgb(): Color {
        val (red, green, blue) = Triple(
            first = (0 until 128).random().plus(127),
            second = (0 until 128).random().plus(127),
            third = (0 until 128).random().plus(127)
        )

        val color = android.graphics.Color.rgb(red, green, blue)
        return Color(color)
    }

    private fun create(name: String, color: String, success: () -> Unit) {
        viewModelScope.launch {
            showLoading()

            val request = TopicRequest(name = name, color = color)
            val response = repository.create(request)

            hideLoading()

            if (response.isSuccessful.not()) {
                showDialog(
                    data = DialogData(
                        type = DialogType.ERROR,
                        title = "Error",
                        description = response.message,
                    )
                )
                return@launch
            }

            showDialog(
                data = DialogData(
                    type = DialogType.SUCCESS,
                    title = "Success",
                    description = response.message,
                    positiveAction = success
                )
            )

        }
    }

    private fun update(name: String, color: String, success: () -> Unit) {
        viewModelScope.launch {


            val id = topic!!.id

            val prevName = topic?.name
            val prevColor = topic?.name

            if (name.equals(prevName, true) and color.equals(prevColor, true)) {
                showDialog(
                    data = DialogData(
                        title = "Oh Oh",
                        description = "Nothing changed in topic details"
                    )
                )
                return@launch
            }

            showLoading()

            val request = TopicRequest(
                name = name.takeIf { it == prevName },
                color = color.takeIf { it == prevColor }
            )
            val response = repository.update(id, request)

            hideLoading()

            if (response.isSuccessful.not()) {
                showDialog(
                    data = DialogData(
                        type = DialogType.ERROR,
                        title = "Error",
                        description = response.message,
                    )
                )
                return@launch
            }

            showDialog(
                data = DialogData(
                    type = DialogType.SUCCESS,
                    title = "Success",
                    description = response.message,
                    positiveAction = success
                )
            )
        }
    }

    fun generateRandomColors() {

        val list = mutableListOf<Color>()

        repeat(5) { list.add(generateRgb()) }

        colors = list
    }

    fun updateName(str: String) {
        name = str
    }

    fun updateColor(selected: Color) {
        color = selected
    }

    fun save(success: () -> Unit) {

        val colorInHex = String.format("#%06X", 0xFFFFFF and color.toArgb())

        when (topic == null) {
            true -> create(name, colorInHex, success)
            false -> update(name, colorInHex, success)
        }
    }

    fun delete(onSuccess: () -> Unit) {
        viewModelScope.launch {
            showLoading()

            val id = topic!!.id
            val response = repository.delete(id)

            hideLoading()

            if (response.isSuccessful.not()) {
                showDialog(
                    data = DialogData(
                        type = DialogType.ERROR,
                        title = "Error",
                        description = response.message
                    )
                )
                return@launch
            }

            showDialog(
                data = DialogData(
                    type = DialogType.SUCCESS,
                    title = "Success",
                    description = response.message,
                    positiveAction = onSuccess
                )
            )
        }
    }

}