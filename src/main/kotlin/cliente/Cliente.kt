package cliente

import db.SerializableCache
import model.Calculo
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket

fun main() {
    val server = Socket("localhost", 1707)
    var user = ""
    var num1 = "null"
    var num2 = "null"
    var operation = ""
    println("Connecting to server...")

    println("Please, introduce the operation, and your username")
    while (user.isBlank() || num1.contentEquals("null") ||
        num2.contentEquals("null") ||
        (!operation.contentEquals("+") &&
                !operation.contentEquals("-") &&
                !operation.contentEquals("*") &&
                !operation.contentEquals("/"))
    ) {
        println("user")
        user = readln()
        println("1st number")
        num1 = readln().toDoubleOrNull().toString()
        println("operator [ + - * / ]")
        operation = readln()
        println("2nd number")
        num2 = readln().toDoubleOrNull().toString()
    }

    val calculo = Calculo(user, operation, num1.toDouble(), num2.toDouble())
    // aqui empieza el envio de mensajes
    val exitBuffer = ObjectOutputStream(server.getOutputStream())
    exitBuffer.writeObject(calculo)
    println("sending to server: $calculo")

    val entryBuffer = ObjectInputStream(server.getInputStream())
    val response = entryBuffer.readObject() as String
    println(response)
    val cache = entryBuffer.readObject() as SerializableCache
    println("""
          - ------- -
        Last operations:
          - ------- -
        """.trimIndent())
    cache.cache.forEach { println(it) }
    println("  - ------- -")
}