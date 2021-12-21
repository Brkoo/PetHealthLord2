package com.example.pethealthlord

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mylib.PetSize
import kotlinx.android.synthetic.main.item_breed_layout.view.*

class SizeRVAdapter(val list: ArrayList<PetSize>) :
    RecyclerView.Adapter<SizeRVAdapter.MyView>() {

    val pics: IntArray = intArrayOf(
        R.drawable.ic_small_dog,
        R.drawable.ic_medium_dog,
        R.drawable.ic_medium_large_dog,
        R.drawable.ic_large_dog
    )

    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView
        var size: TextView
        var text: TextView

        init {
            imageView = view
                .findViewById<ImageView>(R.id.imageView)
            size = view.findViewById<TextView>(R.id.dogSize)
            text = view.findViewById<TextView>(R.id.sizeInfo)

            view.setOnClickListener { v: View ->
                val position: Int = adapterPosition

                if (view.buttonChecked.visibility == View.VISIBLE) {
                    view.buttonChecked.visibility = View.GONE
                } else
                    view.buttonChecked.visibility = View.VISIBLE

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.item_breed_layout,
                parent,
                false
            )
        return MyView(itemView)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        val item = list.get(position)


        holder.imageView.setImageResource(pics[position])
        holder.size.text = item.size
        holder.text.text = item.text

        //Loading Image into view
        //Picasso.get().load(listData).placeholder(R.mipmap.ic_launcher).into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return list.size
    }

}