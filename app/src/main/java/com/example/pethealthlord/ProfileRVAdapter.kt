package com.example.pethealthlord

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.AllPets
import com.example.mylib.Pet
import com.example.mylib.PetSize
import kotlinx.android.synthetic.main.item_breed_layout.view.*

class ProfileRVAdapter(
    private val data: AllPets/*, val items: ArrayList<Pet>*/,
    private val OnClickObject: ProfileRVAdapter.MyOnClick
) :
    RecyclerView.Adapter<ProfileRVAdapter.MyView>() {


    interface MyOnClick {
        fun onClick(p0: View?, position: Int)

    }

    interface MyOnLongClick {
        fun onLongClick(p0: View?, position: Int)
    }


    val pics: IntArray = intArrayOf(
        R.drawable.ic_small_dog,
        R.drawable.ic_medium_dog,
        R.drawable.ic_medium_large_dog,
        R.drawable.ic_large_dog,
        R.drawable.ic_batman
    )

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView

        init {
            imageView = view
                .findViewById<ImageView>(R.id.imageViewProfile)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.profile_picture_layout,
                parent,
                false
            )
        return MyView(itemView)
    }


    override fun getItemCount(): Int {
        return data.AllPets.size
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {

        holder.imageView.setImageResource(pics[position])

        holder.imageView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                OnClickObject.onClick(p0, holder.adapterPosition)
            }


        })

    }

}