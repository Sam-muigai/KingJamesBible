package com.sam.kingjamesbible.feature_bible.core

sealed class DataState<out T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : DataState<T>(data)
    class Error<T>(data: T, message: String) : DataState<T>(data, message)
    class Loading<T>(data: T? = null) : DataState<T>(data)
}