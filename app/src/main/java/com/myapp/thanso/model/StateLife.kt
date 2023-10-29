package com.myapp.thanso.model

data class StateLife(
    val states: List<Int>,
    val peakAge: List<Int>,
    val peakYear: List<Int>
) {
    override fun toString(): String {
        return """
            Chặng 1: ${states[0]}
            Chặng 2: ${states[1]}
            Chặng 3: ${states[2]}
            Chặng 4: ${states[3]}
            Tuổi đỉnh cao 1: ${peakAge[0]}
            Tuổi đỉnh cao 2: ${peakAge[1]}
            Tuổi đỉnh cao 3: ${peakAge[2]}
            Tuổi đỉnh cao 4: ${peakAge[3]}
            Năm đỉnh cao 1: ${peakYear[0]}
            Năm đỉnh cao 2: ${peakYear[1]}
            Năm đỉnh cao 3: ${peakYear[2]}
            Năm đỉnh cao 4: ${peakYear[3]}
        """.trimIndent()
    }
}
