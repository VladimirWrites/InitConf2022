package org.initconf.ui.talks

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.initconf.R
import org.initconf.data.onError
import org.initconf.data.onException
import org.initconf.data.onSuccess
import org.initconf.usecase.GetTalks
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class TalksViewModel @Inject constructor(
    private val getTalks: GetTalks
) : ViewModel() {

    var state by mutableStateOf(TalksState())
        private set

    init {
        loadTalks()
    }

    fun loadTalks() {
        state = state.copy(isLoading = true)
        viewModelScope.launch {
            getTalks().onSuccess { talks ->
                state = state.copy(
                    isLoading = false,
                    errorResId = null,
                    talks = talks.listOfTalks
                )
            }.onError {
                state = state.copy(isLoading = false, errorResId = R.string.error_generic)
            }.onException { throwable ->
                val errorResId = when (throwable) {
                    is HttpException, is UnknownHostException -> R.string.error_no_internet_connection
                    else -> R.string.error_generic
                }
                state = state.copy(isLoading = false, errorResId = errorResId)
            }
        }
    }
}