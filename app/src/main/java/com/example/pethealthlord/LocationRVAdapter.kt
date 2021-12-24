package com.example.pethealthlord

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.AllLocations
import com.example.mylib.AllPets
import timber.log.Timber

class LocationRVAdapter (private val data: AllLocations/*, val items: ArrayList<Pet>*/,
                         private val OnClickObject: LocationRVAdapter.MyOnClick) :
    RecyclerView.Adapter<LocationRVAdapter.MyView>() {


    interface MyOnClick {
        fun onClick(p0: View?, position: Int)

    }

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var Name: TextView
        var Rating: TextView
        var Location: TextView
        var Phone: TextView
        var Time: TextView
        var LocationCV : CardView


        init {
            Name = view
                .findViewById<TextView>(R.id.LName)
            Rating = view.findViewById<TextView>(R.id.LRating)
            Location = view.findViewById<TextView>(R.id.LLocation)
            Phone = view.findViewById<TextView>(R.id.LPhone)
            Time = view.findViewById<TextView>(R.id.LWorkTime)
            LocationCV = itemView.findViewById(R.id.LocationCV)





        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.location_rv_layout,
                parent,
                false
            )

        return MyView(itemView)
    }



    override fun getItemCount(): Int {
        return data.AllLocations.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val item = data.AllLocations[position]


        holder.Name.text = item.name
        holder.Rating.text = item.rating
        holder.Location.text = item.location
        holder.Phone.text = item.phoneNumber
        holder.Time.text = item.time



        holder.LocationCV.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {

                Timber.d("Here code comes Click on ${holder.adapterPosition}") //LOCAL ACTION
                OnClickObject.onClick(p0,holder.adapterPosition) //Action from Activity
            }
        })
        //Loading Image into view
        //Picasso.get().load(listData).placeholder(R.mipmap.ic_launcher).into(holder.imageView)

    }

}
