package com.deeps.model.dao

import com.deeps.data.users

interface userDao {

    suspend fun insert(
        userId:Int,
        name:String,
        age:Int
    ):users?

    suspend fun findUserById(
        userId:Int
    ):users?



    suspend fun deleteUser(userId: Int):Int?

    suspend fun getAllUser():List<users>

    suspend fun updateAllData(
        userId: Int,
        name:String,
        age: Int):Int?

    suspend fun updateAnyData(
        id: Int,
        name: String,
        age: Int
    ):Int?
}