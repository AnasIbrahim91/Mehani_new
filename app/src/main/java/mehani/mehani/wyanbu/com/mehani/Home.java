package mehani.mehani.wyanbu.com.mehani;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private GridView gridView;
    private List<GridViewitems> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        gridView = (GridView) findViewById(R.id.gridview);



getgridview();
        GridViewAdapter gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item, items);

        gridView.setAdapter(gridViewAdapter);


    }


    public List<GridViewitems> getgridview() {

        items = new ArrayList<>();
        items = new ArrayList<>();
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));

        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        items.add(new GridViewitems(R.drawable.homeicon, "anas", 0));
        return items;
    }
}
