## 1. 컬렉션
```kotlin
fun main() {
    val peopleAges = mutableMapOf<String, Int>(
        "Fred" to 30,
        "Ann" to 23
    )
    peopleAges.put("Barbara", 42)
    peopleAges["Joe"] = 51
    peopleAges["Fred"] = 31
    println(peopleAges)
    println(peopleAges.map { "${it.key} is ${it.value}" }.joinToString(", ") )
    val filteredNames = peopleAges.filter { it.key.length < 4 }
	println(filteredNames)

}
```
>Result : 
```
{Fred=31, Ann=23, Barbara=42, Joe=51}
Fred is 31, Ann is 23, Barbara is 42, Joe is 51
{Ann=23, Joe=51}
```
---- 
   
## 2. 람다함수   
```kotlin
fun main() {
    val triple : (Int) -> Int = {it*3}
    println(triple(3))
    
    val peopleNames = listOf("Fred", "Ann", "Barbara", "Joe")
    println(peopleNames.sorted())
    println(peopleNames.sortedWith { str1: String, str2: String -> str1.length - str2.length })
}
```

>Result : 
```
9
[Ann, Barbara, Fred, Joe]
[Ann, Joe, Fred, Barbara]
```
----

## 3. 고차함수
```kotlin
fun main() {
    val words = listOf("about", "acute", "awesome", "balloon", "best", "brief", "class", "coffee", "creative")
    val filteredWords = words.filter { it.startsWith("b", ignoreCase = true) }
        .shuffled()
        .take(2)
        .sorted()
    val filteredWordsC = words.filter{it.startsWith("c", ignoreCase = true)}
    .shuffled()
    .take(2)
    .sorted()
    println(filteredWords)
    println(filteredWordsC)
}
```

>Result :
```
[balloon, brief]
[class, creative]
```
