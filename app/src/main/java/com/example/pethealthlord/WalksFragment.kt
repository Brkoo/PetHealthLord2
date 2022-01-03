package com.example.pethealthlord

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.fragment_bottomsheet_walks.*

import kotlinx.android.synthetic.main.fragment_walks.*
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WalksFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalksFragment : Fragment() {
    lateinit var calendar: Calendar
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_walks, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val HourOfTheDay = arguments?.getString("hourOfTheDay")
        val Minutes = arguments?.getString("Minutes")
       if(HourOfTheDay != null && Minutes != null) {

           var data: String = "Time for a walk at:  $HourOfTheDay:$Minutes "
           rvWalks.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.VERTICAL, false)
           rvWalks.adapter = WalksRvAdapter(data, object : WalksRvAdapter.MyOnClick {
               override fun onClick(p0: View?, position: Int) {

               }

           })



       }

       /* val bottomSheetFragment = BottomSheetFragment()
        AddWalk.setOnClickListener{
            bottomSheetFragment.show(childFragmentManager, "BottomSheetDialog")


            }

                */
        AddWalk.setOnClickListener {
            findNavController().navigate(R.id.action_walksFragment_to_bottomSheetFragment)
        }







    }


}