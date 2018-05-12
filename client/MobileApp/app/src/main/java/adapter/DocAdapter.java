package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.mobile.mobileapp.R;


/**
 * Created by Lijixuan on 2018/5/1.
 */

public class DocAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private String[] mdoclist;

    public DocAdapter(Context context, String[] doclist) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mdoclist = doclist;
    }


    @Override
    public int getCount() {
        return mdoclist.length;
    }

    @Override
    public Object getItem(int i) {
        return mdoclist[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = mLayoutInflater.inflate(R.layout.doclist_layout, null);
        TextView textView = (TextView) view.findViewById(R.id.doclist_item_textview);
        textView.setText(mdoclist[i]);
        return view;
    }
}
