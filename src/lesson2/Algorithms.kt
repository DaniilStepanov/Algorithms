@file:Suppress("UNUSED_PARAMETER")

package lesson2

import java.io.BufferedReader
import java.io.File
import java.io.FileReader

/**
 * Получение наибольшей прибыли (она же -- поиск максимального подмассива)
 * Простая
 *
 * Во входном файле с именем inputName перечислены цены на акции компании в различные (возрастающие) моменты времени
 * (каждая цена идёт с новой строки). Цена -- это целое положительное число. Пример:
 *
 * 201
 * 196
 * 190
 * 198
 * 187
 * 194
 * 193
 * 185
 *
 * Выбрать два момента времени, первый из них для покупки акций, а второй для продажи, с тем, чтобы разница
 * между ценой продажи и ценой покупки была максимально большой. Второй момент должен быть раньше первого.
 * Вернуть пару из двух моментов.
 * Каждый момент обозначается целым числом -- номер строки во входном файле, нумерация с единицы.
 * Например, для приведённого выше файла результат должен быть Pair(3, 4)
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun optimizeBuyAndSell(inputName: String): Pair<Int, Int> {
    val input: List<Int>
    try {
        input = BufferedReader(FileReader(File(inputName))).readLines().map { it.toInt() }
    }
    catch (e: Exception) {
        throw e
    }
    if (input.size < 2)
        throw Exception()
    var res = Pair(1, 2)
    var maxDif = input[1] - input[0]
    for (i in 0 until input.size) {
        for (j in i until input.size) {
            if (input[j] - input[i] > maxDif) {
                res = Pair(i + 1, j + 1)
                maxDif = input[j] - input[i]
            }
        }
    }
    return res
}

/**
 * Задача Иосифа Флафия.
 * Простая
 *
 * Образовав круг, стоят menNumber человек, пронумерованных от 1 до menNumber.
 *
 * 1 2 3
 * 8   4
 * 7 6 5
 *
 * Мы считаем от 1 до choiceInterval (например, до 5), начиная с 1-го человека по кругу.
 * Человек, на котором остановился счёт, выбывает.
 *
 * 1 2 3
 * 8   4
 * 7 6 х
 *
 * Далее счёт продолжается со следующего человека, также от 1 до choiceInterval.
 * Выбывшие при счёте пропускаются, и человек, на котором остановился счёт, выбывает.
 *
 * 1 х 3
 * 8   4
 * 7 6 Х
 *
 * Процедура повторяется, пока не останется один человек. Требуется вернуть его номер (в данном случае 3).
 *
 * 1 Х 3
 * х   4
 * 7 6 Х
 *
 * 1 Х 3
 * Х   4
 * х 6 Х
 *
 * х Х 3
 * Х   4
 * Х 6 Х
 *
 * Х Х 3
 * Х   х
 * Х 6 Х
 *
 * Х Х 3
 * Х   Х
 * Х х Х
 */
fun josephTask(menNumber: Int, choiceInterval: Int): Int {
    var res = 0
    for (i in 1..menNumber) {
        res = (res + choiceInterval) % i
    }
    return res + 1
}

/**
 * Наибольшая общая подстрока.
 * Средняя
 *
 * Дано две строки, например ОБСЕРВАТОРИЯ и КОНСЕРВАТОРЫ.
 * Найти их самую длинную общую подстроку -- в примере это СЕРВАТОР.
 * Если общих подстрок нет, вернуть пустую строку.
 * При сравнении подстрок, регистр символов *имеет* значение.
 * Если имеется несколько самых длинных общих подстрок одной длины,
 * вернуть ту из них, которая встречается раньше в строке first.
 */
fun longestCommonSubstring(second: String, first: String): String {
    val a = ArrayList<ArrayList<Int>>()
    val list = generateSequence(0) { it }.take(second.length + 1).toList()
    a.add(ArrayList(list))
    for (i in 0..first.length)
        a.add(ArrayList(list))
    for (i in 0 until first.length) {
        for (j in 0 until second.length) {
            if (first[i] == second[j]) {
                a[i + 1][j + 1] = a[i][j] + 1
            }
        }
    }
    val maxEl = a.maxBy { it.max()!! }!!.max()!!
    var ans = ""
    if (maxEl == 0) return ans
    for (i in 0 until first.length) {
        for (j in 0 until second.length) {
            if (a[i][j] == maxEl) {
                ans = second.substring(j - maxEl, j)
            }
        }
    }
    return ans
}

