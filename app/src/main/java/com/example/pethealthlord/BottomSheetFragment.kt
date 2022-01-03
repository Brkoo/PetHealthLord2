package com.example.pethealthlord

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.navigation.fragment.findNavController
import com.example.pethealthlord.BottomSheetFragment.Companion.CHANNEL_ID
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottomsheet_walks.*
import java.util.*

class BottomSheetFragment: BottomSheetDialogFragment() {
lateinit var calendar : Calendar
lateinit var alarmManager: AlarmManager
lateinit var pendingIntent: PendingIntent
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottomsheet_walks, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createNotificationChannel()
        numPickerHour.minValue = 1
        numPickerHour.maxValue = 12

        numPickerMin.minValue = 0
        numPickerMin.maxValue = 59

        val str = arrayOf<String>("AM", "PM")

        numPickerAmPm.minValue = 0
        numPickerAmPm.maxValue = (str.size - 1  )
        numPickerAmPm.displayedValues = str

        var hour = 0
        var min = 0
        var pmAmp = ""
        numPickerHour.setOnValueChangedListener { numberPicker, i, i2 ->
         hour = numberPicker.value

        }
        numPickerMin.setOnValueChangedListener { numberPicker, i, i2 ->
            min = numberPicker.value

        }
        numPickerAmPm.setOnValueChangedListener { numberPicker, i, i2 ->
            val i = numberPicker.value
            pmAmp = str[i]


        }

        buttonSave.setOnClickListener{
            calendar = Calendar.getInstance()
            if(pmAmp == "PM"){
            calendar[Calendar.HOUR_OF_DAY] = (hour + 12)
                calendar[Calendar.MINUTE] = min
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            }
            else{
                calendar[Calendar.HOUR_OF_DAY] = hour
                calendar[Calendar.MINUTE] = min
                calendar[Calendar.SECOND] = 0
                calendar[Calendar.MILLISECOND] = 0
            }

            setAlarm()

            Toast.makeText(requireContext(), "Alarm set for $hour - $min - $pmAmp",Toast.LENGTH_LONG).show()



            val HourOfTheDay = calendar[Calendar.HOUR_OF_DAY].toString()

            val bundle = Bundle()

            bundle.putString("hourOfTheDay", HourOfTheDay)
            bundle.putString("Minutes", calendar[Calendar.MINUTE].toString())
            findNavController().navigate(R.id.action_bottomSheetFragment_to_walksFragment, bundle)

        }

    }

    private fun setAlarm() {
    alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), AlarmReceiver::class.java)

        pendingIntent = PendingIntent.getBroadcast(requireContext(), 0, intent, 0)
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent

        )
    }

    companion object {
        val CHANNEL_ID="com.um.feri.cs.pora.kotlinnotification" //my channel id
        val TIME_ID="TIME_ID"
        private var notificationId=0
        fun getNotificationUniqueID():Int {
            return notificationId++;
        }

    }
    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyTestChannel"
            val descriptionText = "Testing notifications"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(BottomSheetFragment.CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val manager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }

    }
}