package org.initconf.data

sealed class Result<T> {
  class Success<T>(val data: T) : Result<T>()
  class Error<T>(val code: Int) : Result<T>()
  class Exception<T>(val throwable: Throwable) : Result<T>()
}

suspend fun <T> Result<T>.onSuccess(
  block: suspend (T) -> Unit
): Result<T> = apply {
  if (this is Result.Success) {
    block(data)
  }
}

suspend fun <T> Result<T>.onError(
  block: suspend (code: Int) -> Unit
): Result<T> = apply {
  if (this is Result.Error<T>) {
    block(code)
  }
}

suspend fun <T> Result<T>.onException(
  block: suspend (throwable: Throwable) -> Unit
): Result<T> = apply {
  if (this is Result.Exception) {
    block(throwable)
  }
}