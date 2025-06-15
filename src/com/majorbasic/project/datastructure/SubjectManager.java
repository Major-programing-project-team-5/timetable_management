package com.majorbasic.project.datastructure;

import com.majorbasic.project.exception.AddException;

import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static com.majorbasic.project.utils.Util.dayTimeArr;

public class SubjectManager {
    //튜플 찾기용으로 사용하는, 해쉬맵이 담겨 있는 객체입니다.

    public static HashSet<Subject> subjectSets;//해당 과목이 존재하는지 찾기 위한 셋
    public static List<Subject> subjectList;//만약 존재한다면 이거 돌려서 찾아감.

    /*
   과목 코드만 주면 이름을 리턴해주기 위한 변수.
   <1300, 자료구조> 식으로 매핑됨.
    */
    public static Map<String, String> subjectSets_fineNameUseCode; // findSubject용 변수.


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
     *
     * @param subject_code 과목 코드를 이용해서 과목을 찾습니다.
     * @return 해당 과목 코드로 찾을 수 있는 과목을 리턴합니다.
     */
    public static Subject findSubject(String subject_code){
        ///예외 처리 구간

        //null일때 기본 리턴
        if(subject_code == null){
            System.out.println("과목 코드 입력이 공백입니다");
            return null;
        }
        //기본적으로 subject를 만들 때 과목코드만 제공함.
        Subject temp_sub = new Subject(subject_code);//과목 코드만 이용해서 과목 만들기.

        if(SubjectManager.subjectSets.contains(temp_sub)){
            for(Subject sub : subjectList){
                if(sub.equals(temp_sub)){
                    return sub;
                }
            }
        }else{
            return null;//만약 존재하지 않는다면 null 리턴
            //같은 과목이 두 번 동시에 들어갈 수는 없으므로 이는 타당함.
        }
        return null;
    }

    /**
     * 학수번호로만 과목을 찾는 함수입니다.
     * @param n 학수번호로만 과목을 찾겠다는 표시입니다. 아무 값이나 넣으세요
     * @return
     */

    public static Subject findSubject(String coursecode, int n){
        ///예외 처리 구간

        //null일때 기본 리턴
        if(coursecode == null){
            System.out.println("학수번호 입력이 공백입니다");
            return null;
        }

        for(Subject sub : subjectList){
            if(sub.equalsUseCourseCode(coursecode)){
                return sub;
            }
        }
        System.out.println("해당 학수번호로 과목을 찾을 수 업습니다.");
        return null;
    }
    /**
     * @param tuples 과목명 학수번호 학점을 이용해 과목을 찾습니다. string 배열로 넣어주세요
     * @return 해당 정보를 가진 과목을 리턴함.
     */
    public static Subject findSubject(String[] tuples) {

        // 예외처리용
        if (tuples.length < 3) {
            System.out.println("과목 튜플의 정보가 부족합니다.");
            return null;
        }

        int credit;
        try {
            credit = Integer.parseInt(tuples[2]);
        } catch (NumberFormatException e) {
            System.out.println("학점 정보가 올바른 숫자가 아닙니다: " + tuples[2]);
            return null;
        }

        // tuple 0번(이름), 1번(학수번호), 2번(학점:int)으로 Subject 생성
        Subject temp_sub = new Subject(tuples[0], tuples[1], credit);

        // 해당 subject가 hashset에 있는지 확인
        // 있으면 subjectList에서 찾아서 정확한 객체를 리턴
        for (Subject sub : subjectList) {
            if (sub.equals(temp_sub)) {
                return sub;
            }
        }

        // 없으면 null 리턴
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
