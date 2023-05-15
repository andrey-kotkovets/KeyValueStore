package com.ak.keyvaluestore.ui.screen

import androidx.lifecycle.ViewModel
import com.ak.keyvaluestore.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    private val _messages = MutableStateFlow(emptyList<Message>())
    val messages: StateFlow<List<Message>> = _messages

    fun onNewCommandEntered(text: String) {
        
    }
}
