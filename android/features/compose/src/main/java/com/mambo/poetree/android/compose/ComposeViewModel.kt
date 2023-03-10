package com.mambo.poetree.android.compose

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.darkrockstudios.richtexteditor.model.RichTextValue
import com.mambo.poetree.AppMonitor.hideLoading
import com.mambo.poetree.AppMonitor.showDialog
import com.mambo.poetree.AppMonitor.showLoading
import com.mambo.poetree.data.domain.Poem
import com.mambo.poetree.data.domain.Topic
import com.mambo.poetree.data.remote.CreatePoemRequest
import com.mambo.poetree.data.remote.EditPoemRequest
import com.mambo.poetree.data.repositories.PoemRepository
import com.mambo.poetree.data.repositories.TopicsRepository
import com.mambo.poetree.utils.DialogData
import com.mambo.poetree.utils.DialogType
import kotlinx.coroutines.launch

/**
 * @project Poetree
 * @author mambobryan
 * @email mambobryan@gmail.com
 * Wed 15 Feb 2023
 */
class ComposeViewModel(savedStateHandle: SavedStateHandle) : ViewModel() {

    private val poemId: String? = savedStateHandle["id"]
    private val poemType: String? = savedStateHandle["type"]
    private val topicId: String? = savedStateHandle["topic"]

    private val poemRepository = PoemRepository()
    private val topicRepository = TopicsRepository()

    var poem by mutableStateOf<Poem?>(null)

    var topics by mutableStateOf<List<Topic>>(emptyList())

    var topic by mutableStateOf<Topic?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var content by mutableStateOf(RichTextValue.get())
        private set

    var isButtonEnabled by mutableStateOf(getIsButtonEnabled())
        private set

    var isValidPoem by mutableStateOf(getValidPoemDetails())
        private set

    init {

        getPoem()

        getTopic()

        viewModelScope.launch {
            val response = topicRepository.getTopics(page = 1)
            if (response.isSuccessful) {
                topics = response.data?.list?.map { it.toDomain() } ?: listOf()
            }
        }
    }

    private fun getPoem() {
        if (poemId != null)
            viewModelScope.launch {
                when (Poem.Type.valueOf(poemType!!)) {
                    Poem.Type.DRAFT -> getLocalPoem(id = poemId)
                    else -> getRemotePoem(id = poemId)
                }
            }
    }

    private fun getLocalPoem(id: String) {
        viewModelScope.launch {
            val mPoem = poemRepository.getDraft(id = id)
            if (mPoem != null) updatePoem(poem = mPoem)
        }
    }

    private fun getRemotePoem(id: String) {
        viewModelScope.launch {
            val response = poemRepository.getPublished(poemId = id)
            if (response.isSuccessful) {
                val mPoem = response.data
                mPoem?.let { updatePoem(poem = it) }
            }
        }
    }

    private fun getTopic() {
        if (topicId.isNullOrBlank().not())
            viewModelScope.launch {
                topicId?.let {
                    val response = topicRepository.get(topicId = it.toInt())
                    if (response.isSuccessful) {
                        val mTopic = response.data?.toDomain()
                        mTopic?.let { topic -> updateTopic(topic = topic) }
                    }
                }
            }
    }

    private fun getIsButtonEnabled() = when (poem != null) {
        true -> (isSameTitle() and isSameContent() and isSameTopic()).not()
        else -> title.isNotBlank() and content.value.text.isNotBlank()
    }

    private fun isSameTopic() = poem?.topic == topic

    private fun isSameContent() = poem?.content == content.value.text

    private fun isSameTitle() = poem?.title == title

    private fun getValidPoemDetails() = when (poem != null) {
        true -> (poem?.title?.isNotBlank() == true) and (poem?.content?.isNotBlank() == true) and (poem?.topic != null)
        false -> title.isNotBlank() and content.value.text.isNotBlank() and (topic != null)
    }

    private fun updateStates() {
        isButtonEnabled = getIsButtonEnabled()
        isValidPoem = getValidPoemDetails()
    }

    private fun updatePoem(poem: Poem) {
        this.poem = poem
        topic = poem.topic
        title = poem.title
        content = RichTextValue.fromString(text = poem.content)
        updateStates()
    }

    fun updateTopic(topic: Topic) {
        this.topic = topic
        updateStates()
    }

