package com.myapp.thanso.model

data class PersonalIndex(
    val lifePathIndex: Int,
    val birthIndex: Int,
    val soulIndex: Int,
    val rationalThinkingIndex: Int,
    val balanceIndex: Int,
    val attitudeIndex: Int,
    val passionIndex: List<Int>,
    val subconsciousPowerIndex: Int,
    val arrow: List<String>,
    val emptyArrow: List<String>,
    val missingIndex: List<Int>,
    val personalityIndex: Int,
    val missionIndex: Int,
    val orientationIndex: Int,
    val actualizationIndex: Int,
    val personMonthIndex: List<Int>,
    val personYearIndex: List<Int>,
    val stateLifeIndex: StateLife,
    val stateChallengeIndex: List<Int>
) {
    override fun toString(): String {
        return """
1. Chỉ số Đường đời : $lifePathIndex
2. Chỉ số Ngày sinh : $birthIndex
3. Chỉ số Nội tâm (Linh hồn) : $soulIndex
4. Chỉ số Tư duy lý trí : $rationalThinkingIndex
5. Chỉ số Cân bằng : $balanceIndex
6. Chỉ số Thái độ (Ngày sinh + Tháng sinh) : $attitudeIndex
7. Chỉ số Đam mê : $passionIndex
8. Sức mạnh tiềm thức: $subconsciousPowerIndex
9. Mũi tên có: $arrow
10. Mũi tên không có: ${"\n"} ${emptyArrow.joinToString(separator = "\n")}
11. Chỉ số thiếu: $missingIndex
12. Chỉ số Nhân cách: $personalityIndex
13. Chỉ số Sứ mệnh: $missionIndex
14. Chỉ số định hướng: $orientationIndex
15. Chỉ số hiện thực hóa: $actualizationIndex
15. Chỉ số Tháng cá nhân: $personMonthIndex
16. Chỉ số Năm cá nhân: $personYearIndex
17. Chỉ số chặng đường đời: 
    $stateLifeIndex
18. Chỉ số thách thức chặng: $stateChallengeIndex
        """.trimIndent()
    }
}
