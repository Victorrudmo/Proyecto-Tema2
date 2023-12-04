package com.example.proyecto2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var btnCallPhone: ImageButton
    private lateinit var btnOpenUrl: ImageButton
    private lateinit var btnSetAlarm: ImageButton
    private lateinit var btnOpenCalculadora: ImageButton
    private lateinit var btnOpenDados: ImageButton
    private lateinit var btnOpenChistes: ImageButton

    companion object {
        const val PHONE = "684101238"
        const val URL = "https://www.elmundo.es/"
    }

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initEvent()

        val textViewBienvenida = findViewById<TextView>(R.id.textViewBienvenida)

        // Obtener el nombre de usuario desde el Intent
        val nombreUsuario = intent.getStringExtra("nombre")

        // Muestra el mensaje de bienvenida
        if (!nombreUsuario.isNullOrBlank()) {
            textViewBienvenida.text = "Bienvenido, $nombreUsuario!"
        }
    }

    private fun initEvent() {
        btnCallPhone = findViewById(R.id.btn_call)
        btnCallPhone.setOnClickListener {
            requestPermissions()
        }

        btnOpenUrl = findViewById(R.id.btn_internet)
        btnOpenUrl.setOnClickListener {
            openUrl()
        }

        btnSetAlarm = findViewById(R.id.btn_alarm)
        btnSetAlarm.setOnClickListener {
            setAlarm()
        }

        btnOpenCalculadora = findViewById(R.id.btn_calculadora)
        btnOpenCalculadora.setOnClickListener {
            openCalculadora()
        }

        btnOpenDados = findViewById(R.id.btn_dados)
        btnOpenDados.setOnClickListener {
            openDados()
        }

        btnOpenChistes = findViewById(R.id.btn_chistes)
        btnOpenChistes.setOnClickListener {
            openChistes()
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                openCallActivity()
            } else {
                requestPermissionLauncher.launch(Manifest.permission.CALL_PHONE)
            }
        } else {
            openCallActivity()
        }
    }

    private fun openCallActivity() {
        val intent = Intent(this, CallActivity::class.java).apply {
            putExtra("phone_number", PHONE)
        }
        startActivity(intent)
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            openCallActivity()
        } else {
            Toast.makeText(this, "Debes habilitar los permisos", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUrl() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(URL))
        startActivity(intent)
    }

    private fun setAlarm() {
        val now = Calendar.getInstance()
        now.add(Calendar.MINUTE, 2)
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val minute = now.get(Calendar.MINUTE)

        val intent = Intent(AlarmClock.ACTION_SET_ALARM)
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Mensaje de alarma")
        intent.putExtra(AlarmClock.EXTRA_HOUR, hour)
        intent.putExtra(AlarmClock.EXTRA_MINUTES, minute)
        startActivity(intent)
    }

    private fun openCalculadora() {
        val intent = Intent(this, CalculadoraActivity::class.java)
        startActivity(intent)
    }

    private fun openDados() {
        val intent = Intent(this, DadosActivity::class.java)
        startActivity(intent)
    }

    private fun openChistes() {
        val intent = Intent(this, ChistesActivity::class.java)
        startActivity(intent)
    }
}
