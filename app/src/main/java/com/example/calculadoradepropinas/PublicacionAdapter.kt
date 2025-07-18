package com.example.calculadoradepropinas

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

interface OnCantidadChangedListener {
    fun onCantidadIncreased(position: Int)
    fun onCantidadDecreased(position: Int)
}

class PublicacionAdapter(context: Context, resource: Int, objects: List<Publicacion>, private val listener: OnCantidadChangedListener) :
    ArrayAdapter<Publicacion>(context, resource, objects) {

    private val cantidades = SparseArray<Int>()
    private val totales = SparseArray<Double>()

    init {
        for (i in objects.indices) {
            cantidades.put(i, objects[i].cantidad)
            totales.put(i, objects[i].total)
        }
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_productos, parent, false)
        }

        val currentPublicacion = getItem(position)

        val imagen = itemView?.findViewById<ImageView>(R.id.imageViewPublicacion)
        val nombre = itemView?.findViewById<TextView>(R.id.textViewNombre)
        val descripcion = itemView?.findViewById<TextView>(R.id.textViewDescripcion)
        val precio = itemView?.findViewById<TextView>(R.id.textViewPrecioNumero)
        val cantidad = itemView?.findViewById<TextView>(R.id.textViewQuantity)
        val total = itemView?.findViewById<TextView>(R.id.textViewTotalNumero)


        imagen?.setImageResource(currentPublicacion?.imagen ?: R.drawable.kayli)
        nombre?.text = currentPublicacion?.nombre
        descripcion?.text = currentPublicacion?.descripcion
        precio?.text = currentPublicacion?.precio.toString()

        // Obtener la cantidad del SparseArray
        val currentCantidad = cantidades[position]
        cantidad?.text = currentCantidad.toString()

        val currentTotal = totales[position]
        total?.text = currentTotal.toString()

        val buttonIncrease = itemView?.findViewById<Button>(R.id.buttonIncrease)
        buttonIncrease?.setOnClickListener {
            //listener.onCantidadIncreased(position)
            //cantidad?.text = currentPublicacion?.cantidad.toString()  // Actualizamos la cantidad después de aumentar
            listener.onCantidadIncreased(position)

            cantidades.put(position, cantidades[position] + 1)
            cantidad?.text = cantidades[position].toString()

            totales.put(position, cantidades[position] * currentPublicacion?.precio!!)
            total?.text = totales[position].toString()

            mostrarTotalGeneral()
        }

        val buttonDecrease = itemView?.findViewById<Button>(R.id.buttonDecrease)
        buttonDecrease?.setOnClickListener {
            //listener.onCantidadDecreased(position)
            //cantidad?.text = currentPublicacion?.cantidad.toString()  // Actualizamos la cantidad después de disminuir
            listener.onCantidadDecreased(position)
            if (cantidades[position] > 0) {

                cantidades.put(position, cantidades[position] - 1)
                cantidad?.text = cantidades[position].toString()

                totales.put(position, cantidades[position] * currentPublicacion?.precio!!)
                total?.text = totales[position].toString()

                mostrarTotalGeneral()
            }
        }


        return itemView!!
    }

    private fun mostrarTotalGeneral() {
        var totalGeneral = 0.0
        for (i in 0 until totales.size()) {
            totalGeneral += totales.valueAt(i)
        }
        //Toast.makeText(context, "Total general: $totalGeneral", Toast.LENGTH_SHORT).show()
        ProductoManager.actualizarTotalGeneral(totalGeneral)  // Almacenar el total general en ProductoManager
        (context as MainActivity).actualizarTotalGeneralTextView()
    }

}
