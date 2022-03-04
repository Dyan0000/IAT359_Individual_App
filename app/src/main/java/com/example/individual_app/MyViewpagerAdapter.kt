package com.example.individual_app

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
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

        if (position == 0)
        {
            Log.d("MyViewpagerAdapter", "position is $position")
            supportFragmentManager
                .beginTransaction()
                .add(R.id.todo_list,
                    TodoListFragment.newInstance(),
                    "todo_list").commit()
        }
        else if (position == 1)
        {
            Log.d("MyViewpagerAdapter", "position is $position")
        }

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