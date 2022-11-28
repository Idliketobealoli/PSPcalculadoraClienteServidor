package servidor

import java.net.ServerSocket
import java.net.Socket

fun main() {
    var numClientes = 0
    lateinit var server: ServerSocket
    lateinit var client: Socket
    val port = 1707
    val exit = false

    println("Starting server...")
    try {
        server = ServerSocket(port)
        while (!exit) {
            println("Waiting for connections...")
            client = server.accept()
            numClientes++
            println("Petition -> ${client.inetAddress} --- ${client.port}")
            // gestion de clientes
            val gestor = GestionClientes(client, numClientes)
            gestor.start()
        }
        println("Closing server...")
        server.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}