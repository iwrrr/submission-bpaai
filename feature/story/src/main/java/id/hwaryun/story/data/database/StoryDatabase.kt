package id.hwaryun.story.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.hwaryun.story.data.database.dao.RemoteKeysDao
import id.hwaryun.story.data.database.dao.StoryDao
import id.hwaryun.story.data.model.entity.RemoteKeys
import id.hwaryun.story.data.model.entity.StoryEntity

@Database(
    entities = [StoryEntity::class, RemoteKeys::class],
    version = 1,
    exportSchema = false
)
abstract class StoryDatabase : RoomDatabase() {

    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}