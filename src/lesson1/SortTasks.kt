@file:Suppress("UNUSED_PARAMETER")

package lesson1

import java.io.*
import java.util.*

/**
 * Сортировка времён
 *
 * Простая
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле с именем inputName содержатся моменты времени в формате ЧЧ:ММ:СС,
 * каждый на отдельной строке. Пример:
 *
 * 13:15:19
 * 07:26:57
 * 10:00:03
 * 19:56:14
 * 13:15:19
 * 00:40:31
 *
 * Отсортировать моменты времени по возрастанию и вывести их в выходной файл с именем outputName,
 * сохраняя формат ЧЧ:ММ:СС. Одинаковые моменты времени выводить друг за другом. Пример:
 *
 * 00:40:31
 * 07:26:57
 * 10:00:03
 * 13:15:19
 * 13:15:19
 * 19:56:14
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortTimes(inputName: String, outputName: String) {
    val inp = BufferedReader(FileReader(File(inputName))).readLines()
    val split = inp.map { it.split(":") }
    split.forEach {
        if (it.size != 3 || Integer.parseInt(it.component1()) !in 0..23
                || Integer.parseInt(it.component2()) !in 0..59
                || Integer.parseInt(it.component3()) !in 0..59) throw NumberFormatException()
    }
    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(outputName)))
    writer.write(inp.sorted().joinToString(separator = "\n"))
    writer.close()
}

/**
 * Сортировка адресов
 *
 * Средняя
 *
 * Во входном файле с именем inputName содержатся фамилии и имена жителей города с указанием улицы и номера дома,
 * где они прописаны. Пример:
 *
 * Петров Иван - Железнодорожная 3
 * Сидоров Петр - Садовая 5
 * Иванов Алексей - Железнодорожная 7
 * Сидорова Мария - Садовая 5
 * Иванов Михаил - Железнодорожная 7
 *
 * Людей в городе может быть до миллиона.
 *
 * Вывести записи в выходной файл outputName,
 * упорядоченными по названию улицы (по алфавиту) и номеру дома (по возрастанию).
 * Людей, живущих в одном доме, выводить через запятую по алфавиту (вначале по фамилии, потом по имени). Пример:
 *
 * Железнодорожная 3 - Петров Иван
 * Железнодорожная 7 - Иванов Алексей, Иванов Михаил
 * Садовая 5 - Сидоров Петр, Сидорова Мария
 *
 * В случае обнаружения неверного формата файла бросить любое исключение.
 */
fun sortAddresses(inputName: String, outputName: String) {
    val input = BufferedReader(FileReader(File(inputName))).readLines()
    input.forEach {
        if (!Regex(""".+ - .+""").matches(it)) throw Exception()
    }
    val lines = input.map { it.split(" - ") }
    val res = TreeMap<String, MutableList<String>>()
    for (l in lines) {
        if (res.containsKey(l.last())) res[l.last()]!!.add(l.first())
        else res[l.last()] = mutableListOf(l.first())
    }
    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(outputName)))
    for (en in res) {
        writer.write("${en.key} - ${en.value.joinToString(separator = ", ")}\n")
    }
    writer.close()
}

/**
 * Сортировка температур
 *
 * Средняя
 * (Модифицированная задача с сайта acmp.ru)
 *
 * Во входном файле заданы температуры различных участков абстрактной планеты с точностью до десятых градуса.
 * Температуры могут изменяться в диапазоне от -273.0 до +500.0.
 * Например:
 *
 * 24.7
 * -12.6
 * 121.3
 * -98.4
 * 99.5
 * -12.6
 * 11.0
 *
 * Количество строк в файле может достигать ста миллионов.
 * Вывести строки в выходной файл, отсортировав их по возрастанию температуры.
 * Повторяющиеся строки сохранить. Например:
 *
 * -98.4
 * -12.6
 * -12.6
 * 11.0
 * 24.7
 * 99.5
 * 121.3
 */
fun sortTemperatures(inputName: String, outputName: String) {
    val input = BufferedReader(FileReader(File(inputName))).readLines()
    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(outputName)))
    for (el in input.map { it.toDouble() }.sorted()) {
        writer.write("$el\n")
    }
    writer.close()
}

/**
 * Сортировка последовательности
 *
 * Средняя
 * (Задача взята с сайта acmp.ru)
 *
 * В файле задана последовательность из n целых положительных чисел, каждое в своей строке, например:
 *
 * 1
 * 2
 * 3
 * 2
 * 3
 * 1
 * 2
 *
 * Необходимо найти число, которое встречается в этой последовательности наибольшее количество раз,
 * а если таких чисел несколько, то найти минимальное из них,
 * и после этого переместить все такие числа в конец заданной последовательности.
 * Порядок расположения остальных чисел должен остаться без изменения.
 *
 * 1
 * 3
 * 3
 * 1
 * 2
 * 2
 * 2
 */
fun sortSequence(inputName: String, outputName: String) {
    val input = BufferedReader(FileReader(File(inputName))).readLines()
    val numberList = input.map { Integer.parseInt(it) }.toMutableList()
    val countable = numberList.groupingBy { it }.eachCount()
    val max = countable.maxBy { it.value }
    val maxCountable =
            if (countable.filter { it.value == max!!.value }.size != 1)
                countable.minBy { it.key }!!.key
            else max!!.key
    numberList.removeIf { it == maxCountable }
    for (i in 0 until max!!.value)
        numberList.add(maxCountable)
    val writer = BufferedWriter(OutputStreamWriter(FileOutputStream(outputName)))
    numberList.forEach { writer.write("$it\n") }
    writer.close()
}

/**
 * Соединить два отсортированных массива в один
 *
 * Простая
 *
 * Задан отсортированный массив first и второй массив second,
 * первые first.size ячеек которого содержат null, а остальные ячейки также отсортированы.
 * Соединить оба массива в массиве second так, чтобы он оказался отсортирован. Пример:
 *
 * first = [4 9 15 20 28]
 * second = [null null null null null 1 3 9 13 18 23]
 *
 * Результат: second = [1 3 4 9 9 13 15 20 23 28]
 */
fun <T : Comparable<T>> mergeArrays(first: Array<T>, second: Array<T?>) {
    val res = first + second.slice(first.size until second.size).map { it as T }
    res.sort()
    second.forEachIndexed { index, t -> second[index] = res[index] }
}

