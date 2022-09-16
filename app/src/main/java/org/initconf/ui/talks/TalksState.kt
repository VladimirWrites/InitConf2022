package org.initconf.ui.talks

import androidx.annotation.StringRes
import org.initconf.ui.model.Talk

data class TalksState(
    var isLoading: Boolean = false,
    var talks: List<Talk> = emptyList(),
    @StringRes val errorResId: Int? = null,
)