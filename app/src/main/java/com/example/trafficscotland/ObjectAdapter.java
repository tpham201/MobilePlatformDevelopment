
//Pham Thi Hue
//S1839331

package com.example.trafficscotland;

import android.app.Activity;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class ObjectAdapter extends ArrayAdapter<objectTrafficScotland> {
    private Activity context;
    private ArrayList<objectTrafficScotland> object;
    private List<objectTrafficScotland> searchList=null;
    private static LayoutInflater inflater=null;

    public ObjectAdapter(Activity context, int textViewResourceId, ArrayList<objectTrafficScotland> _object)
    {
        super (context, textViewResourceId, _object);
        try
        {
            this.context=context;
            this.object=_object;
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        catch (Exception e)
        {}

    }
    public int getCount()
    {
        return object.size();
    }
    public objectTrafficScotland getItem(objectTrafficScotland position)
    {
        return position;
    }
    public long getItemId(int position)
    {
        return  position;
    }
    public static class ViewHolder
    {
        //private TextView title,description,link, georrspoint, date;
        public TextView titleD;
        public TextView descriptionD;
        public TextView linkD;
        public TextView georrspointD;
        public TextView dateD;

    }
    public View getView(int position, View convertView, ViewGroup parent)
    {
        View itemView = convertView;
        final ViewHolder holder;
        try
        {
            if (convertView==null)
            {
                itemView=inflater.inflate(R.layout.list_item,null);
                holder = new ViewHolder();
                holder.titleD=(TextView) itemView.findViewById(R.id.title);
                holder.descriptionD=(TextView) itemView.findViewById(R.id.description);
                holder.linkD=(TextView) itemView.findViewById(R.id.link);
                holder.georrspointD=(TextView) itemView.findViewById(R.id.georrspoint);
                holder.dateD=(TextView) itemView.findViewById(R.id.date);
                itemView.setTag(holder);
            }
            else
            {
                holder=(ViewHolder) itemView.getTag();
            }
            holder.titleD.setText(object.get(position).title);
            holder.descriptionD.setText(object.get(position).description);
            holder.linkD.setText(object.get(position).link);
            holder.georrspointD.setText(object.get(position).georsspoint);
            holder.dateD.setText(object.get(position).date);

        }
        catch (Exception e)
        {

        }
        return itemView;

    }
}
