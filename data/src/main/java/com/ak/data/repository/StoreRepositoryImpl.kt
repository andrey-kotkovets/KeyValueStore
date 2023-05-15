package com.ak.data.repository

import com.ak.data.store.KeyValueStore
import com.ak.domain.exception.MissingKeyException
import com.ak.domain.exception.NoTransactionException
import com.ak.domain.repository.StoreRepository

class StoreRepositoryImpl(
    private val keyValueStore: KeyValueStore
) : StoreRepository {

    override fun set(key: String, value: String) {
        keyValueStore.set(key, value)
    }

    override fun get(key: String): String {
        return keyValueStore.get(key) ?: throw MissingKeyException()
    }

    override fun delete(key: String) {
        keyValueStore.delete(key)
    }

    override fun count(value: String): Int {
        return keyValueStore.count(value)
    }

    override fun begin() {
        keyValueStore.begin()
    }

    override fun commit() {
        if (!keyValueStore.commit()) {
            throw NoTransactionException()
        }
    }

    override fun rollback() {
        if (!keyValueStore.rollback()) {
            throw NoTransactionException()
        }
    }
}
