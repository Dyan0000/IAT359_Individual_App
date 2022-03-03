package com.example.individual_app

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2

class MyViewpager2Adapter (private val views: Array<Int>,
                           private val context: Context
) : RecyclerView.Adapter<MyViewpager2Adapter.MyViewHolder> ()
{
    class MyViewHolder (val viewpager2: ViewPager2) : RecyclerView.ViewHolder (viewpager2)

    override fun getItemCount(): Int = views.size

    override fun onCreateViewHolder(container: ViewGroup, position: Int): MyViewHolder {
        val thisView = views[position]
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(
            thisView,
            container,
            false
        ) as ViewPager2
        container.addView(layout)
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.viewpager2.currentItem = views[position]
    }

}