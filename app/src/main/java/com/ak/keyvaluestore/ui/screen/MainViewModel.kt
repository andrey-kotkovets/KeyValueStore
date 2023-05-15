package com.ak.keyvaluestore.ui.screen

import androidx.lifecycle.ViewModel
import com.ak.domain.exception.MissingKeyException
import com.ak.domain.exception.NoTransactionException
import com.ak.domain.exception.UnknownCommandException
import com.ak.domain.interactor.StoreInteractor
import com.ak.keyvaluestore.R
import com.ak.keyvaluestore.model.Message
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val storeInteractor: StoreInteractor
) : ViewModel() {

    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages: StateFlow<List<Message>> = _messages

    fun onNewCommandEntered(text: String) {
        val commandText = text.trim()
        if (commandText.isNotBlank()) {
            val resultMessage = processCommand(commandText)
            _messages.update {
                it.toMutableList().apply {
                    add(buildCommandMessage(commandText))
                    if (resultMessage != null) add(resultMessage)
                }
            }
        }
    }

    private fun processCommand(commandText: String): Message? {
        return try {
            val result = storeInteractor.process(commandText)
            if (result != null && result != Unit) Message(text = result.toString()) else null
        } catch (e: MissingKeyException) {
            Message(textResId = R.string.key_not_set)
        } catch (e: UnknownCommandException) {
            Message(textResId = R.string.unknown_command)
        } catch (e: NoTransactionException) {
            Message(textResId = R.string.no_transaction)
        }
    }

    private fun buildCommandMessage(commandText: String): Message {
        return Message(text = "> $commandText")
    }
}
