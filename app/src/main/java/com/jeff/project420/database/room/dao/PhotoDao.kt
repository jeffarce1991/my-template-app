package com.jeff.project420.database.room.dao

import androidx.room.*
import com.jeff.project420.database.local.Photo

@Dao
public interface PhotoDao {
    @Query("Select * FROM photo")
    fun getAll(): List<Photo>

    @Query("Select * FROM photo WHERE id IN (:photoIds)")
    fun loadAllByIds(photoIds: IntArray): List<Photo>


    @Query("SELECT * FROM photo WHERE title LIKE :title AND title LIMIT 1")
    fun findByTitle(title: String): Photo

    @Insert
    fun insertAll(vararg photos: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(photos: List<Photo>)

    @Insert
    fun insert(photo: Photo)

    @Delete
    fun delete(photo: Photo)

}