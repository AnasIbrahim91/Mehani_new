package mehani.mehani.wyanbu.com.mehani.GridView;

/**
 * Created by Anas on 9/8/2017.
 */

public class GridViewitems {
    private String imageurl;
    private String name;

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public GridViewitems(String imageurl, String name, int id) {
        this.imageurl = imageurl;
        this.name = name;
        this.id = id;
    }

    private int id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
