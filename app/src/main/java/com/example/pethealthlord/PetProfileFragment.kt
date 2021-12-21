package com.example.pethealthlord

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.mylib.AllPets
import com.example.mylib.Pet
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_pet_profile.*
import org.apache.commons.io.FileUtils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PetProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PetProfileFragment : Fragment(R.layout.fragment_pet_profile) {

    lateinit var app: MyApplication
    lateinit var pet: Pet
    val gson = Gson()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        app = (activity?.application as MyApplication)
        val data = gson.fromJson(FileUtils.readFileToString(app.file), AllPets::class.java)


        val position = requireArguments().getInt("pet")

        showEditTextDialog(position)

        pet = data.AllPets[position]


        tvPetName.text = pet.Name.toString()

        weight.text = pet.Weight.toString()


    }

    private fun showEditTextDialog(position: Int) {
        tvPetName.setOnClickListener {

            val builder = AlertDialog.Builder(this.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.ETName)

            with(builder) {
                setTitle("Change your pets Name")
                setPositiveButton("OK") { dialog, which ->
                    app.data.AllPets[position].Name = editText.text.toString()
                    app.saveToFile()
                    view?.findNavController()
                        ?.navigate(R.id.action_petProfileFragment_self, Bundle().apply {
                            putInt("pet", position)
                        })
                }
                setNegativeButton("Cancel") { dialog, which ->
                    Log.d("Main", "negative button clicked")
                }
                setView(dialogLayout)
                show()
            }


        }
        weightCard.setOnClickListener {

            val builder = AlertDialog.Builder(this.context)
            val inflater = layoutInflater
            val dialogLayout = inflater.inflate(R.layout.edit_text_layout, null)
            val editText = dialogLayout.findViewById<EditText>(R.id.ETName)

            with(builder) {
                setTitle("Change your pets Weight")
                editText.setText("Weight")
                setPositiveButton("OK") { dialog, which ->
                    app.data.AllPets[position].Weight = editText.text.toString().toInt()
                    app.saveToFile()
                    view?.findNavController()
                        ?.navigate(R.id.action_petProfileFragment_self, Bundle().apply {
                            putInt("pet", position)
                        })
                }
                setNegativeButton("Cancel") { dialog, which ->
                    Log.d("Main", "negative button clicked")
                }
                setView(dialogLayout)
                show()
            }


        }


    }


}