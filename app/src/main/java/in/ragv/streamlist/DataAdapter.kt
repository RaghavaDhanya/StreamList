package `in`.ragv.streamlist

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class DataAdapter(val items : ArrayList<DataModel>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }


    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data =items.get(position)
        holder?.title?.text = data.title

        holder.itemView.setOnClickListener {
            Log.d("Raghava","Clicked_$data.title")

            val uri = Uri.parse(FileService().getPresignedURL(data.key).toString())
            val vlcIntent = Intent(Intent.ACTION_VIEW)
            vlcIntent.setPackage("org.videolan.vlc")
            vlcIntent.setDataAndTypeAndNormalize(uri, "video/*")
            vlcIntent.putExtra("title", data.title)
            vlcIntent.putExtra("from_start", false)
            context.startActivity(vlcIntent)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.grid_entry, parent, false))
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val title = view.findViewById<TextView>(R.id.title_text)

}