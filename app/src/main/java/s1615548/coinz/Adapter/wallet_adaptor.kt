package s1615548.coinz.Adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.layout_wallet.view.*
import s1615548.coinz.Activity.wallet_Layout
import s1615548.coinz.R

class wallet_adaptor(private val getContext: Context, private val CustomLayoutId: Int, private val custom_item: ArrayList<wallet_Layout>)
    : ArrayAdapter<wallet_Layout>(getContext, CustomLayoutId, custom_item) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var row = convertView

        val Holder : ViewHolder

        if(row == null){

            val inflater = (getContext as Activity).layoutInflater

            row = inflater.inflate(CustomLayoutId, parent, false)

            Holder = ViewHolder()

            Holder.img = row.findViewById(R.id.img) as ImageView

            Holder.txt = row.findViewById(R.id.txt) as TextView

            row.setTag(Holder)

        }else{
            Holder = row.getTag() as ViewHolder
        }

        val item = custom_item[position]

        Holder.img!!.setImageResource(item.image)
        Holder.txt!!.setText(item.text)

        return row!!

    }

    class ViewHolder
    {
        internal var img : ImageView? = null
        internal var txt : TextView? = null
    }

}