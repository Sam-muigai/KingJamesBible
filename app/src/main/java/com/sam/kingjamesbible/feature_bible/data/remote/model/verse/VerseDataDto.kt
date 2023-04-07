package com.sam.kingjamesbible.feature_bible.data.remote.model.verse

import com.sam.kingjamesbible.feature_bible.data.local.verse.VersesLocal

data class VerseDataDto(
    val bibleId: String,
    val bookId: String,
    val content: String,
    val copyright: String,
    val id: String,
    val next: NextDto,
    val number: String,
    val previous: PreviousDto,
    val reference: String,
    val verseCount: Int
){
    fun toVerseLocal():VersesLocal{
        return VersesLocal(
            nextId = next.id,
            id = id,
            previousId = previous.id,
            content = content,
            reference = reference
        )
    }
}
