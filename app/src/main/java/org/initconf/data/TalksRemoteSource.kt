package org.initconf.data

import org.initconf.data.Result.Success
import org.initconf.data.Result.Error
import org.initconf.data.Result.Exception
import org.initconf.ui.model.Talks
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TalksRemoteSource @Inject constructor(
    private val service: TalksService
) {
    suspend fun getTalks() :Result<Talks> {
        return when (val response = service.getTalks()) {
            is Success -> {
                Success(response.data)
            }
            is Error -> {
                Error(response.code)
            }
            is Exception -> {
                Exception(response.throwable)
            }
        }
    }

}