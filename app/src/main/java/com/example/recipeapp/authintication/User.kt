package com.example.recipeapp.authintication
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "email") val email: String,
    @ColumnInfo(name = "password") val password: String
)