package com.ak.domain.model

sealed class CommandType(val name: String, val parts: Int) {

    class Set : CommandType("set", 3)
    class Get : CommandType("get", 2)
    class Delete : CommandType("delete", 2)
    class Count : CommandType("count", 2)
    class Begin : CommandType("begin", 1)
    class Commit : CommandType("commit", 1)
    class Rollback : CommandType("rollback", 1)
}
