package com.example.proyecto2

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class CalculadoraActivity : AppCompatActivity() {

    private lateinit var pantalla: TextView
    private var operacionActual: String = ""
    private var resultado: Double = 0.0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculadora)
        pantalla = findViewById(R.id.pantallaprincipal)

        val botonesNumericos = listOf(
            R.id.btn_0, R.id.btn_1, R.id.btn_2, R.id.btn_3,
            R.id.btn_4, R.id.btn_5, R.id.btn_6, R.id.btn_7,
            R.id.btn_8, R.id.btn_9, R.id.btn_decimal
        )

        val botonesOperaciones = listOf(
            R.id.btn_sumar, R.id.btn_restar, R.id.btn_multiplicar,
            R.id.btn_dividir, R.id.btn_porcentaje
        )

        val btnDel = findViewById<Button>(R.id.btn_del)
        val btnAC = findViewById<Button>(R.id.btn_ac)
        val btnSigno = findViewById<Button>(R.id.btn_signo)
        val btnIgual = findViewById<Button>(R.id.btn_igual)

        for (id in botonesNumericos) {
            findViewById<Button>(id).setOnClickListener { onNumeroClick(it) }
        }

        for (id in botonesOperaciones) {
            findViewById<Button>(id).setOnClickListener { onOperacionClick(it) }
        }

        btnDel.setOnClickListener { onDelClick() }
        btnAC.setOnClickListener { onACClick() }
        btnSigno.setOnClickListener { onSignoClick() }
        btnIgual.setOnClickListener { onIgualClick() }
    }

    private fun onNumeroClick(view: View) {
        val numero = (view as Button).text
        operacionActual += numero
        actualizarPantalla()
    }

    private fun onOperacionClick(view: View) {
        val operacion = (view as Button).text.toString()
        when (operacion) {
            "%" -> {
                if (operacionActual.isNotEmpty() && operacionActual.last().isDigit()) {
                    operacionActual += " $operacion "
                    actualizarPantalla()
                } else {
                    mostrarMensajeError("Porcentaje no válido")
                }
            }
            else -> {
                if (operacionActual.isNotEmpty() && operacionActual.last().isDigit()) {
                    operacionActual += " $operacion "
                    actualizarPantalla()
                } else {
                    mostrarMensajeError("Operación no válida")
                }
            }
        }
    }

    private fun onDelClick() {
        if (operacionActual.isNotEmpty()) {
            operacionActual = operacionActual.substring(0, operacionActual.length - 1)
            actualizarPantalla()
        }
    }

    private fun onACClick() {
        operacionActual = ""
        resultado = 0.0
        actualizarPantalla()
    }

    private fun onSignoClick() {
        if (operacionActual.isNotEmpty()) {
            val partes = operacionActual.split(" ").toMutableList()
            if (partes.isNotEmpty()) {
                val ultimo = partes.last()
                if (ultimo.toDoubleOrNull() != null) {
                    val nuevoUltimo = (-1 * ultimo.toDouble()).toString()
                    partes[partes.size - 1] = nuevoUltimo
                    operacionActual = partes.joinToString(" ")
                    actualizarPantalla()
                }
            }
        }
    }

    private var numeroPorcentaje: Double = 0.0

    private fun evaluarOperacion(): Double {
        val partes = operacionActual.split(" ").toMutableList()

        for (i in partes.indices) {
            if (partes[i] == "%") {
                if (i > 0 && i < partes.size - 1) {
                    numeroPorcentaje = partes[i - 1].toDouble()
                    return (numeroPorcentaje * partes[i + 1].toDouble() / 100.0)
                } else {
                    mostrarMensajeError("Operación de porcentaje incorrecta")
                }
            }
        }

        var resultado = partes[0].toDouble()
        var i = 1

        while (i < partes.size) {
            val operador = partes[i]
            val numero = partes[i + 1].toDouble()

            when (operador) {
                "+" -> resultado += numero
                "-" -> resultado -= numero
                "X" -> resultado *= numero
                "/" -> resultado /= numero
            }

            i += 2
        }

        return resultado
    }

    private fun onIgualClick() {
        try {
            resultado = evaluarOperacion()
            operacionActual = resultado.toString()
            actualizarPantalla()
        } catch (e: ArithmeticException) {
            mostrarMensajeError("Error en la operación")
        } catch (e: Exception) {
            mostrarMensajeError("Error de formato")
        }
    }

    private fun mostrarMensajeError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun actualizarPantalla() {
        pantalla.text = operacionActual
    }
}