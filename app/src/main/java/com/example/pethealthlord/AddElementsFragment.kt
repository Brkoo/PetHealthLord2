package com.example.pethealthlord

import android.app.DatePickerDialog
import android.content.Context
import android.media.Image
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_elements.*
import org.apache.commons.io.FileUtils

import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.Pet

import com.example.mylib.PetSize
import kotlinx.android.synthetic.main.item_breed_layout.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.util.*
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddElementsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */


class AddElementsFragment : Fragment(R.layout.fragment_add_elements) {
    lateinit var app: MyApplication
    val gson = Gson()
    private lateinit var editText: EditText
    private lateinit var imageBtn: ImageView

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
       // app = application as MyApplication
        app = (activity?.application as MyApplication)
        super.onViewCreated(view, savedInstanceState)
        var smallPet = PetSize(1, "Small", "Small pet")
        var mediumPet = PetSize(2, "Medium", "Medium sized pet")
        var mediumLargePet = PetSize(3, "Medium to Large", "Medium-Large sized pet")
        var largePet = PetSize(4, "Large", "Large sized pet")










        val sizeList: ArrayList<PetSize> = ArrayList()



        exit.setOnClickListener(){
            view.findNavController().navigate(R.id.action_addElementsFragment_to_welcomeFragment)
        }


        //izbiranje datuma rojstva

        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel(myCalendar)

        }
        callendarImage.setOnClickListener{
            this.context?.let { it1 -> DatePickerDialog(it1, datePicker, myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show() }
            val age: Int = getAge(myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
        }

        rvSize.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)





            sizeList.add(smallPet)
            sizeList.add(mediumPet)
            sizeList.add(mediumLargePet)
            sizeList.add(largePet)




        rvSize.adapter = SizeRVAdapter(sizeList)

        editTextAge.text

        AddPetButton.setOnClickListener{
            app.data.AllPets.add(Pet(editTextTextPersonName.text.toString(),
                editTextAge.text.toString().toInt(),
                editTextWeight.text.toString().toInt()
                ))
            app.saveToFile()
            view.findNavController().navigate(R.id.action_addElementsFragment_to_welcomeFragment)
        }

    }



    private fun updateLabel(myCalendar: Calendar) {
        val MyFormat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(MyFormat, Locale.UK)
        editTextBirth.setText(sdf.format(myCalendar.time))

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getAge(year: Int, month: Int, dayOfMonth: Int): Int {
        return Period.between(
            LocalDate.of(year, month, dayOfMonth),
            LocalDate.now()
        ).years
    }

}