/**
 * Число простых чисел в интервале
 * Простая
 *
 * Рассчитать количество простых чисел в интервале от 1 до limit (включительно).
 * Если limit <= 1, вернуть результат 0.
 *
 * Справка: простым считается число, которое делится нацело только на 1 и на себя.
 * Единица простым числом не считается.
 */
fun calcPrimesNumber(limit: Int): Int {
    if (limit <= 1) return 0
    val list = generateSequence(true) { it }.take(limit + 1).toMutableList()
    list[0] = false
    list[1] = false
    for (i in 2 until list.size) {
        if (list[i]) {
            var j = 2
            while (i * j < list.size) {
                list[i * j] = false
                ++j
            }
        }
    }
    return list.count { it }
}

/**
 * Балда
 * Сложная
 *
 * В файле с именем inputName задана матрица из букв в следующем формате
 * (отдельные буквы в ряду разделены пробелами):
 *
 * И Т Ы Н
 * К Р А Н
 * А К В А
 *
 * В аргументе words содержится множество слов для поиска, например,
 * ТРАВА, КРАН, АКВА, НАРТЫ, РАК.
 *
 * Попытаться найти каждое из слов в матрице букв, используя правила игры БАЛДА,
 * и вернуть множество найденных слов. В данном случае:
 * ТРАВА, КРАН, АКВА, НАРТЫ
 *
 * И т Ы Н     И т ы Н
 * К р а Н     К р а н
 * А К в а     А К В А
 *
 * Все слова и буквы -- русские или английские, прописные.
 * В файле буквы разделены пробелами, строки -- переносами строк.
 * Остальные символы ни в файле, ни в словах не допускаются.
 */
data class Letter(val sym: Char, var coorX: Int = 0, var coorY: Int = 0,
                  val nextLetters: MutableList<Letter> = mutableListOf()) {
    override fun toString(): String {
        return "$sym ${nextLetters.size} ($coorX, $coorY)"
    }

    override fun equals(other: Any?): Boolean {
        other?.let {
            val otherLetter = other as Letter
            return this.coorX == otherLetter.coorX && this.coorY == otherLetter.coorY
        }
        return false

    }
}

fun baldaSearcher(inputName: String, words: Set<String>): Set<String> {
    val input = BufferedReader(FileReader(File(inputName)))
            .readLines()
            .map { it.split(' ') }
            .map { it.map { Letter(it.first()) } }
    val directions = listOf(Pair(-1, 0), Pair(0, -1), Pair(1, 0), Pair(0, 1))
    for (i in 0 until input.size) {
        val curInp = input[i]
        for (j in 0 until curInp.size) {
            val letter = input[i][j]
            letter.coorX = i
            letter.coorY = j
            for (d in directions) {
                val newX = i + d.first
                val newY = j + d.second
                if (newX >= 0 && newX < input.size) {
                    if (newY >= 0 && newY < input[0].size) {
                        letter.nextLetters.add(input[newX][newY])
                    }
                }
            }
        }
    }
    val splitInput = input.flatten()
    val wordsToChar = words.map { it.toList() }
    val res = mutableSetOf<String>()
    for (i in 0 until wordsToChar.size) {
        val word = wordsToChar[i]
        var curLetters = splitInput.filter { it.sym == word.first() }
        if (curLetters.isEmpty()) continue
        val used = mutableListOf<Letter>()
        curLetters.forEach { used.add(it) }
        for (j in 1 until word.size) {
            val filtered = curLetters.filter { it.nextLetters.map { it.sym }.contains(word[j]) }
            val deletedLetters = curLetters.filterNot { it.nextLetters.map { it.sym }.contains(word[j]) }
            deletedLetters.forEach { used.remove(it) }
            curLetters = filtered.map { it.nextLetters }.flatten().filter { it.sym == word[j] }
            curLetters = curLetters.filterNot { used.contains(it) }
            curLetters.forEach { used.add(it) }
            if (curLetters.isEmpty())
                break
            if (j == word.size - 1)
                res.add(words.elementAt(i))
        }
    }
    return res
}