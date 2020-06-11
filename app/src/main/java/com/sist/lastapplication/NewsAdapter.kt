package com.sist.lastapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class NewsAdapter(private val context: Context,
                  private val dataList: ArrayList<HashMap<String, String>>) : BaseAdapter() {
                //ArrayList<HashMap<String, String>>) 맵 데이터를 넘기면 for문 돌리기 가능
    private val inflater: LayoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int { return dataList.size }
    override fun getItem(position: Int): Int { return position }
    override fun getItemId(position: Int): Long { return position.toLong() }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var dataitem = dataList[position]

        val rowView = inflater.inflate(R.layout.news_item, parent, false)
        rowView.findViewById<TextView>(R.id.news_title).text = dataitem.get("title")
        rowView.findViewById<TextView>(R.id.news_content).text = dataitem.get("content")
        rowView.findViewById<TextView>(R.id.news_author).text = dataitem.get("author")

        rowView.tag = position
        return rowView
    }
}
