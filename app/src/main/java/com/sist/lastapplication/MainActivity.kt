package com.sist.lastapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
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
                var intent=Intent(this,HomeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_pop->{
                var intent=Intent(this,PopActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_recommend->{
                var intent=Intent(this,RecommandActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_recipe->{
                var intent=Intent(this,RecipeActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.home_news->{
                var intent=Intent(this,NewsActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}