package Core.DataStructure;

import Core.Exception.addException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class subjectManager {
    //튜플 찾기용으로 사용하는, 해쉬맵이 담겨 있는 객체입니다.

    public static HashSet<Subject> subjectSets;
    public static List<Subject> subjectList;
    public static Map<String, String> subjectSets_fineNameUseCode; // findSubject용 변수.
    /*
    과목 코드만 주면 이름을 리턴해주기 위한 변수.
    <1300, 자료구조> 식으로 매핑됨.
     */

    /**
     * subjectManager에 과목을 추가하는 메소드입니다.
     * @param subject 추가할 서브젝트입니다.
     * @return 제대로 추가되었는지, 추가되지 않았는지를 리턴합니다.
     */
    public static boolean addSubjectToManager(Subject subject){
        try{
            if(subjectSets.contains(subject)){
                throw new addException("subjectManager - addSubjectToManager : 과목이 이미 존재함.");
            }else{
                subjectSets.add(subject);
                subjectList.add(subject);
                if(!subjectSets_fineNameUseCode.containsKey(subject.getSubjectName())){

                    subjectSets_fineNameUseCode.put(subject.getSubjectCode(), subject.getSubjectName());
                }
                return true;
            }
        }catch (addException e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    //해쉬맵에서 리턴해야 하는 것은 subject
    //그러므로 중복을 허용해선 안된다.
}
