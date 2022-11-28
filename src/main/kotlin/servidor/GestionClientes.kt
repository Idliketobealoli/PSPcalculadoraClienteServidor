package servidor

import db.Cache
import db.SerializableCache
import model.Calculo
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.net.Socket
import java.util.logging.Level
import java.util.logging.Logger

class GestionClientes() : Thread() {
    var numCliente: Int = 0
    lateinit var client: Socket

    constructor(
        socket: Socket,
        numCliente: Int
    ) : this() {
        this.client = socket
        this.numCliente = numCliente
    }

    override fun run() {
        // paso de mensajes con cliente
        var entryBuffer: ObjectInputStream? = null
        try {
            println("Attending client: $numCliente from ${client.inetAddress}")
            //client.setSoLinger(true, 100)
            entryBuffer = ObjectInputStream(client.getInputStream())
            val entry = entryBuffer.readObject() as Calculo
            //sleep(1000)
            val result = entry.resultado()
            Cache.updateCache(result)
            val cache = SerializableCache(Cache.getCache())
            println("Received from client $numCliente: $entry")
            val exitBuffer = ObjectOutputStream(client.getOutputStream())
            exitBuffer.writeObject(result)
            println("Sent to client $numCliente: $result")
            exitBuffer.writeObject(cache)
            println("Sent to client $numCliente: $cache")
        } catch (e: Exception) {
            Logger.getLogger(GestionClientes::class.java.name).log(Level.SEVERE, null, e)
        } finally {
            try {
                entryBuffer?.close()
            } catch (e: Exception) {
                Logger.getLogger(GestionClientes::class.java.name).log(Level.SEVERE, null, e)
            }
        }
    }
}