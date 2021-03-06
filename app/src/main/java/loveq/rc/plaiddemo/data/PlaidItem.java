package loveq.rc.plaiddemo.data;

/**
 * Created by rc on 2017/12/22.
 * Description:
 */

public abstract class PlaidItem {

    public final long id;
    public final String title;
    public String url; // can't be final as some APIs use different serialized names
    public String dataSource;
    public int page;
    public float weight; // used for sorting
    public int colspan;

    public PlaidItem(long id,
                     String title,
                     String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Override
    public String toString() {
        return title;
    }
    /**
     * Equals check based on the id field
     */
    @Override
    public boolean equals(Object o) {
        return (o.getClass() == getClass() && ((PlaidItem) o).id == id);
    }
}
