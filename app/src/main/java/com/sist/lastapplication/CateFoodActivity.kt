package com.sist.lastapplication

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import org.json.JSONObject
import java.net.URL

class CateFoodActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cate_food)
        val cateno=intent.getStringExtra("cateno")
        val title=intent.getStringExtra("title")
        val subject=intent.getStringExtra("subject")

        val titleTextView=findViewById<TextView>(R.id.cate_titleView)
        titleTextView.text=title

        val subjectTextView=findViewById<TextView>(R.id.cate_subjectTextView)
        subjectTextView.text=subject
        fetchJsonData().execute(cateno) //데이터추가
    }
    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /*findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE*/
        }

        //nodejs돌리기
        override fun doInBackground(vararg params: String?): String? {
            return URL("http://211.238.142.195:3355/cate_food2?cno="+params[0]).readText(
                Charsets.UTF_8
            )
        }  //위에서 실행한 결과를 아래 result에서 받음
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            /* findViewById<ProgressBar>(R.id.loader).visibility = View.GONE*/

            val jsonObj = JSONObject(result)
            //{"category":[{},{},{}] 카테고리가 갖고있는 배열을 줘..
            val usersArr = jsonObj.getJSONArray("cate_food")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)
                //데이터가졍ㅁ
                val map = HashMap<String, String>()
                //키                                 값
                map["title"] = singleUser.getString("title")
                map["address"] = singleUser.getString("address")
                var img:String=singleUser.getString("image")
                //그림이 ^로 4개가 붙어있기떄문에 한개 자르기
                img=img.substring(0,img.indexOf("^"))
                map["image"] = img
                map["tel"] = singleUser.getString("tel")
                dataList.add(map)
            }
            //객체선언
            val cateListView=findViewById<ListView>(R.id.cate_ListView)
            //어댑터 부르기
            cateListView.adapter = CateFoodAdapter(this@CateFoodActivity, dataList)
        }
    }


}