    fun updateTitle(text: String) {
        title = text
        updateStates()
    }

    fun updateContent(value: RichTextValue) {
        content = value
        updateStates()
    }

    fun save(onSuccess: (Poem) -> Unit) {
        when (poem == null) {
            true -> createDraft(onSuccess)
            false -> {
                val type = poem!!.type

                if (type == Poem.Type.DRAFT)
                    updateLocalPoem(onSuccess)
                else
                    updatePublishedPoem(onSuccess)
            }
        }
    }

    private fun updatePublishedPoem(onSuccess: (Poem) -> Unit) {

        val request = EditPoemRequest(
            poemId = poem!!.id,
            title = title,
            content = content.value.text,
            html = null,
            topic = topic?.id
        )

        viewModelScope.launch {
            showLoading()
            val response = poemRepository.updatePublished(request = request)
            hideLoading()
            when (response.isSuccessful) {
                true -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Update Error",
                            description = response.message
                        )
                    )
                }
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.SUCCESS,
                            title = "Update Success",
                            description = response.message,
                            positiveAction = { response.data?.let { onSuccess(it) } }
                        )
                    )
                }
            }
        }
    }

    private fun updateLocalPoem(onSuccess: (Poem) -> Unit) {
        viewModelScope.launch {
            showLoading()
            val poem = poemRepository.updateDraft(
                id = poem!!.id,
                title = title,
                content = content.value.text,
                topic = topic
            )
            hideLoading()
            when (poem == null) {
                true -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Update Error",
                            description = "Couldn't update poem"
                        )
                    )
                }
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.SUCCESS,
                            title = "Update Success",
                            description = "Updated poem successfully",
                            positiveAction = { onSuccess(poem) }
                        )
                    )
                }
            }

        }
    }

    fun publish(onSuccess: (Poem) -> Unit) {
        viewModelScope.launch {
            val request = CreatePoemRequest(
                title = title,
                content = content.value.text,
                html = content.value.text,
                topic = topic!!.id
            )
            showLoading()
            val response = poemRepository.createPublished(request = request)
            hideLoading()
            when (response.isSuccessful) {
                true -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Success",
                            description = response.message,
                            positiveAction = { response.data?.let { onSuccess(it) } }
                        )
                    )
                }
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.SUCCESS,
                            title = "Failed",
                            description = response.message,
                            positiveAction = {}
                        )
                    )
                }
            }

        }
    }

    fun delete(onSuccess: () -> Unit) {
        if (poem != null) {
            val id = poem!!.id
            when (poem!!.type) {
                Poem.Type.DRAFT -> deleteDraft(id, onSuccess)
                else -> deletePublishedPoem(id, onSuccess)
            }
        }
    }

    private fun deleteDraft(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            showLoading()
            poemRepository.deleteDraft(id = id)
            hideLoading()
            onSuccess.invoke()
        }
    }

    private fun deletePublishedPoem(id: String, onSuccess: () -> Unit) {
        if (poemId != null)
            viewModelScope.launch {
                showLoading()
                val response = poemRepository.deletePublished(poemId = id)
                hideLoading()
                when (response.isSuccessful) {
                    true -> {
                        showDialog(
                            data = DialogData(
                                type = DialogType.ERROR,
                                title = "Success",
                                description = response.message,
                                positiveAction = { response.data?.let { onSuccess() } }
                            )
                        )
                    }
                    false -> {
                        showDialog(
                            data = DialogData(
                                type = DialogType.SUCCESS,
                                title = "Error",
                                description = response.message,
                                positiveAction = { }
                            )
                        )
                    }
                }
            }
    }

    private fun createDraft(onSuccess: (Poem) -> Unit) {
        viewModelScope.launch {
            showLoading()
            val poem =
                poemRepository.saveDraft(title = title, content = content.value.text, topic = topic)
            hideLoading()
            when (poem == null) {
                true -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.ERROR,
                            title = "Error",
                            description = "Couldn't save poem"
                        )
                    )
                }
                false -> {
                    showDialog(
                        data = DialogData(
                            type = DialogType.SUCCESS,
                            title = "Success",
                            description = "Saved poem successfully",
                            positiveAction = { onSuccess(poem) }
                        )
                    )
                }
            }

        }
    }

}