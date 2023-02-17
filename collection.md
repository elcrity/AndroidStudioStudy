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
    {Fred=31, Ann=23, Barbara=42, Joe=51}
    Fred is 31, Ann is 23, Barbara is 42, Joe is 51
    {Ann=23, Joe=51}
