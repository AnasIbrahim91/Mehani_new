package mehani.mehani.wyanbu.com.mehani;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;



public class ListViewAdapter extends ArrayAdapter<ListViewitem> {
    public ListViewAdapter(Context context, int resource, List<ListViewitem> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if(null == v) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_item, null);
        }
        ListViewitem list_item = getItem(position);

        TextView txtid = (TextView) v.findViewById(R.id.txid);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        TextView txtTitle = (TextView) v.findViewById(R.id.txtTitle);
        TextView txtDescription = (TextView) v.findViewById(R.id.txtDescription);

        txtid.setText(list_item.getId());

        Picasso.with(getContext()).load(list_item.getImg()).into(img);
        txtTitle.setText(list_item.getTitle());
        txtDescription.setText(list_item.getDes());

        return v;
    }
}
