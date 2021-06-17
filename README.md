## Follow  The White Rabbit

<div style='float: center'>
  <img src="https://upload.wikimedia.org/wikipedia/commons/a/a9/Literature_personage_stub.png" alt="White-rabbit" height="250" width="250" align="middle">
</div>

## About this project

This project is the solution for the [Follow the white rabbit][] coding challenge done in Kotlin.

[Follow the white rabbit]:https://followthewhiterabbit.trustpilot.com/

## Performance

* Solves the easy secret phrase in about 1.5 minutes.
    * `printout stout yawls`

* Solves the medium secret phrase in about 4 minutes.
    * `ty outlaws printouts`

## Implementation Details

The first step is to filter the given list of words, discarding all the impossible words, in this case:

* Words which contain characters unused in the given phrase.
* Words which contain characters that occur more than the number of times they've occurred in the given phrase.

The second step is to iterate over the filtered list of words creating combinations of 3 words and:
* Checking if the size of the combination is equal to the given phrase.
* If so, checking if the content of this combination is an anagram of the given phrase.

The last step is to hash this possible combination of words and compare it to the given hashes.

## How to start
Run with gradle

* `./gradlew run`
