package com.jiangkang.ktools

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.Explode
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import androidx.recyclerview.widget.GridLayoutManager
import butterknife.ButterKnife
import com.jiangkang.hybrid.Khybrid
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //转场动画
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.enterTransition = Explode()
            window.exitTransition = Explode()
        }
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        initViews()
        reportFullyDrawn()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.FLAG_ACTIVITY_CLEAR_TOP and intent.flags != 0) {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menu.add(0, 0, 0, "关于")
        menu.add(0, 1, 1, "源代码")
        menu.add(0, 2, 2, "文章解析")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            0 -> startActivity(Intent(this@MainActivity, AboutActivity::class.java))
            1 -> openBrowser("https://github.com/jiangkang/KTools")
            2 -> openBrowser("http://www.jianshu.com/u/2c22c64b9aff")
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openBrowser(url: String) {
        Khybrid().loadUrl(this, url)
    }

    private fun initViews() {
        rc_function_list.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = FunctionAdapter(this@MainActivity)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            finishAfterTransition()
        }
    }

    companion object {
        @JvmStatic
        fun launch(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }

        private const val TAG = "MainActivity"
    }
}