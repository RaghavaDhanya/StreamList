package `in`.ragv.streamlist

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_main)

        val data = ArrayList<DataModel>()
        val recyclerView:RecyclerView = findViewById(R.id.recycler_view)
        val empty_text: TextView = findViewById(R.id.empty_text)
        recyclerView.layoutManager = GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false)
        recyclerView.adapter = DataAdapter(data, this)
        Thread(Runnable {
            try {
                data.addAll(FileService().getStreamables())
                runOnUiThread {
                    recyclerView.adapter?.notifyDataSetChanged()
                    if(data.isEmpty()){
                        empty_text.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }
                }

            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }).start()
    }
}