package com.sist.lastapplication

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_recipe.*
import org.json.JSONObject
import java.net.URL

class RecipeActivity : AppCompatActivity() {
    var dataList = ArrayList<HashMap<String, String>>()
    var page:Int=1
    var totalPage:Int=1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)
        fetchJsonData().execute("1")

        preBtn.setOnClickListener{
            if(page>1)
            {
                page--;
                dataList.clear()  //새로운 내용으로 가져옴.
                fetchJsonData().execute(page.toString())
            }
        }
        nextBtn.setOnClickListener{
            if(page<totalPage)
            {
                page++;
                dataList.clear()  //새로운 내용으로 가져옴.
                fetchJsonData().execute(page.toString())
            }
        }
    }

    inner class fetchJsonData() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            /*findViewById<ProgressBar>(R.id.loader).visibility = View.VISIBLE*/

        }

        //nodejs돌리기
        override fun doInBackground(vararg params: String?): String? {
            return URL("http://211.238.142.195:3355/recipe2?page="+params[0]).readText(
                Charsets.UTF_8
            )
        }
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)

            /* findViewById<ProgressBar>(R.id.loader).visibility = View.GONE*/

            val jsonObj = JSONObject(result)
            //{"recipe":[{},{},{}] 카테고리가 갖고있는 배열을 줘..
            val usersArr = jsonObj.getJSONArray("recipe")
            for (i in 0 until usersArr.length()) {
                val singleUser = usersArr.getJSONObject(i)
                //데이터가졍ㅁ
                val map = HashMap<String, String>()
                map["title"] = singleUser.getString("title")
                map["poster"] = singleUser.getString("poster")
                dataList.add(map)
            }
            //객체선언
            val recipeListView=findViewById<ListView>(R.id.recipe_ListView)
            //어댑터 부르기
            recipeListView.adapter = RecipeAdapter(this@RecipeActivity, dataList)
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