package com.jeff.project420.database.usecase.local.loader

import com.jeff.project420.database.local.Photo
import io.reactivex.Single
import java.util.*

interface PhotoLocalLoader {
    fun loadAll(): Single<List<Photo>>
}