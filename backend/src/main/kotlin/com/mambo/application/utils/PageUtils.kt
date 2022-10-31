package com.mambo.application.utils

const val PAGE_SIZE = 20

data class PageData<T>(
    val current: Int, val next: Int?, val previous: Int?, val list: List<T>?
)

fun getLimitAndOffset(page: Int): Pair<Int, Long> {
    return when (page == 0) {
        true -> Pair(PAGE_SIZE, 0L)
        false -> {
            val offset = page.times(PAGE_SIZE).minus(PAGE_SIZE).toLong()
            Pair(PAGE_SIZE, offset)
        }
    }
}

fun <T> getPagedData(page: Int, result: List<T>): PageData<T> {

    val previousPage = if (page == 1) null else page - 1
    val nextPage = when (result.size < PAGE_SIZE) {
        true -> null
        false -> page + 1
    }

    return PageData(current = page, next = nextPage, previous = previousPage, list = result)
}

