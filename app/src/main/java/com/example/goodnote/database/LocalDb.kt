package com.example.goodnote.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.goodnote.database.daos.*
import com.example.goodnote.database.entityModels.*
import com.example.goodnote.utils.DB_NAME
import com.example.goodnote.utils.DEFAULT_TITLE
import com.example.goodnote.utils.DUMMY_TEXT
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Database(
    entities = [NoteEntity::class, TagEntity::class, JoinNoteTagEntity::class],
    version = 3
)
abstract class LocalDb : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun tagDao(): TagDao
    abstract fun joinNoteTagDao(): JoinNoteTagDao

    companion object {

        @Volatile
        private var instance: LocalDb? = null

        /*operator*/fun getInstance(context: Context): LocalDb {
            return instance ?: synchronized(this) {
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
                    val noteDao = database.noteDao()
                    val tagDao = database.tagDao()
                    val joinDao = database.joinNoteTagDao()

                    noteDao.addNote(NoteEntity("research", DUMMY_TEXT, "fake1"))
                    noteDao.addNote(NoteEntity("Higher, my gallows", DUMMY_TEXT, "fake2"))
                    noteDao.addNote(NoteEntity(DEFAULT_TITLE, "etc", "fake3"))
                    noteDao.addNote(NoteEntity("Beckoning the heart", DUMMY_TEXT, "fake4"))
                    noteDao.addNote(NoteEntity("Ballad of 3 mists", DUMMY_TEXT, "fake5"))
                    noteDao.addNote(NoteEntity("Our good and proper shoes", DUMMY_TEXT, "fake6"))
                    noteDao.addNote(NoteEntity("Hatchling", DUMMY_TEXT, "fake7"))
                    noteDao.addNote(NoteEntity("Quiet life of Galeb Rekah", DUMMY_TEXT, "fake8"))
                    noteDao.addNote(NoteEntity("the kinda summer", "that sucks the life out of you", "fake9"))


                    tagDao.addTag(TagEntity("people", "fake1"))
                    tagDao.addTag(TagEntity("world", "fake2"))
                    tagDao.addTag(TagEntity("minutiae", "fake3"))
                    tagDao.addTag(TagEntity("atmo", "fake4"))
                    tagDao.addTag(TagEntity("art", "fake5"))
                    tagDao.addTag(TagEntity("history", "fake6"))
                    tagDao.addTag(TagEntity("fairs", "fake7"))
                    tagDao.addTag(TagEntity("folklore", "fake8"))
                    tagDao.addTag(TagEntity("isms", "fake9"))
                    tagDao.addTag(TagEntity("ancients", "fake10"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake1", "fake1"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake2", "fake1"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake3", "fake1"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake8", "fake1"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake8", "fake2"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake10", "fake2"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake4", "fake4"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake7", "fake5"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake1", "fake5"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake9", "fake5"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake6", "fake6"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake2", "fake7"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake8", "fake8"))

                    joinDao.addNoteTag(JoinNoteTagEntity("fake1", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake2", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake3", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake4", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake5", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake6", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake7", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake8", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake9", "fake9"))
                    joinDao.addNoteTag(JoinNoteTagEntity("fake10", "fake9"))
                }
            }
        }
    }
}