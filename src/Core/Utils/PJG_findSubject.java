package Core.Utils;
import Core.DataStructure.*;

import javax.security.auth.Subject;
import java.util.*;

public class PJG_findSubject {

    public String name;
    public int subNumber;
    public int cost;
    public String area;
    public String days;
    public String room;
    public String[] previousSub;

    public subject_temp fineSubjectbytuples(String tuples){
        String[] tuple = tuples.split(" ");
        int length = tuple.length;
        if(length == 6){
            subject_temp tempSubject = new subject_temp(tuple[0], Integer.parseInt(tuple[1]), Integer.parseInt(tuple[2]),
                                                        tuple[3], tuple[4], tuple[5]);
            if(subjectManager.subjectSets.contains(tempSubject)){
                for(var temp : subjectManager.subjectList){
                    if(tempSubject.equals(temp)){
                        return temp;
                    }
                }
            }
        }
        List<subject_temp> tempSubjectList = new ArrayList<>();
        int i = 0;
        for(var temp : subjectManager.subjectList) {
            if (tuple[i].equals(temp.name)) {
                tempSubjectList.add(temp);
                i++;
            }
            if(tuple[i].equals(temp.subNumber))

        }
        if
    }
}
