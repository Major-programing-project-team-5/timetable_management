package Core.DataStructure;

import java.util.Arrays;
import java.util.Objects;

public class subject_temp {



    public String name;
    public int subNumber;
    public int cost;
    public String area;
    public String days;
    public String room;
    public String[] previousSub;

    public subject_temp(String name, int subNumber, int cost, String area, String days, String room) {
        this.name = name;
        this.subNumber = subNumber;
        this.cost = cost;
        this.area = area;
        this.days = days;
        this.room = room;
    }

    public subject_temp(String name, int subNumber, int cost, String area, String days, String room, String[] previousSub) {
        this.name = name;
        this.subNumber = subNumber;
        this.cost = cost;
        this.area = area;
        this.days = days;
        this.room = room;
        this.previousSub = previousSub;
    }

    @Override
    //toString쪽 더 다듬어주시면 됩니다.
    public String toString() {
        return "subject_temp{" +
                "name='" + name + '\'' +
                ", subNumber=" + subNumber +
                ", cost=" + cost +
                ", area='" + area + '\'' +
                ", days='" + days + '\'' +
                ", room='" + room + '\'' +
                ", previousSub=" + Arrays.toString(previousSub) +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        subject_temp that = (subject_temp) object;
        return subNumber == that.subNumber && cost == that.cost && Objects.equals(name, that.name) && Objects.equals(area, that.area) && Objects.equals(days, that.days) && Objects.equals(room, that.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, subNumber, cost, area, days, room, Arrays.hashCode(previousSub));
    }

    public String[] getSubjectInfo(){
        return new String[]{name, Integer.toString(subNumber), Integer.toString(cost),
                        area, days, room};
    }
}
