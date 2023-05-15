package com.ak.domain.repository

interface StoreRepository {

    fun set(key: String, value: String)

    fun get(key: String): String?

    fun delete(key: String)

    fun count(value: String): Int

    fun begin()

    fun commit()

    fun rollback()
}
