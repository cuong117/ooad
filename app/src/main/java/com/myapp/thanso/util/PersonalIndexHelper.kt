package com.myapp.thanso.util

import com.myapp.thanso.model.PersonalIndex
import com.myapp.thanso.model.StateLife
import java.text.Normalizer
import java.util.Calendar
import java.util.regex.Pattern
import kotlin.math.abs

class PersonalIndexHelper(firstName: String, lastName: String, birthDay: String) {

    private val date: List<Int>
    private val month: List<Int>
    private val year: List<Int>
    private val yearOriginal: Int
    private val firstName: String
    private val lastName: String
    private val listVowel = listOf('a', 'e', 'u', 'i', 'o')
    private val matrixOriginal = listOf(listOf(3, 6, 9), listOf(2, 5, 8), listOf(1, 4, 7))
    private val breakList = listOf(11, 22, 33)

    init {
        val listData = birthDay.split('/')
        date = convertIntToList(listData[0].toInt())
        month = convertIntToList(listData[1].toInt())
        year = convertIntToList(listData[2].toInt())
        yearOriginal = listData[2].toInt()
        this.firstName = removeAccent(firstName).lowercase()
        this.lastName = removeAccent(lastName).lowercase()
    }

    private val textToNumber = mapOf(
        'a' to 1,
        'j' to 1,
        's' to 1,
        'b' to 2,
        'k' to 2,
        't' to 2,
        'c' to 3,
        'l' to 3,
        'u' to 3,
        'd' to 4,
        'm' to 4,
        'v' to 4,
        'e' to 5,
        'n' to 5,
        'w' to 5,
        'f' to 6,
        'o' to 6,
        'x' to 6,
        'g' to 7,
        'p' to 7,
        'y' to 7,
        'h' to 8,
        'q' to 8,
        'z' to 8,
        'i' to 9,
        'r' to 9,
    )

    private fun removeAccent(s: String?): String {
        var temp: String = Normalizer.normalize(s, Normalizer.Form.NFD)
        val pattern: Pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+")
        temp = pattern.matcher(temp).replaceAll("")
        temp = temp.replace("Đ".toRegex(), "D")
        temp = temp.replace("đ".toRegex(), "d")
        return temp
    }

    private fun getLifePathIndex(): Int {
        val sumDate = sumListNumber(date)
        val sumMonth = sumListNumber(month)
        val sumYear = sumListNumber(year)
        return sumListNumber(listOf(sumDate, sumMonth, sumYear))
    }

    private fun getBirthIndex() = sumListNumber(date)

    private fun getSoulIndex(): Int {
        val firstNames = this.firstName.split(" ")
        val result = mutableListOf<Int>()
        firstNames.forEach {
            result.add(sumVowel(it))
        }
        result.add(sumVowel(lastName))
        return sumListNumber(result)
    }

    private fun getRationalThinkingIndex() =
        sumListNumberNoBreak(convertStringToListInt(lastName) + date)

    private fun getBalanceIndex(): Int {
        val listString = firstName.split(" ") + lastName
        val listValue = mutableListOf<Int>()
        listString.forEach {
            listValue.add(textToNumber[it[0]] ?: 0)
        }
        return sumListNumberNoBreak(listValue)
    }

    private fun getAttitudeIndex() = sumListNumberNoBreak(date + month)

    private fun getPassionIndex(): List<Int> {
        val listValue = convertStringToListInt(firstName + lastName)
        var maxFrequency = 0
        val listResult = mutableListOf<Int>()
        val valueFrequency = mutableMapOf<Int, Int>()
        listValue.forEach {
            valueFrequency[it] = (valueFrequency[it] ?: 0) + 1
        }
        valueFrequency.values.forEach { frequency ->
            if (frequency > maxFrequency) maxFrequency = frequency
        }
        valueFrequency.forEach { (key, value) -> if (value == maxFrequency) listResult.add(key) }
        return listResult
    }

    private fun getSubconsciousPowerIndex(missingIndex: Int) = 9 - missingIndex

