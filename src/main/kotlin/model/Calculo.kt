package model

class Calculo(): java.io.Serializable {
    lateinit var user: String
    lateinit var operador: String
    var numero1: Double = 0.0
    var numero2: Double = 0.0

    constructor(
        user:String,
        operador:String,
        num1:Double,
        num2:Double
    ) : this() {
        this.user = user
        this.operador = operador
        this.numero1 = num1
        this.numero2 = num2
    }

    fun resultado(): String {
        return when (operador) {
            "+" -> { "$user: $numero1 + $numero2 = ${numero1+numero2}" }
            "-" -> { "$user: $numero1 - $numero2 = ${numero1-numero2}" }
            "*" -> { "$user: $numero1 * $numero2 = ${numero1*numero2}" }
            "/" -> {
                if (numero2 == 0.0) "$user: $numero1 / $numero2 = No se puede dividir por 0"
                else "$user: $numero1 / $numero2 = ${numero1/numero2}"
            }
            else -> ""
        }
    }
}