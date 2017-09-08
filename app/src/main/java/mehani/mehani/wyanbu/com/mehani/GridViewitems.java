package mehani.mehani.wyanbu.com.mehani;

/**
 * Created by Anas on 9/8/2017.
 */

public class GridViewitems {
    private int imageId;
    private String name;
    private int id;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

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

    public GridViewitems(int imageId, String name, int id) {
        this.imageId = imageId;
        this.name = name;
        this.id = id;
    }
}
