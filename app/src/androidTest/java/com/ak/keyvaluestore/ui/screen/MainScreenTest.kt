package com.ak.keyvaluestore.ui.screen

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.ak.keyvaluestore.model.Message
import com.ak.keyvaluestore.util.TestConstant
import org.junit.Rule
import org.junit.Test

class MainScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun enterCommand_inputFieldIsEmptyAfterSending() {
        val command = "set foo bar"

        composeTestRule.setContent {
            MainContent(
                messages = emptyList()
            )
        }

        composeTestRule
            .onNodeWithTag(TestConstant.INPUT_TEXT_FIELD)
            .performTextInput(command)

        composeTestRule
            .onNodeWithTag(TestConstant.INPUT_TEXT_FIELD)
            .assertTextContains(command)

        composeTestRule
            .onNodeWithTag(TestConstant.INPUT_BUTTON)
            .performClick()

        composeTestRule
            .onNodeWithTag(TestConstant.INPUT_TEXT_FIELD)
            .assertTextContains("")
    }

    @Test
    fun commands_showCommandsOnScreen() {
        val command1 = "> set foo bar"
        val command2 = "> get foo"
        val result = "bar"
        val messages = listOf(
            Message(text = command1),
            Message(text = command2),
            Message(text = result),
        )

        composeTestRule.setContent {
            MainContent(messages)
        }

        composeTestRule
            .onNodeWithText(command1)
            .assertExists()

        composeTestRule
            .onNodeWithText(command2)
            .assertExists()

        composeTestRule
            .onNodeWithText(result)
            .assertExists()
    }
}
