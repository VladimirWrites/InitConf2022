package org.initconf

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import org.initconf.data.talk
import org.initconf.data.talks
import org.initconf.model.Talk
import org.initconf.ui.theme.InitConf2022Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitConf2022Theme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    TalksList(talks = talks)
                }
            }
        }
    }
}

@Composable
fun TalksList(talks: List<Talk>) {
    LazyColumn() {
        items(talks) { talk ->
            TalkCard(talk)
        }
    }
}

@Composable
fun TalkCard(talk: Talk) {
    val context = LocalContext.current
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(all = 8.dp)
            .clickable { Toast.makeText(context,"${talk.title} clicked", Toast.LENGTH_SHORT).show() }
    ) {
        AsyncImage(
            model = talk.speaker.image,
            contentDescription = "Image of ${talk.speaker}",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .clip(CircleShape)
                .size(60.dp)
        )
        Column(
            modifier = Modifier.padding(all = 8.dp)
        ) {
            Text(text = talk.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Text(text = talk.speaker.name, fontWeight = FontWeight.Normal, fontSize = 14.sp)
            Text(text = talk.speaker.title, fontStyle = FontStyle.Italic, fontSize = 12.sp)
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InitConf2022Theme {
        TalksList(talks = talks)
    }
}