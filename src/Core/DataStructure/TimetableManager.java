package Core.DataStructure;

import Core.Exception.addException;

import java.sql.Time;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class TimetableManager {
    public static HashSet<Timetable> timetableSets;
    public static List<Timetable> timetableList;
    /**
     * TimeTableManager에 시간표를 추가하는 메소드입니다.
     * @param timetable 시간표에 추가할 시간표입니다
     * @return 제대로 추가되었는지, 추가되지 않았는지를 리턴합니다.
     */
    public static boolean addTimeTabletoManager(Timetable timetable){
        try{
            if(timetableSets.contains(timetable)){
                throw new addException("TimetableManager - addTimeTabletoManager : 시간표가 이미 존재함.");
            }else{
                timetableSets.add(timetable);
                timetableList.add(timetable);

                return true;
            }
        }catch (addException e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
