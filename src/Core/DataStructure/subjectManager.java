package Core.DataStructure;

import Core.Exception.addException;

import java.util.HashMap;
import java.util.HashSet;

public class subjectManager {
    //튜플 찾기용으로 사용하는, 해쉬맵 담긴 뭐 그런겁니다.

    public HashSet<subject_temp> subjectSets;

    /**
     * subjectManager에 과목을 추가하는 메소드입니다.
     * @param subject 추가할 서브젝트의 이름입니다
     * @return 제대로 추가되었는지, 추가되지 않았는지를 리턴합니다.
     */
    public boolean addSubjectToManager(subject_temp subject){
        try{
            if(subjectSets.contains(subject)){
                throw new addException("subjectManager - addSubjectToManager : 과목이 이미 존재함.");
            }else{
                subjectSets.add(subject);
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
