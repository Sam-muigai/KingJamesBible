package com.sam.kingjamesbible.feature_bible.data.local.verse

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sam.kingjamesbible.feature_bible.domain.model.verses.Verses


@Entity
data class VersesLocal(
    val nextId:String,
    val previousId:String,
    val content:String,
    @PrimaryKey(autoGenerate = false)
    val id:String,
    val reference:String
){
    fun toVerse():Verses{
        return Verses(
            nextId = nextId,
            previousId = previousId,
            content = content,
            id = id,
            reference = reference
        )
    }
}