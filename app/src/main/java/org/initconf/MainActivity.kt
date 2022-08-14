package org.initconf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import org.initconf.data.talk
import org.initconf.model.Talk
import org.initconf.ui.theme.InitConf2022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitConf2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    TalkCard(talk)
                }
            }
        }
    }
}

@Composable
fun TalkCard(talk: Talk) {
    Row() {
        AsyncImage(
            model = talk.speaker.image,
            contentDescription = "Image of ${talk.speaker}",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
        )
        Column() {
            Text(text = talk.title)
            Text(text = talk.speaker.name)
            Text(text = talk.speaker.title)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InitConf2022Theme {
        TalkCard(talk)
    }
}