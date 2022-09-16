package org.initconf.data.call

import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type
import org.initconf.data.Result

// Implementation taken from https://github.com/skydoves/retrofit-adapters

class ResultCallAdapter(private val type: Type) : CallAdapter<Type, Call<Result<Type>>> {

    override fun responseType(): Type = type
    override fun adapt(call: Call<Type>): Call<Result<Type>> = ResultCall(call)
}