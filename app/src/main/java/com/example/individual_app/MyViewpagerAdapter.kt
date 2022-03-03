package com.example.individual_app

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

class MyViewpagerAdapter (private val views: Array<Int>,
                          private val context: Context
) : PagerAdapter()
{
    override fun getCount(): Int = views.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean
    {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val thisView = views[position]
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(
            thisView,
            container,
            false) as ViewGroup
        container.addView(layout)
        return layout
    }

    override fun destroyItem(container: ViewGroup, position: Int, view: Any)
    {
        container.removeView(view as View)
    }

    override fun getPageTitle(position: Int): CharSequence?
    {
        val tabTitles: Array<String> = arrayOf("TO-DO", "DONE")
        return tabTitles[position]
    }

}