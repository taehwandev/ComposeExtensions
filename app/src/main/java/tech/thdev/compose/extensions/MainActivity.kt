package tech.thdev.compose.extensions

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tech.thdev.compose.extensions.keyboard.state.foundation.rememberKeyboardVisible
import tech.thdev.compose.extensions.keyboard.state.foundation.keyboardHide
import tech.thdev.compose.extensions.ui.theme.ComposeKeyboardStateTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeKeyboardStateTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .imePadding()
                        .keyboardHide()
                ) {
                    // test aaa
                    Greeting(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    modifier: Modifier
) {
    val typed = remember { mutableStateOf("") }
    val keyboardStateMessage = remember { mutableStateOf("") }
    val keyboardShow by rememberKeyboardVisible()
    LaunchedEffect(keyboardShow) {
        keyboardStateMessage.value = "Show keyboard".takeIf { keyboardShow } ?: "Hide keyboard"
    }

    Column(
        modifier = modifier
    ) {
        TextField(
            value = typed.value,
            onValueChange = {
                typed.value = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Text(
            text = keyboardStateMessage.value,
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
        )

        Spacer(
            modifier = Modifier
                .weight(1f)
        )

        val context = LocalContext.current
        if (keyboardShow) {
            Button(
                onClick = {
                    Toast.makeText(context, "${keyboardStateMessage.value} : ${typed.value}", Toast.LENGTH_SHORT).show()
                },
                shape = AbsoluteRoundedCornerShape(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "On keyboard button")
            }
        } else {
            Button(
                onClick = {
                    Toast.makeText(context, "${keyboardStateMessage.value} : ${typed.value}", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(text = "Button")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposeKeyboardStateTheme {
        Greeting(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}