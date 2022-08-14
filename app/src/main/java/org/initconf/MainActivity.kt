package org.initconf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
    Column() {
        Text(text = talk.title)
        Text(text = talk.speaker.name)
        Text(text = talk.speaker.title)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InitConf2022Theme {
        TalkCard(talk)
    }
}