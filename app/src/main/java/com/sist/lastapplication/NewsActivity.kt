package com.sist.lastapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_cate_food.*
import kotlinx.android.synthetic.main.activity_news.*
import org.json.JSONObject
import java.net.URL

class NewsActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)
        fetchJsonData().execute("맛집") // ==> vararg params: String? => params가 받음.

        //클릭하면
        news_btn.setOnClickListener{
            val fd=news_editView.text //입력값을 받아옴
            dataList.clear() //쌓였던 데이터를 지우고 다시 실행.
            fetchJsonData().execute(fd.toString())
        }

        news_ListView.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view, position, id ->
            /*   var cateno=findViewById<TextView>(R.id.catenoTextView).text
               Toast.makeText(this,"선택번호:$cateno",Toast.LENGTH_SHORT).show()
               var title=findViewById<TextView>(R.id.titleTextView).text
               var subject=findViewById<TextView>(R.id.subjectTextView).text
               */
            var link=dataList.get(position).get("link")
            val intent=Intent(this,NewsDetailActivity::class.java)
            intent.putExtra("link",link.toString())//화면이 바뀌기 전에 데이터 넘기기
            //화면이동명령
            startActivity(intent)
        }
    }

    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /*findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE*/

        }

        //nodejs돌리기
        override fun doInBackground(vararg params: String?): String? {
            return URL("http://211.238.142.195:3355/news2?fd="+params[0]).readText(
                Charsets.UTF_8
            )
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            /* findViewById<ProgressBar>(R.id.loader).visibility = View.GONE*/

            val jsonObj = JSONObject(result)
            //{"category":[{},{},{}] 카테고리가 갖고있는 배열을 줘..
            val usersArr = jsonObj.getJSONArray("news")  //from server "news"
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)
                //데이터가졍ㅁ
                val map = HashMap<String, String>()
                //키                                 값
                map["title"] = singleUser.getString("title")
                map["content"] = singleUser.getString("description")
                map["author"] = singleUser.getString("author")
                map["link"] = singleUser.getString("link")
                dataList.add(map)
            }
            //객체선언
            val newsListView=findViewById<ListView>(R.id.news_ListView)
            //어댑터 부르기
            newsListView.adapter = NewsAdapter(this@NewsActivity, dataList)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item?.itemId)
        {
            R.id.home_menu->{
                //화면 이동하기
                var intent= Intent(this,HomeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_pop->{
                var intent= Intent(this,PopActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_recommend->{
                var intent= Intent(this,RecommandActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_recipe->{
                var intent= Intent(this,RecipeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_news->{
                var intent= Intent(this,NewsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}