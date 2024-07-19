package com.shivam_raj.noteapp.database

enum class Priority(
    val id: Int,
) {
    High(4),
    Medium(3),
    Low(2),
    VeryLow(1),
    None(0);

    companion object {
        fun getValueWithId(int: Int): Priority {
            return when (int) {
                4 -> High
                3 -> Medium
                2 -> Low
                1 -> VeryLow
                else -> None
            }
        }
    }
}