package com.example.mylib

import java.util.*

class Pet(var Name: String, var Age: Int, var Weight: Int,/* val picture: Int ,*/ var id:String = UUID.randomUUID().toString().replace("-", "" )) {

    val VaccineList: MutableList<Vaccine> = mutableListOf();
}