package com.majorbasic.project.datastructure;

import com.majorbasic.project.exception.AddException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.majorbasic.project.utils.Util.dayTimeArr;

public class SubjectManager {
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
     *
     * @param subject 추가할 서브젝트입니다.
     * @return 제대로 추가되었는지, 추가되지 않았는지를 리턴합니다.
     */
    public static boolean addSubjectToManager(Subject subject) {
        try {
            if (subjectSets.contains(subject)) {
                throw new AddException("subjectManager - addSubjectToManager : 과목이 이미 존재함.");
            } else {
                subjectSets.add(subject);
                subjectList.add(subject);
                if (!subjectSets_fineNameUseCode.containsKey(subject.getSubjectName())) {

                    subjectSets_fineNameUseCode.put(subject.getSubjectCode(), subject.getSubjectName());
                }
                return true;
            }
        } catch (AddException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    //해쉬맵에서 리턴해야 하는 것은 subject
    //그러므로 중복을 허용해선 안된다.


    /**
     * @param tuples 튜플의 입력 형태 : 자료구조 월,목 13:00~15:00,17:00~18:00 1300
     *               각각 tuple0, (1, 2), 3
     * @return 해당 정보를 가진 과목을 리턴함.
     */
    public static Subject findSubject(String[] tuples) {
        if (tuples.length == 1) {
            //선수과목용 : 만약 선수과목의 추가용으로 과목코드만 날아왔을시
            //이 경우 과목 코드만 가지고 판별함.
            if (SubjectManager.subjectSets_fineNameUseCode.containsKey(tuples[0])) {
                String sujectname = SubjectManager.subjectSets_fineNameUseCode.get(tuples[0]);
                return new Subject(sujectname, tuples[0]);
            } else {
                return null;
            }
        }
        if (tuples.length < 4) {
            System.out.println("과목 튜플의 정보가 부족합니다.");
            return null;
        }
        //일반적인 과목 찾기용
        String[] day = new String[]{tuples[1], tuples[2]};

        Subject tempsubject = new Subject(tuples[0], day, tuples[3]);
        if (SubjectManager.subjectSets.contains(tempsubject)) {
            for (Subject i : SubjectManager.subjectList) {
                if (i.equals(tempsubject)) {
                    return i;
                }
            }
        }

        return null;
    }

    /**
     * SubjectManager쪽은 static이라 tostring override가 안 되어서 직접 만들었습니다.
     * @return 보유 중인 모든 과목의 정보를 string으로 리턴합니다.
     */
    public static String getAllSubjectInfo() {
        StringBuilder builder = new StringBuilder();
        for (Subject i : subjectList) {
            builder.append(i.toString()).append("\n");
        }
        return builder.toString();
    }
}
