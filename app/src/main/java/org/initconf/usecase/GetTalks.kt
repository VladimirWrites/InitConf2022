package org.initconf.usecase

import org.initconf.data.Result
import org.initconf.data.TalksRemoteSource
import org.initconf.ui.model.Talks
import javax.inject.Inject

class GetTalks @Inject constructor(private val talksRemoteSource: TalksRemoteSource) {
    suspend operator fun invoke(): Result<Talks> {
        return talksRemoteSource.getTalks()
    }
}