package com.example.parkingapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.min


class HomeFragment : Fragment(){


    val now = Calendar.getInstance()

    private fun pickDate(
        year:Int = now.get(Calendar.YEAR),
        month:Int =now.get(Calendar.MONTH),
        dayOfMonth:Int = now.get(Calendar.DAY_OF_MONTH)){

    val timePicker = TimePickerDialog(requireContext(),{view, hourOfDay,minute ->
            timePicked.text = "$year,${month+1},$dayOfMonth \n $hourOfDay:$minute"
        },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),true)
    timePicker.show()
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        button.setOnClickListener {

            parentFragmentManager.beginTransaction().apply {
                replace(R.id.mapsFragment,MapsFragment())
                commit()
            }
        }


        timePicker.setOnClickListener{
            val cal = Calendar.getInstance()
            if (checkDay.isChecked)
                pickDate()
            else{
                DatePickerDialog(requireContext(), { view, year, month, dayOfMonth ->
                    pickDate(year,month, dayOfMonth)
                },cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH)).show()
        }}

    }


}