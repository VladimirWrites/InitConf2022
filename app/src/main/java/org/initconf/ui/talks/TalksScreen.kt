package org.initconf.ui.talks

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import org.initconf.R
import org.initconf.data.talks
import org.initconf.ui.model.Talk
import org.initconf.ui.theme.InitConf2022Theme

@Composable
fun TalksScreen(viewModel: TalksViewModel = viewModel()) {
    Box(modifier = Modifier.fillMaxSize()) {
        val state = viewModel.state
        ProgressView(
            isLoading = state.isLoading,
            modifier = Modifier.align(Center)
        )
        TalksList(
            state = state.talks
        )
        Snackbar(
            textResId = state.errorResId,
            onActionClick = { viewModel.loadTalks() },
            modifier = Modifier.align(BottomCenter)
        )
    }
}

@Composable
fun TalksList(
    state: List<Talk>
) {
    LazyColumn {
        items(state) { talk ->
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

@Composable
private fun Snackbar(
    textResId: Int?,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (textResId == null) return
    val hostState = SnackbarHostState()
    val scope = rememberCoroutineScope()
    val message = stringResource(textResId)
    val actionLabel = stringResource(R.string.action_refresh)
    SnackbarHost(hostState = hostState, modifier)
    LaunchedEffect(
        key1 = hostState,
        block = {
            scope.launch {
                val result = hostState.showSnackbar(
                    message = message,
                    actionLabel = actionLabel,
                    duration = SnackbarDuration.Indefinite
                )
                if (result == SnackbarResult.ActionPerformed) {
                    onActionClick()
                }
            }
        }
    )
}

@Composable
private fun ProgressView(isLoading: Boolean, modifier: Modifier = Modifier) {
    if (isLoading) {
        CircularProgressIndicator(modifier = modifier)
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    InitConf2022Theme {
        TalksList(state = talks)
    }
}