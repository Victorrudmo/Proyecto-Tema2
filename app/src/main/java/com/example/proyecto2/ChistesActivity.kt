package com.example.proyecto2

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto2.databinding.ActivityChistesBinding
import java.util.Locale

object Chistes {
    val chistes = listOf(
        "¿Cuál es el último animal que subió al arca de Noé? El del-fin",
        "¿Cómo se dice pañuelo en japonés? Saka-moko",
        "¿Qué le dice un gusano a otro gusano? Voy a dar una vuelta a la manzana",
        "¿Cuál es el colmo de Aladdín? Tener mal genio",
        "Si se muere una pulga, ¿a dónde va? Al pulgatorio",
        "Sí los zombies se deshacen con el paso del tiempo ¿zombiodegradables?",
        "¿Qué hace un perro con un taladro? Ta-ladrando",
        "¿Qué hace una abeja en el gimnasio? Zumba",
        "¿Qué le dice un jardinero a otro? Nos vemos cuando podamos",
        "¿Cómo se despiden los químicos? Ácido un placer"
    )

    fun obtenerChisteAleatorio(): String {
        return chistes.shuffled().first()
    }
}

class ChistesActivity : AppCompatActivity() {
    private lateinit var bindingChistes: ActivityChistesBinding
    private lateinit var textToSpeech: TextToSpeech
    val MYTAG = "LOGCAT"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingChistes = ActivityChistesBinding.inflate(layoutInflater)
        setContentView(bindingChistes.root)
        configureTextToSpeech()
        initEvent()
    }

    private fun configureTextToSpeech() {
        textToSpeech = TextToSpeech(applicationContext, TextToSpeech.OnInitListener {
            if (it != TextToSpeech.ERROR) {
                // Establecer el idioma a español
                textToSpeech.language = Locale("es", "ES")  // Código de idioma y país para español
                Log.i(MYTAG, "Sin problemas en la configuración TextToSpeech")
            } else {
                Log.i(MYTAG, "Error en la configuración TextToSpeech")
            }
        })
    }

    private fun initEvent() {
        val btnExample = findViewById<Button>(R.id.btnExample)
        btnExample.setOnClickListener {
            executorSingleTouch(Chistes.obtenerChisteAleatorio())
            Log.i(MYTAG, "Escuchamos un chiste aleatorio")
        }
    }

    private fun speakMeDescription(s: String) {
        Log.i(MYTAG, "Intenta hablar")
        textToSpeech.speak(s, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    private fun executorSingleTouch(chiste: String) {
        speakMeDescription(chiste)
    }

    override fun onDestroy() {
        if (::textToSpeech.isInitialized) {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
        super.onDestroy()
    }
}

