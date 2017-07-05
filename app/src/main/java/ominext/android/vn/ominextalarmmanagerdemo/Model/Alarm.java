package ominext.android.vn.ominextalarmmanagerdemo.Model;

/**
 * Created by MyPC on 03/07/2017.
 */

public class Alarm {
    private boolean check = false;
    private int id;
    private String time;
    private int iUniqueId;

    public Alarm(boolean check, int id, String time, int iUniqueId) {
        this.check = check;
        this.id = id;
        this.time = time;
        this.iUniqueId = iUniqueId;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
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

    public int getiUniqueId() {
        return iUniqueId;
    }

    public void setiUniqueId(int iUniqueId) {
        this.iUniqueId = iUniqueId;
    }
}
