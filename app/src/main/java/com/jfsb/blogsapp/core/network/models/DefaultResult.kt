package com.jfsb.blogsapp.core.network.models


sealed class DefaultResult<out T> {
    object Loading: DefaultResult<Nothing>()

    data class Success<out T>(
        val data: T
    ): DefaultResult<T>()

    data class Error(
        val message: String
    ): DefaultResult<Nothing>()
}