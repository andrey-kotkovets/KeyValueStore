package com.ak.data.store

import java.util.LinkedList

class KeyValueStoreImpl : KeyValueStore {

    private val stack: LinkedList<MutableMap<String, String>> = LinkedList()

    private val currentStore: MutableMap<String, String>
        get() = stack.last()

    init {
        stack.add(mutableMapOf())
    }

    /**
     * Store the value for key
     */
    override fun set(key: String, value: String) {
        currentStore[key] = value
    }

    /**
     * @return the current value for key
     */
    override fun get(key: String): String? {
        return currentStore[key]
    }

    /**
     * Remove the entry for key
     */
    override fun delete(key: String) {
        currentStore.remove(key)
    }

    /**
     * @return the number of keys that have the given value
     */
    override fun count(value: String): Int {
        return currentStore.values.count { it == value }
    }

    /**
     * Start a new transaction
     */
    override fun begin() {
        stack.add(currentStore.toMutableMap())
    }

    /**
     * Complete the current transaction
     *
     * @return True if a transaction is completed successfully, false otherwise.
     */
    override fun commit(): Boolean {
        if (stack.size > 1) {
            val lastIndex = stack.size - 1
            stack.removeAt(lastIndex - 1)
            return true
        }
        return false
    }

    /**
     * Revert to state prior to BEGIN call
     *
     * @return True if rollback is completed successfully, false otherwise.
     */
    override fun rollback(): Boolean {
        if (stack.size > 1) {
            stack.removeLast()
            return true
        }
        return false
    }
}
