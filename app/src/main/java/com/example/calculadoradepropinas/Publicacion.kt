package com.example.calculadoradepropinas

data class Publicacion(val imagen: Int, val nombre: String, val descripcion: String, val precio: Double, var cantidad: Int = 0, var total:Double)
