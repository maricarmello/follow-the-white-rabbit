import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import kotlin.collections.ArrayList

class TheSecret {

    companion object {
        private const val ALPHABET_SIZE = 26
        private val anagramLetters: String = "poultry outwits ants".replace(" ", "")
        private val hashes = arrayOf(
            "e4820b45d2277f3844eac66c903e84be",
            "23170acc097c24edb98fc5488ab033fe",
            "665e5bcb0c20062fe8abaaf4628bb154"
        )
    }

    private val anagramCharCount = getCharCount(anagramLetters)

    private val listOfWords = File(this::class.java.getResource("/wordList")!!.toURI())

    fun find() {
        val filteredList = discardImpossibleWords(listOfWords)

        findEasyAndMediumAnagrams(filteredList)
    }

    private fun discardImpossibleWords(words: File): ArrayList<String> {
        val possibleWords: ArrayList<String> = ArrayList()
        words.forEachLine { word ->
            // Discarding words with single quotes right away
            if (word.indexOf("\'") < 0 && isPossibleWordByChar(getCharCount(word)))
                possibleWords += word
        }
        return possibleWords
    }

    private fun findEasyAndMediumAnagrams(words: ArrayList<String>) {
        val foundHashes: ArrayList<String> = ArrayList()
        words.forEach { firstWord ->
            words.forEach { secondWord ->
                words.forEach { thirdWord ->
                    if ((firstWord + secondWord + thirdWord).count() == anagramLetters.count()) {
                        val totalCount: IntArray = getCharCount(firstWord + secondWord + thirdWord)
                        if (totalCount.contentEquals(anagramCharCount)) {
                            val possibleCombination = "$firstWord $secondWord $thirdWord"
                            if (hashes.contains(possibleCombination.toMd5Hash()) && !foundHashes.contains(
                                    possibleCombination
                                )
                            ) {
                                foundHashes += possibleCombination
                                println("Secret phrase found: $possibleCombination")
                                if (foundHashes.size == 2) return
                            }
                        }
                    }
                }
            }
        }
    }

    private fun getCharCount(word: String): IntArray {
        val offset = 'a'.toInt()
        val charCounts = IntArray(ALPHABET_SIZE)
        for (char in word.indices) {
            val c = word[char].toInt()
            val codeChar: Int = c - offset
            if (codeChar in 0 until ALPHABET_SIZE) charCounts[codeChar]++
            else break
        }
        return charCounts
    }

    private fun isPossibleWordByChar(wordCharCount: IntArray): Boolean {
        for (int in 0 until ALPHABET_SIZE) {
            if (wordCharCount[int] > anagramCharCount[int])
                return false
        }
        return true
    }

    private fun String.toMd5Hash(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(this.toByteArray())).toString(16).padStart(32, '0')

    }
}