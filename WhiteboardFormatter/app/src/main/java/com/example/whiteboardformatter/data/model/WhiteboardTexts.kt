package com.example.whiteboardformatter.data.model

data class WhiteboardTexts(
    var whiteboard: Whiteboard,
    var texts:Array<Text>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WhiteboardTexts

        if (whiteboard != other.whiteboard) return false
        if (!texts.contentEquals(other.texts)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = whiteboard.hashCode()
        result = 31 * result + texts.contentHashCode()
        return result
    }
}