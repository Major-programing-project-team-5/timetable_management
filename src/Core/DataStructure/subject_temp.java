package Core.DataStructure;

import java.util.Arrays;

public class subject_temp {



    public String name;
    public int subNumber;
    public int cost;
    public String area;
    public String days;
    public String room;
    public String[] previousSub;

    public subject_temp(){

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
}
