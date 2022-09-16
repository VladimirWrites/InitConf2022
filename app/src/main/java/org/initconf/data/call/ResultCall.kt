package org.initconf.data.call

import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import org.initconf.data.Result

// Implementation taken from https://github.com/skydoves/retrofit-adapters

class ResultCall<T : Any>(private val delegate: Call<T>) : Call<Result<T>> {

    override fun enqueue(callback: Callback<Result<T>>) {
        delegate.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                val result = executeCall { response }
                callback.onResponse(this@ResultCall, Response.success(result))
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(this@ResultCall, Response.success(Result.Exception(t)))
            }
        })
    }

    override fun execute(): Response<Result<T>> {
        throw NotImplementedError()
    }

    override fun clone(): Call<Result<T>> = ResultCall(delegate.clone())
    override fun isExecuted(): Boolean = delegate.isExecuted
    override fun isCanceled(): Boolean = delegate.isCanceled
    override fun cancel() = delegate.cancel()
    override fun request(): Request = delegate.request()
    override fun timeout(): Timeout = delegate.timeout()

    private fun <T : Any> executeCall(
        execute: () -> Response<T>
    ): Result<T> {
        return try {
            val response = execute()
            val body = response.body()
            when {
                response.isSuccessful && body != null -> Result.Success(body)
                else -> Result.Error(code = response.code())
            }
        } catch (e: Throwable) {
            Result.Exception(e)
        }
    }
}