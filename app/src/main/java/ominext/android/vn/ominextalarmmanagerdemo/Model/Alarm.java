package ominext.android.vn.ominextalarmmanagerdemo.Model;

/**
 * Created by MyPC on 03/07/2017.
 */

public class Alarm {
    private int id;
    private String time;

    public Alarm( int id, String time) {

        this.id = id;
        this.time = time;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
