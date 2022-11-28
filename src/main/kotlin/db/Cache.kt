package db

import java.lang.Thread.sleep
import java.util.concurrent.locks.ReentrantLock

object Cache {
    private val MAX_CAPACITY = 5
    private val cache = mutableListOf<String>()
    private val lock = ReentrantLock()

    fun getCache(): List<String> {
        return cache.toList()
    }

    fun updateCache(newElement: String) {
        while (!lock.tryLock()) {
            sleep((100L..500L).random())
        }
        if (lock.isHeldByCurrentThread) {
            if (cache.size < MAX_CAPACITY) {
                cache.add(newElement)
            }
            else {
                cache.removeFirstOrNull()
                cache.add(newElement)
            }
            lock.unlock()
        }
    }
}

class SerializableCache(): java.io.Serializable {
    lateinit var cache: List<String>

    constructor(
        cache: List<String>
    ) : this() {
        this.cache = cache
    }
}