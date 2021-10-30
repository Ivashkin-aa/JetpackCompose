package com.example.jetpackCompose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackCompose.ui.theme.Lab3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab3Theme {
                MyApp()
            }
        }
    }

    @Composable
    fun MyApp() {

        var shouldShowOnBoarding by rememberSaveable { mutableStateOf(true) }

        if (shouldShowOnBoarding) {
            OnBoardingScreen(onClicked = { shouldShowOnBoarding = false })
        } else {
            Conversation()
        }
    }

    @Composable
    fun OnBoardingScreen(onClicked: () -> Unit) {
        Surface {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "JetPack Compose. Codelab task",
                    style = MaterialTheme.typography.subtitle1
                )

                Button(
                    modifier = Modifier.padding(10.dp),
                    onClick = onClicked
                ) {
                    Text(text = "View")
                }

            }

        }
    }

    data class Message(
        val author: String,
        val body: String
    )

    @Composable
    fun Conversation() {
        val msg = SomeDialogue.conversationSample
        LazyColumn {
            items(msg) { msg ->
                MessageCard(msg)
            }
        }
    }

    @Composable
    fun MessageCard(text: Message) {
        Row(modifier = Modifier.padding(all = 5.dp)) {
            Image(
                painter = androidx.compose.ui.res.painterResource(id = R.drawable.image1),
                contentDescription = "Cartoon",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .border(2.dp, Blue, CircleShape)
            )

            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }

            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                Text(
                    text.author,
                    color = Blue,
                    style = MaterialTheme.typography.subtitle2
                )
                Spacer(modifier = Modifier.height(3.dp))
                Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                    Text(
                        text.body,
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.body2
                    )
                }
            }
        }
    }

    @Preview(uiMode = UI_MODE_NIGHT_YES)
    @Composable
    fun PreviewMessageCard() {
        Lab3Theme {
            Conversation()
        }
    }
}
