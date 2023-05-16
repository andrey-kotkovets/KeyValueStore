package com.ak.keyvaluestore.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ak.keyvaluestore.R
import com.ak.keyvaluestore.model.Message
import com.ak.keyvaluestore.ui.theme.KeyValueStoreTheme
import com.ak.keyvaluestore.util.TestConstant
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val messages by viewModel.messages.collectAsStateWithLifecycle()
    MainContent(
        messages = messages,
        onNewCommandEntered = viewModel::onNewCommandEntered
    )
}

@Composable
fun MainContent(
    messages: List<Message>,
    onNewCommandEntered: (String) -> Unit = {}
) {
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        StoreTopAppBar()
        StoreMessages(
            messages = messages,
            scrollState = scrollState,
            modifier = Modifier.weight(1f),
        )
        CommandInput(
            onNewCommandEntered = onNewCommandEntered,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
    }
    if (messages.isNotEmpty()) {
        LaunchedEffect(messages.size) {
            coroutineScope.launch {
                scrollState.animateScrollToItem(messages.size - 1)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreTopAppBar() {
    TopAppBar(
        title = { Text(stringResource(R.string.app_name)) },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun StoreMessages(
    messages: List<Message>,
    scrollState: LazyListState,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = scrollState
    ) {
        items(messages) { item ->
            Text(
                text = item.text ?: stringResource(requireNotNull(item.textResId)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommandInput(
    onNewCommandEntered: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var text by remember { mutableStateOf("") }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val onSendAction = {
            onNewCommandEntered(text)
            text = ""
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .weight(1f)
                .testTag(TestConstant.INPUT_TEXT_FIELD),
            keyboardActions = KeyboardActions(onDone = { onSendAction() }),
            singleLine = true
        )
        IconButton(
            onClick = onSendAction,
            modifier = Modifier.testTag(TestConstant.INPUT_BUTTON)
        ) {
            Icon(
                imageVector = Icons.Filled.Send,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
private fun MainScreenPreview() {
    KeyValueStoreTheme {
        MainContent(
            messages = listOf(
                Message(text = "> set foo bar"),
                Message(text = "> get foo"),
                Message(text = "foo")
            ),
            onNewCommandEntered = {}
        )
    }
}
