package com.example.pethealthlord

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.commit
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mylib.AllPets
import com.example.mylib.Pet
import com.example.mylib.PetSize
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_add_elements.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import org.apache.commons.io.FileUtils
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
 * Use the [WelcomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    lateinit var app: MyApplication
    val gson = Gson()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = (activity?.application as MyApplication)
        val data = gson.fromJson(FileUtils.readFileToString(app.file), AllPets::class.java)


        //val petsList: ArrayList<Pet> = ArrayList()

        profileRV.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        profileRV.adapter =
            ProfileRVAdapter(data/*, petsList*/, object : ProfileRVAdapter.MyOnClick {
                override fun onClick(p0: View?, position: Int) {

                    app.savePosition(position)

                    //val action = WelcomeFragmentDirections.actionWelcomeFragmentToPetProfileFragment(position)


                    view.findNavController().navigate(R.id.welcome_to_profile, Bundle().apply {
                        putInt("pet", position)
                    })
                }
            })

    }

}