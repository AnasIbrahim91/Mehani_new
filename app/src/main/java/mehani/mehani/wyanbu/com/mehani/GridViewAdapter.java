package mehani.mehani.wyanbu.com.mehani;


import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Anas on 9/8/2017.
 */


public class GridViewAdapter extends ArrayAdapter<GridViewitems> {
    public GridViewAdapter(Context context, int resource, List<GridViewitems> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (null == v) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.grid_item, null);
        }
        GridViewitems GridViewitems = getItem(position);
        ImageView img = (ImageView) v.findViewById(R.id.imageView_grid);
        TextView name = (TextView) v.findViewById(R.id.txtname_grid);
        TextView id = (TextView) v.findViewById(R.id.id_grid);

        img.setImageResource(GridViewitems.getImageId());
        name.setText(GridViewitems.getName());
        id.setText(GridViewitems.getId()+"");

        return v;
    }
}
