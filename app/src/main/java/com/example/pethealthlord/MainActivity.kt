package com.example.pethealthlord

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.Pet
import com.example.pethealthlord.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var newRecyclerView: RecyclerView
    lateinit var app: MyApplication
    private lateinit var deletedPet: Pet


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //fragmentManager(WelcomeFragment())
        val navController = findNavController(R.id.fragmentContainerView)

        fab.setOnClickListener(){
            navController.navigateUp()
            navController.navigate(R.id.action_welcomeFragment_to_addElementsFragment)
        }

            bottomAppBar.setOnMenuItemClickListener{
                val position = app.getPosition()
                when(it.itemId){
                    R.id.DeleteItem ->{
                        deletedPet = app.data.AllPets.get(position)
                        app.data.AllPets.removeAt(position)
                        app.saveToFile()
                        //navController.navigateUp()

                        /*Snackbar.make(newRecyclerView, "${deletedPet.Name} is deleted", Snackbar.LENGTH_LONG).setAction("Undo", View.OnClickListener {
                            app.data.AllPets.add(deletedPet)
                            app.saveToFile()


                        }).show()*/
                        navController.navigate(R.id.action_petProfileFragment_to_welcomeFragment)
                    }


                }
                true
            }


        }




    }


