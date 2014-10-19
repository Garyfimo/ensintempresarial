package empresarial.synapsesdk.com.model;

/**
 * Created by Garyfimo on 10/19/14.
 */
public class Project {

    private int id;
    private String title;
    private String sub_title;
    private String resume;
    private int image;

    public Project() {
    }

    public Project(int id, String title, String sub_title, String resume, int image) {
        this.title = title;
        this.sub_title = sub_title;
        this.resume = resume;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getResume() {
        return resume;
    }

    public void setResume(String resume) {
        this.resume = resume;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
