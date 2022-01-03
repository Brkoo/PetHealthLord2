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
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.item_breed_layout.view.*
import org.w3c.dom.Text

class WalksRvAdapter(
    private val data: String/*, val items: ArrayList<Pet>*/,
    private val OnClickObject: WalksRvAdapter.MyOnClick
) :
    RecyclerView.Adapter<WalksRvAdapter.MyView>() {


    interface MyOnClick {
        fun onClick(p0: View?, position: Int)

    }

    interface MyOnLongClick {
        fun onLongClick(p0: View?, position: Int)
    }



    class MyView(view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView
        var fab : FloatingActionButton

        init {
            textView = view.findViewById(R.id.whentextView)
            fab = view.findViewById(R.id.floatingActionButton)
        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val itemView: View = LayoutInflater
            .from(parent.context)
            .inflate(
                R.layout.walks_layout_rv,
                parent,
                false
            )
        return MyView(itemView)
    }


    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {

        holder.textView.text = data

        holder.fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                OnClickObject.onClick(p0, holder.adapterPosition)
            }


        })

    }

}