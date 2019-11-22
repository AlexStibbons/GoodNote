package com.example.goodnote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.goodnote.database.daos.*
import com.example.goodnote.database.models.*
import com.example.goodnote.utils.DB_NAME
import com.example.goodnote.utils.DEFAULT_TITLE
import com.example.goodnote.utils.DUMMY_TEXT
import com.example.goodnote.utils.DUMMY_TITLE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
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
            return Room.databaseBuilder(context, LocalDb::class.java, DB_NAME).addCallback(LocalDbCallback()).build()
        }
    }

    private class LocalDbCallback : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            instance?.let { database ->
                GlobalScope.launch {
                    var noteDao = database.noteDao()

                    noteDao.addNote(Note("research", DUMMY_TEXT, "fake1"))
                    noteDao.addNote(Note("Higher, my gallows", DUMMY_TEXT, "fake2"))
                    noteDao.addNote(Note("smth on fog", "lighthouse keepers? village? small island?", "fake3"))
                    noteDao.addNote(Note(DEFAULT_TITLE, "etc", "fake4"))
                    noteDao.addNote(Note("BEckoning the heart", DUMMY_TEXT, "fake5"))
                    noteDao.addNote(Note("Ballad of 3 mists", DUMMY_TEXT, "fake6"))
                    noteDao.addNote(Note("Our good and proper shoes", DUMMY_TEXT, "fake7"))
                    noteDao.addNote(Note("Hatchling", DUMMY_TEXT, "fake8"))
                    noteDao.addNote(Note("Quiet life of Galeb Rekah", DUMMY_TEXT, "fake9"))
                    noteDao.addNote(Note("the kinda summer", "that sucks the life out of you", "fake10"))


                    val tagDao = database.tagDao()

                    tagDao.addTag(Tag("people", "fake1"))
                    tagDao.addTag(Tag("world", "fake2"))
                    tagDao.addTag(Tag("minutiae", "fake3"))
                    tagDao.addTag(Tag("atmo", "fake4"))
                    tagDao.addTag(Tag("art", "fake5"))
                    tagDao.addTag(Tag("history", "fake6"))
                    tagDao.addTag(Tag("fairs", "fake7"))
                    tagDao.addTag(Tag("ww1", "fake8"))
                    tagDao.addTag(Tag("exp archeology", "fake9"))
                    tagDao.addTag(Tag("exp archeology 2", "fake10"))
                }
            }
        }
    }
}