package org.initconf.data

import org.initconf.ui.model.Talks
import retrofit2.http.GET

interface TalksService {

  @GET(value = "remotedata/talks.json")
  suspend fun getTalks(): Result<Talks>

}