package com.example.mylib

import java.util.*
import java.util.Date

class Vaccine (var id:String = UUID.randomUUID().toString().replace("-", "" ), var Name: String, var Date: Date ) {
}