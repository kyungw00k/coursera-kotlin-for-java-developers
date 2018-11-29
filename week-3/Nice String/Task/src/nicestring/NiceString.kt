package nicestring

fun String.isNice(): Boolean {
    val isContainSubstrings = contains("bu") || contains("ba") || contains("be")
    val isContainsAtLeastThreeVowels = count { "aeiou".contains(it) } >= 3
    val isContainsDoubleLetter = contains("(.)\\1".toRegex())

//    println(!isContainSubstrings)
//    println(isContainsAtLeastThreeVowels)
//    println(isContainsDoubleLetter)

    return arrayOf(
            !isContainSubstrings,
            isContainsAtLeastThreeVowels,
            isContainsDoubleLetter).count { it == true } > 1
}