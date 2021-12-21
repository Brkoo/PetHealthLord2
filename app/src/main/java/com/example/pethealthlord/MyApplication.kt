package com.example.pethealthlord

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import android.view.View
import android.widget.TextView
import com.google.gson.GsonBuilder
import org.apache.commons.io.FileUtils
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.*
import android.app.Application
import com.example.mylib.AllPets
import com.example.mylib.Pet
import kotlin.collections.ArrayList

const val MY_SP_FILE_NAME = "myshared.data"
const val MY_FILE_NAME = "mydata.json"


class MyApplication : Application() {

    lateinit var data: AllPets
    private lateinit var gson: Gson
    lateinit var file: File
    private lateinit var uuid: UUID
    lateinit var sharedPref: SharedPreferences


    override fun onCreate() {
        super.onCreate()


        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
            gson = Gson()
            file = File(filesDir, MY_FILE_NAME)
            Timber.i(file.path.toString())
            if (file.exists()) {
                data = gson.fromJson(FileUtils.readFileToString(file), AllPets::class.java)
            } else
                data = AllPets("Allpets")
            saveToFile()

            initShared()
            if (!containsID()) {
                saveID(UUID.randomUUID().toString().replace("-", ""))
            }
            Timber.d("ID of app is ${getID()}")

        }
    }

    fun saveToFile() {
        try {
            //for FileUtils import org.apache.commons.io.FileUtils
            //in gradle implementation 'org.apache.commons:commons-io:1.3.2'
            FileUtils.writeStringToFile(file, gson.toJson(data))
            Timber.d("Save to file.")
        } catch (e: IOException) {
            Timber.d("Can't save " + file.path)
        }

    }

    fun initShared() {
        sharedPref = getSharedPreferences(MY_SP_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun saveID(id: String) {
        with(sharedPref.edit()) {
            putString("ID", id)
            apply()
        }
    }

    fun savePosition(position: Int) {
        with(sharedPref.edit()) {
            putInt("Position", position)
            apply()
        }
    }

    fun saveNotf(notf: Boolean) {
        with(sharedPref.edit()) {
            putBoolean("NOTF", notf)
            apply()
        }
    }

    fun containsID(): Boolean {
        return sharedPref.contains("ID")
    }

    fun getID(): String? {
        return sharedPref.getString("ID", "DefaultNoData").toString()
    }

    fun getPosition(): Int {
        return sharedPref.getInt("Position", 0)
    }
}