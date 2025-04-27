package Core.Utils;

import Core.DataStructure.Subject;
import Core.DataStructure.subjectManager;

public class findSubjectClass {
    /**
     *
     * @param tuples 튜플의 입력 형태 : 자료구조 월,목 13:00~15:00,17:00~18:00 1300
     *               각각 tuple0, (1, 2), 3
     * @return 해당 정보를 가진 과목을 리턴함.
     */
    public static Subject findSubject(String[] tuples){
        if(tuples.length == 1){
            //선수과목용 : 만약 선수과목의 추가용으로 과목코드만 날아왔을시
            //이 경우 과목 코드만 가지고 판별함.
            if(subjectManager.subjectSets_fineNameUseCode.containsKey(tuples[0])){
                String sujectname = subjectManager.subjectSets_fineNameUseCode.get(tuples[0]);
                Subject tempsubject = new Subject(sujectname, tuples[0]);
                return tempsubject;
            }
            else{
                return null;
            }
        }
        //일반적인 과목 찾기용
        String[] day = tuples[1].split(",");
        String[] time = tuples[2].split(",");
        if(day.length == 2){
            if(time.length == 2){
                day[0] = day[0] + ","  + time[0];
                day[1] = day[1] + "," + time[1];
            }
            else{
                day[0] = day[0] + ","  + time[0];
                day[1] = day[1] + "," + time[0];
            }
        }else{
            day[0] = day[0] + ","  + time[0];
        }

         Subject tempsubject = new Subject(tuples[0], day, tuples[3]);
        if(subjectManager.subjectSets.contains(tempsubject)){
            for(Subject i : subjectManager.subjectList){
                if(i.equals(tempsubject)){
                    return i;
                }
            }
        }
        return null;


        //만약 튜플 개수 다 구현해야 하는 쪽으로 갈거면 이 쪽도 구현
//        switch (tuples.length){
//            case 4:
//
//                break;
//            case 3:
//                break;
//            case 2:
//                break;
//            case 1:
//                break;
//            default:
//                return null;
//                break;
//        }
    }
}
