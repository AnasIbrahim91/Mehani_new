package mehani.mehani.wyanbu.com.mehani.ListView;

/**
 * Created by Anas on 9/8/2017.
 */

public class ListViewitem {
    private String id;
    private String title;
    private String des;

    public ListViewitem(String id, String title, String des, String img) {
        this.id = id;
        this.title = title;
        this.des = des;
        this.img = img;
    }

    public String getId() {

        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    private String img;
}
