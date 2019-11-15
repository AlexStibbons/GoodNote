package com.example.goodnote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.goodnote.database.daos.*
import com.example.goodnote.database.models.*
import com.example.goodnote.utils.DB_NAME
import com.example.goodnote.utils.DUMMY_TEXT
import com.example.goodnote.utils.DUMMY_TITLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [Note::class, Tag::class, JoinNoteTag::class],
    version = 2
)
abstract class LocalDb : RoomDatabase() {

    // get all daos
    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
    abstract fun joinNoteTagDao(): JoinNoteTagDao

    companion object {

        @Volatile
        private var instance: LocalDb? = null

        /*operator*/fun getInstance(context: Context): LocalDb {
            return instance ?: synchronized(this) { // more info on this here
                instance ?: buildDb(context).also { instance = it }
            }
        }

        private fun buildDb(context: Context): LocalDb {
            return Room.databaseBuilder(context, LocalDb::class.java, DB_NAME).build()
        }
    }

    // local db would need scope every time it's initialized, so...
    private class LocalDbCallback(private val scope: CoroutineScope) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                scope.launch {
                    var noteDao = database.noteDao()

                    noteDao.addNote(Note(DUMMY_TITLE, DUMMY_TEXT))
                    noteDao.addNote(Note("epistolary?", DUMMY_TEXT))
                    noteDao.addNote(Note("smth", "lighthouse keepers? village? small island?"))

                }
            }
        }
    }
}