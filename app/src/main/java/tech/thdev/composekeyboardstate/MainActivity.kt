package tech.thdev.composekeyboardstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.thdev.compose.extensions.keyboard.state.MutableExKeyboardStateSource
import tech.thdev.compose.extensions.keyboard.state.foundation.collectIsKeyboardAsState
import tech.thdev.compose.extensions.keyboard.state.foundation.removeFocusWhenKeyboardIsHidden
import tech.thdev.compose.extensions.keyboard.state.localowners.LocalMutableExKeyboardStateSourceOwner
import tech.thdev.composekeyboardstate.ui.theme.ComposeKeyboardStateTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKeyboardStateTheme {
                CompositionLocalProvider(
                    LocalMutableExKeyboardStateSourceOwner provides MutableExKeyboardStateSource()
                ) {
                    Scaffold(
                        modifier = Modifier
                            .fillMaxSize()
                            .removeFocusWhenKeyboardIsHidden()
                    ) {
                        val keyboardStateMessage = remember { mutableStateOf("") }
                        val keyboardShow by LocalMutableExKeyboardStateSourceOwner.current.collectIsKeyboardAsState()
                        LaunchedEffect(keyboardShow) {
                            keyboardStateMessage.value = "Show keyboard".takeIf { keyboardShow } ?: "Hide keyboard"
                        }
                        Column(
                            modifier = Modifier
                                .padding(it)
                        ) {
                            Greeting()
                            Text(
                                text = keyboardStateMessage.value,
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Greeting() {
    val typed = remember { mutableStateOf("") }

    TextField(
        value = typed.value,
        onValueChange = {
            typed.value = it
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeKeyboardStateTheme {
        Greeting()
    }
}