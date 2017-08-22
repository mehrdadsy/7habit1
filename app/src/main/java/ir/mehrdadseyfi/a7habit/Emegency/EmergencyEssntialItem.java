package ir.mehrdadseyfi.a7habit.Emegency;

import com.orm.SugarRecord;

/**
 * Created by admin on 8/21/2017.
 */

public class EmergencyEssntialItem extends SugarRecord<EmergencyEssntialItem> {
    String title;

    String detial;
    String calenderYear;
    String calenderMount;
    String calenderday;
    String hours;
    String minutes;

    public EmergencyEssntialItem(String title, String detial, String calenderYear, String calenderMount, String calenderday, String hours, String minutes) {
        this.title = title;
        this.detial = detial;
        this.calenderYear = calenderYear;
        this.calenderMount = calenderMount;
        this.calenderday = calenderday;
        this.hours = hours;
        this.minutes = minutes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetial() {
        return detial;
    }

    public void setDetial(String detial) {
        this.detial = detial;
    }

    public String getCalenderYear() {
        return calenderYear;
    }

    public void setCalenderYear(String calenderYear) {
        this.calenderYear = calenderYear;
    }

    public String getCalenderMount() {
        return calenderMount;
    }

    public void setCalenderMount(String calenderMount) {
        this.calenderMount = calenderMount;
    }

    public String getCalenderday() {
        return calenderday;
    }

    public void setCalenderday(String calenderday) {
        this.calenderday = calenderday;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public EmergencyEssntialItem() {
    }

}