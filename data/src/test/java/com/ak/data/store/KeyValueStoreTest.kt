package com.ak.data.store

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class KeyValueStoreTest {

    private val keyFoo = "foo"
    private val keyBar = "bar"
    private val keyBaz = "baz"
    private val value123 = "123"
    private val value456 = "456"
    private val value789 = "789"
    private val valueAbc = "abc"
    private val valueDef = "def"

    private lateinit var keyValueStore: KeyValueStore

    @Before
    fun init() {
        keyValueStore = KeyValueStoreImpl()
    }

    @Test
    fun `set and get a value`() {
        keyValueStore.apply {
            set(keyFoo, value123)
            assertEquals(value123, get(keyFoo))
        }
    }

    @Test
    fun `delete a value`() {
        keyValueStore.apply {
            set(keyFoo, value123)
            assertEquals(value123, get(keyFoo))
            delete(keyFoo)
            assertNull(get(keyFoo))
        }
    }

    @Test
    fun `count the number of occurrences of a value`() {
        keyValueStore.apply {
            set(keyFoo, value123)
            set(keyBar, value456)
            set(keyBaz, value123)
            assertEquals(2, count(value123))
            assertEquals(1, count(value456))
        }
    }

    @Test
    fun `commit a transaction`() {
        keyValueStore.apply {
            set(keyBar, value123)
            assertEquals(value123, get(keyBar))
            begin()
            set(keyFoo, value456)
            assertEquals(value123, get(keyBar))
            delete(keyBar)
            commit()
            assertNull(get(keyBar))
            assertFalse(rollback())
            assertEquals(value456, get(keyFoo))
        }
    }

    @Test
    fun `rollback a transaction`() {
        keyValueStore.apply {
            set(keyFoo, value123)
            set(keyBar, valueAbc)
            begin()
            set(keyFoo, value456)
            assertEquals(value456, get(keyFoo))
            set(keyBar, valueDef)
            assertEquals(valueDef, get(keyBar))
            rollback()
            assertEquals(value123, get(keyFoo))
            assertEquals(valueAbc, get(keyBar))
            assertFalse(commit())
        }
    }

    @Test
    fun `nested transactions`() {
        keyValueStore.apply {
            set(keyFoo, value123)
            set(keyBar, value456)
            begin()
            set(keyFoo, value456)
            begin()
            assertEquals(2, count(value456))
            assertEquals(value456, get(keyFoo))
            set(keyFoo, value789)
            assertEquals(value789, get(keyFoo))
            rollback()
            assertEquals(value456, get(keyFoo))
            delete(keyFoo)
            assertNull(get(keyFoo))
            rollback()
            assertEquals(value123, get(keyFoo))
        }
    }
}
