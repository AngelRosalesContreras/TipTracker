package com.example.calculadoradepropinas

import android.os.Bundle
import android.content.Context
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var buttonCalculate: Button
    private lateinit var buttonReset: Button
    private lateinit var editTextPropinas: EditText
    private lateinit var editTextPersonas: EditText
    private lateinit var textViewPropina: TextView
    private lateinit var textViewTotal: TextView
    private lateinit var textViewPersonas: TextView

    private var propinasAnterior: Double = 0.0
    private var personasAnterior: Int = 0
    private var totalGeneralAnterior: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        buttonCalculate = findViewById(R.id.botonCalcular)
        buttonReset = findViewById(R.id.botonReset)

        //editTextPropinas = findViewById(R.id.editTextPropina)
        //editTextPersonas = findViewById(R.id.editTextPersonas)

        //textViewPropina = findViewById(R.id.textViewPagarPropina)
        //textViewTotal = findViewById(R.id.textViewTotalPrecio)
        //textViewPersonas = findViewById(R.id.textViewTotalPrecioPersona)


        val listViewOtherPosts: ListView = findViewById(R.id.listViewOtherPosts)
        ProductoManager.configurarLista(this, listViewOtherPosts)

        //val mensaje = "T: $totalGeneralAnterior\n" + "P: $propinasAnterior\n" + "P: $personasAnterior"
        //Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()


        buttonCalculate.setOnClickListener {
            calcularPropina()
            //val mensaje = "T: $totalGeneralAnterior\n" + "P: $propinasAnterior\n" + "P: $personasAnterior"
            // Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()

        }

        buttonReset.setOnClickListener {
            resetValues()

        }

    }

    fun actualizarTotalGeneralTextView() {
        val textViewTotalGeneral = findViewById<TextView>(R.id.textViewNombrePrecio)
        val totalGeneral = ProductoManager.obtenerTotalGeneral()
        textViewTotalGeneral.text = totalGeneral.toString()
        //Toast.makeText(this, "Total general: $totalGeneral", Toast.LENGTH_SHORT).show()
    }

    private fun calcularPropina() {
        val editTextPropinas = findViewById<EditText>(R.id.editTextPropina)
        val editTextPersonas = findViewById<EditText>(R.id.editTextPersonas)
        val textViewPropina = findViewById<TextView>(R.id.textViewPagarPropina)
        val textViewTotal = findViewById<TextView>(R.id.textViewTotalPrecio)
        val textViewPersonas = findViewById<TextView>(R.id.textViewTotalPrecioPersona)

        val propinasText = editTextPropinas.text.toString()
        val personasText = editTextPersonas.text.toString()

        if (propinasText.isEmpty()) {
            editTextPropinas.error = "Campo requerido"
            return
        }

        if (personasText.isEmpty()) {
            editTextPersonas.error = "Campo requerido"
            return
        }

        val propinas = propinasText.toDouble()
        val personas = personasText.toInt()

        val totalGeneral = ProductoManager.obtenerTotalGeneral()

        // Verificar si los valores son los mismos
        if (propinas == propinasAnterior && personas == personasAnterior && totalGeneral == totalGeneralAnterior) {
            // Si son los mismos, no hacer nada
            return
        }

        // Actualizar valores anteriores
        propinasAnterior = propinas
        personasAnterior = personas
        totalGeneralAnterior = totalGeneral


        // Calcular la propina como un porcentaje del total general
        val propinaTotal = (propinas / 100) * totalGeneral

        // Formatear propinaTotal a dos decimales
        val propinaTotalFormateada = String.format("%.2f", propinaTotal).toDouble()

        // Sumar la propina al total general
        val nuevoTotalGeneral = totalGeneral + propinaTotal

        // Formatear nuevoTotalGeneral a dos decimales
        val nuevoTotalGeneralFormateado = String.format("%.2f", nuevoTotalGeneral).toDouble()

        //ProductoManager.actualizarTotalGeneral(nuevoTotalGeneralFormateado) // Actualizar el total general en ProductoManager

        // Mostrar el resultado de la propina en textViewPropina
        textViewPropina.text = String.format("%.2f", propinaTotalFormateada)

        // Mostrar el nuevo total general en textViewTotal
        textViewTotal.text = String.format("%.2f", nuevoTotalGeneralFormateado)

        // Calcular y mostrar el total por persona
        val totalPorPersona = nuevoTotalGeneral / personas

        // Formatear totalPorPersona a dos decimales
        val totalPorPersonaFormateado = String.format("%.2f", totalPorPersona)

        textViewPersonas.text = totalPorPersonaFormateado
    }


    private fun resetValues() {
        val editTextPropinas = findViewById<EditText>(R.id.editTextPropina)
        val editTextPersonas = findViewById<EditText>(R.id.editTextPersonas)
        val textViewPropina = findViewById<TextView>(R.id.textViewPagarPropina)
        val textViewTotal = findViewById<TextView>(R.id.textViewTotalPrecio)
        val textViewPersonas = findViewById<TextView>(R.id.textViewTotalPrecioPersona)

        // Limpiar EditTexts
        editTextPropinas.text.clear()
        editTextPersonas.text.clear()

        // Establecer valores a 0.0 en TextViews
        textViewPropina.text = "0.0"
        textViewTotal.text = "0.0"
        textViewPersonas.text = "0.0"

        propinasAnterior = 0.0
        personasAnterior = 0
        totalGeneralAnterior = 0.0

    }

}