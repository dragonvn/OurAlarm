package vn.heallife.duchv.ouralarm.background.model;

import android.content.Intent;

import vn.heallife.duchv.ouralarm.AlarmReceiver;

/**
 * Created by duchv on 2/13/15.
 */
public class Alarm {
    private String hour;
    private String minus;
    private String text;
    private boolean[] repeat;
    public boolean enable;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMinus() {
        return minus;
    }

    public int getMinusInt() {
        return Integer.parseInt(minus);
    }

    public void setMinus(String minus) {
        this.minus = minus;
    }

    public String getHour() {
        return hour;
    }
    public int getHourInt() {
        return Integer.parseInt(hour);
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public boolean[] getRepeat() {
        return repeat;
    }

    public void setRepeat(boolean[] repeat) {
        this.repeat = repeat;
    }
    public Alarm(String h, String m, String msg, boolean enable){
        setHour(h);
        setMinus(m);
        setText(msg);
        setEnable(enable);
    }
    public void editAlarm(String h, String m, String msg, boolean enable){
        setHour(h);
        setMinus(m);
        setText(msg);
        setEnable(enable);
    }
}
