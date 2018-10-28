package s1615548.coinz.Adapter

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import s1615548.coinz.Model.wallet_Layout
import s1615548.coinz.R
import s1615548.coinz.changeLight

class wallet_adaptor(private val getContext: Context, private val CustomLayoutId: Int, private val custom_item: ArrayList<wallet_Layout>)
    : ArrayAdapter<wallet_Layout>(getContext, CustomLayoutId, custom_item) {

    var selectedPositions = MutableList<Boolean>(custom_item.size+1){false}


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

        if(selectedPositions[position]){
            changeLight(Holder.img!!,-80)
        }else{
            changeLight(Holder.img!!,0)
        }

        Holder.img!!.setImageResource(item.image)
        Holder.txt!!.setText(item.text)

        return row!!

    }

    class ViewHolder
    {
        internal var img : ImageView? = null
        internal var txt : TextView? = null
    }

    fun numSelect(): Int{
        var output = 0

        var i = 0
        while(i<selectedPositions.size){
            if(selectedPositions[i]){
                output++
            }
            i++
        }
        return output
    }


}