package com.example.goodnote.utils

import com.example.goodnote.database.models.Note

const val DB_NAME = "GoodNoteDB"

const val DUMMY_TITLE = "The Flannan Lighthouse disappearances"
const val DUMMY_TEXT = "What the Hesperus crew did find at the lighthouse was a set of perplexing clues. The " +
        "replacement keeper, Joseph Moore, was the first to investigate, and reported an all-encompassing " +
        "sense of dread as he ascended the cliff toward the newly constructed lighthouse. Inside, " +
        "the kitchen table contained plates of meat, potatoes, and pickles. The clock was stopped, " +
        "and there was an overturned chair nearby. The lamp was ready for lighting, and two of the " +
        "three oilskin coats belonging to Thomas Marshall, James Ducat, and Donald McArthur were gone. " +
        "The gate and door were firmly shut.\n" +
        "These clues only led to more questions. Why would one of the keepers have gone out without " +
        "his coat—and for that matter, why would all three have left together at all when the rules " +
        "forbade it? Someone needed to man the post at all times, so something unusual must have drawn " +
        "them out. When Moore returned with his report, Harvey had the island searched. The hunt came up " +
        "empty. The captain then sent a telegram to the mainland ..."

// dummy notes
internal var dummyNotes: MutableList<Note> = mutableListOf(
    Note(DUMMY_TITLE, DUMMY_TEXT).apply { id = 1 },
    Note("Title Two", "text").apply { id = 2 },
    Note("Title Three", DUMMY_TEXT).apply { id = 3 },
    Note("Title Four", DUMMY_TEXT).apply { id = 4 },
    Note("Title Five", "text").apply { id = 5 },
    Note("Title Six", "text").apply { id = 6 },
    Note("Title Seven", "text").apply { id = 7 },
    Note("Title Eight", DUMMY_TEXT).apply { id = 8 },
    Note("Title Nine", "text").apply { id = 9 },
    Note("Title Ten", "text").apply { id = 10 }
)