    private fun getArrow(): List<List<String>> {
        val listData = date + month + year
        val listArrow = mutableListOf<String>()
        val listEmptyArrow = mutableListOf<String>()
        val matrix = matrixOriginal.map {
            it.map { value -> value to (value in listData) }
        }
        val length = matrix.size
        for (i in 0..<length) {
            // Vertical Arrow
            if (matrix[i][0].second && matrix[i][1].second && matrix[i][2].second) {

                listArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[i][0].first,
                            matrix[i][1].first,
                            matrix[i][2].first
                        )
                    )
                )

            } else if ((matrix[i][0].second || matrix[i][1].second || matrix[i][2].second).not()) {

                listEmptyArrow.add(
                    sortAndJoin(
                        listOf(matrix[i][0].first, matrix[i][1].first, matrix[i][2].first)
                    )
                )

            }

            // Diagonal arrow
            if (i == 0 && matrix[i][i].second && matrix[1][1].second && matrix[2][2].second) {

                listArrow.add(
                    sortAndJoin(
                        listOf(matrix[i][i].first, matrix[1][1].first, matrix[2][2].first)
                    )
                )

            } else if (i == 0 && (matrix[i][i].second || matrix[1][1].second || matrix[2][2].second).not()) {

                listEmptyArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[i][i].first, matrix[1][1].first, matrix[2][2].first
                        )
                    )
                )

            }

            // Diagonal arrow
            if (i == length - 1 && matrix[i][i].second && matrix[i - 1][i - 1].second && matrix[i - 2][i - 2].second) {

                listArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[i][i].first,
                            matrix[i - 1][i - 1].first,
                            matrix[i - 2][i - 2].first
                        )
                    )
                )

            } else if (i == length - 1 && (matrix[i][i].second || matrix[i - 1][i - 1].second || matrix[i - 2][i - 2].second).not()) {

                listEmptyArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[i][i].first,
                            matrix[i - 1][i - 1].first,
                            matrix[i - 2][i - 2].first
                        )
                    )
                )

            }

            // Horizontal Arrow
            if (matrix[0][i].second && matrix[1][i].second && matrix[2][i].second) {

                listArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[0][i].first, matrix[1][i].first, matrix[2][i].first
                        )
                    )
                )

            } else if ((matrix[0][i].second || matrix[1][i].second || matrix[2][i].second).not()) {

                listEmptyArrow.add(
                    sortAndJoin(
                        listOf(
                            matrix[0][i].first, matrix[1][i].first, matrix[2][i].first
                        )
                    )
                )

            }
        }
        return listOf(listArrow, listEmptyArrow)
    }

    private fun getMissingIndex(): List<Int> {
        val result = mutableListOf<Int>()
        val listName = convertStringToListInt(firstName + lastName).distinct().sorted()
        for (i in 1..9) {
            if (i !in listName) {
                result.add(i)
            }
        }
        return result
    }

    private fun getPersonalityIndex(): Int {
        val firstNames = this.firstName.split(" ")
        val result = mutableListOf<Int>()
        firstNames.forEach {
            result.add(sumConsonant(it))
        }
        result.add(sumConsonant(lastName))
        return sumListNumber(result)
    }

    private fun getMissionIndex(): Int {
        val firsts = firstName.split(" ")
        val result = mutableListOf<Int>()
        firsts.forEach {
            result.add(sumListNumber(convertStringToListInt(it)))
        }
        result.add(sumListNumber(convertStringToListInt(lastName)))
        return sumListNumber(result)
    }

    private fun getOrientationIndex(lifePathIndex: Int, missionIndex: Int): Int {
        val life = sumListNumberNoBreak(convertIntToListNoBreak(lifePathIndex))
        val mission = sumListNumberNoBreak(convertIntToListNoBreak(missionIndex))
        return sumListNumberNoBreak(convertIntToListNoBreak(abs(life - mission)))
    }

    private fun getActualizationIndex(lifePathIndex: Int, missionIndex: Int): Int {
        val life = sumListNumberNoBreak(convertIntToListNoBreak(lifePathIndex))
        val mission = sumListNumberNoBreak(convertIntToListNoBreak(missionIndex))
        return sumListNumberNoBreak(listOf(life, mission))
    }

    private fun getPersonalMonthIndex(): List<Int> {
        val calendar = Calendar.getInstance()
        val currentMonth = calendar.get(Calendar.MONTH) + 1
        val result = mutableListOf<Int>()
        for (i in currentMonth..<currentMonth + CONSECUTIVE) {
            result.add(
                sumListNumberNoBreak(year + convertIntToList(i))
            )
        }
        return result
    }

    private fun getPersonalYearIndex(): List<Int> {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val result = mutableListOf<Int>()
        for (i in currentYear..<currentYear + CONSECUTIVE) {
            result.add(
                sumListNumberNoBreak(date + month + convertIntToList(i))
            )
        }
        return result
    }

    private fun getStateLifeIndex(lifePathIndex: Int): StateLife {
        val lifePath = sumListNumberNoBreak(convertIntToListNoBreak(lifePathIndex))
        val date = sumListNumberNoBreak(date)
        val month = sumListNumberNoBreak(month)
        val year = sumListNumberNoBreak(year)
        val state1 = sumListNumberNoBreak(listOf(date, month))
        val state2 = sumListNumberNoBreak(listOf(date, year))
        val state3 = sumListNumberNoBreak(listOf(state1, state2))
        val state4 = sumListNumber(listOf(month, year))
        val age1 = AGE_DEFAULT - lifePath
        val age2 = age1 + AGE_STEP
        val age3 = age2 + AGE_STEP
        val age4 = age3 + AGE_STEP
        val year1 = yearOriginal + age1
        val year2 = year1 + AGE_STEP
        val year3 = year2 + AGE_STEP
        val year4 = year3 + AGE_STEP
        return StateLife(
            states = listOf(state1, state2, state3, state4),
            peakAge = listOf(age1, age2, age3, age4),
            peakYear = listOf(year1, year2, year3, year4)
        )
    }

    private fun getStateChallengeIndex(): List<Int> {
        val date = sumListNumberNoBreak(date)
        val month = sumListNumberNoBreak(month)
        val year = sumListNumberNoBreak(year)
        val state1 = abs(month - date)
        val state2 = abs(date - year)
        val state3 = abs(state1 - state2)
        val state4 = abs(month - year)
        return listOf(state1, state2, state3, state4)
    }

    private fun sumListNumber(list: List<Int>): Int {
        var result = list.sum()
        while (result.toString().length > 1 && result !in breakList) {
            val newList = convertIntToList(result)
            result = newList.sum()
        }
        return result
    }

    private fun sumListNumberNoBreak(list: List<Int>): Int {
        var result = list.sum()
        while (result.toString().length > 1) {
            val newList = convertIntToListNoBreak(result)
            result = newList.sum()
        }
        return result
    }

    private fun convertIntToListNoBreak(number: Int): List<Int> {
        val result = number.toString()
        return result.map {
            it.toString().toInt()
        }
    }

    private fun convertIntToList(number: Int): List<Int> {
        if (number in breakList) {
            return listOf(number)
        }
        val result = number.toString()
        return result.map {
            it.toString().toInt()
        }
    }

    private fun convertStringToListInt(string: String): List<Int> {
        val listValue = mutableListOf<Int>()
        string.forEach {
            listValue.add(textToNumber[it.lowercaseChar()] ?: 0)
        }
        return listValue
    }

    private fun sumConsonant(string: String): Int {
        val length = string.length
        val listVowelValue = mutableListOf<Int>()
        for (i in 0..<length) {
            val isNotVowel = (string[i].lowercaseChar() in listVowel).not()
            val isNotY = yIsVowel(string, i, length).not()
            if (isNotVowel) {
                listVowelValue.add(textToNumber[string[i]] ?: 0)
            } else if (string[i].toString().equals("y", ignoreCase = true) && isNotY) {
                listVowelValue.add(textToNumber[string[i]] ?: 0)
            }
        }
        return sumListNumber(listVowelValue)
    }

    private fun sumVowel(string: String): Int {
        val length = string.length
        val listVowelValue = mutableListOf<Int>()
        for (i in 0..<length) {
            val isVowel = string[i].lowercaseChar() in listVowel
            val isY = yIsVowel(string, i, length)
            if (isVowel) {
                listVowelValue.add(textToNumber[string[i]] ?: 0)
            } else if (string[i].toString().equals("y", ignoreCase = true) && isY) {
                listVowelValue.add(textToNumber[string[i]] ?: 0)
            }
        }
        return sumListNumber(listVowelValue)
    }

    private fun yIsVowel(string: String, index: Int, length: Int): Boolean {
        return if (string[index].equals('Y', ignoreCase = true)) {
            if (index == 0 && length > 1) {
                (string[1].lowercaseChar() in listVowel).not()
            } else if (index > 0 && index < length - 1) {
                println("${(string[index - 1].lowercaseChar() in listVowel).not() && (string[index + 1].lowercaseChar() in listVowel).not()}")
                (string[index - 1].lowercaseChar() in listVowel).not() && (string[index + 1].lowercaseChar() in listVowel).not()
            } else if (index == length - 1 && length > 1) {
                (string[index - 1].lowercaseChar() in listVowel).not()
            } else {
                false
            }
        } else {
            false
        }
    }

    private fun sortAndJoin(list: List<Int>): String {
        return list.sorted().joinToString(separator = ",")
    }

    fun create(): PersonalIndex {
        val arrowFull = getArrow()
        val lifePathIndex = getLifePathIndex()
        val missionIndex = getMissionIndex()
        val missingIndex = getMissingIndex()
        return PersonalIndex(
            lifePathIndex = getLifePathIndex(),
            birthIndex = getBirthIndex(),
            soulIndex = getSoulIndex(),
            rationalThinkingIndex = getRationalThinkingIndex(),
            balanceIndex = getBalanceIndex(),
            attitudeIndex = getAttitudeIndex(),
            passionIndex = getPassionIndex(),
            subconsciousPowerIndex = getSubconsciousPowerIndex(missingIndex = missingIndex.size),
            arrow = arrowFull[0],
            emptyArrow = arrowFull[1],
            missingIndex = missingIndex,
            personalityIndex = getPersonalityIndex(),
            missionIndex = getMissionIndex(),
            orientationIndex = getOrientationIndex(
                lifePathIndex = lifePathIndex,
                missionIndex = missionIndex
            ),
            actualizationIndex = getActualizationIndex(
                lifePathIndex = lifePathIndex,
                missionIndex = missionIndex
            ),
            personMonthIndex = getPersonalMonthIndex(),
            personYearIndex = getPersonalYearIndex(),
            stateLifeIndex = getStateLifeIndex(lifePathIndex),
            stateChallengeIndex = getStateChallengeIndex()
        )
    }

    companion object {
        private const val CONSECUTIVE = 5
        private const val AGE_DEFAULT = 36
        private const val AGE_STEP = 9
    }
}