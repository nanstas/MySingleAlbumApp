package ru.netology.mysinglealbumapp.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.netology.mysinglealbumapp.entity.TrackEntity

@Dao
interface TrackDao {
    @Query("SELECT * FROM TrackEntity ORDER BY id ASC")
    fun getTracks(): LiveData<List<TrackEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTracks(tracks: List<TrackEntity>)

    @Query(
        """
        UPDATE TrackEntity SET
        isPlayed = CASE WHEN isPlayed THEN 0 ELSE 1 END
        WHERE id = :id
    """
    )
    suspend fun isPlayed(id: Int)
}