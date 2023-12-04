package com.example.proyecto2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto2.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Adaptador para la lista de años de nacimiento
        val years = (1950..2023).toList().map { it.toString() }
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerAnnoNacimiento.adapter = adapter

        // Listener para la selección del año de nacimiento
        binding.spinnerAnnoNacimiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // No se selecciona nada
            }
        }

        binding.btnValidar.setOnClickListener {
            // Verificar si los campos están completos
            if (!camposCompletos()) {
                // Mostrar un Toast indicando que se deben rellenar todos los campos
                Toast.makeText(this, "Debes rellenar todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                // Todos los campos están completos, se realiza la validación
                val nombre = binding.editTextNombre.text.toString()

                // Mostrar la pantalla de progreso
                showProgressBar()

                // Simula una espera de 2 segundos antes de iniciar MainActivity
                Handler().postDelayed({
                    // Crear el Intent y agregar el nombre como extra
                    val intent = Intent(this, MainActivity::class.java).apply {
                        putExtra("nombre", nombre)
                    }

                    // Iniciar la actividad MainActivity
                    startActivity(intent)
                    finish()
                }, 2000)
            }
        }
    }

    private fun showProgressBar() {
        val progressIntent = Intent(this, ProgressActivity::class.java)
        startActivity(progressIntent)
    }

    private fun camposCompletos(): Boolean {
        // Verificar si los campos están completos
        val nombre = binding.editTextNombre.text.toString()
        val apellidos = binding.editTextApellidos.text.toString()
        val selectedSexoId = binding.radioGroupSexo.checkedRadioButtonId
        val selectedSituacionLaboralId = binding.radioGroupSituacionLaboral.checkedRadioButtonId

        return nombre.isNotEmpty() && apellidos.isNotEmpty() && selectedSexoId != -1 && selectedSituacionLaboralId != -1
    }
}
