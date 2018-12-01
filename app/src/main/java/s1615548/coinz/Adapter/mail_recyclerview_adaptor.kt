package s1615548.coinz.Adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import s1615548.coinz.R

class mail_recyclerview_adaptor(val titles: ArrayList<String>): RecyclerView.Adapter<mail_recyclerview_adaptor.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view:View = LayoutInflater.from(parent.context).inflate(R.layout.layout_mail, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mail_title.text = titles[position]
    }

    override fun getItemCount() = titles.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mail_title: TextView = itemView.findViewById(R.id.mail_title)
    }

}
