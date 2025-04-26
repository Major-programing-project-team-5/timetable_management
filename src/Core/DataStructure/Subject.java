package Core.DataStructure;

import Core.Utils.findSubjectClass;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Subject {

    private String subjectName;// 과목명

    private String[] subjectDayTime;// 과목 수업 요일 및 시간
    /*
   {(월,13:00~15:00), (화,13:00~15:00)}의 형식으로 저장되어 있음.
   입력받을 때는 월,화 13:00~15:00의 형식으로 입력받음
   만약 요일별로 수업 시간이 다를 시에는 월,화 13:00~15:00,17:00~18:00으로 입력받음.
     */

    private String subjectCode; // 과목코드

    private int credit;// 학점
    /*
    1~9 사이여야 함.
    만약 임시로 생성된 변수라면 -1로 고정.
     */

    private String category; // 이수구분
    /*
    예시 : 전공필수, 전공선택
     */
    private String courseCode; // 학수번호
    /*
    2차 구현때 위해 남겨둔 레거시
     */

    private String lectureRoom; // 강의실
    /*
    302호, 301호, 공B305 등등..
     */
    private List<Subject> previousSubject;// 선수과목
    /*
    텍스트 파일엔 과목코드만 적어둠.
     */

    /**
     * 생성자
     * @param previousSubjectCodes 선수과목 정보임. 만약 없으면 null 넣을 것.
     */
    public Subject(String subjectName, String[] subjectDayTime, String subjectCode,
                   int credit, String category, String courseCode, String lectureRoom,
                   List<String> previousSubjectCodes) {
        this.subjectName = subjectName;
        this.subjectDayTime = subjectDayTime;
        this.subjectCode = subjectCode;
        this.credit = credit;
        this.category = category;
        this.courseCode = courseCode;
        this.lectureRoom = lectureRoom;
        if(previousSubjectCodes != null){
            for(var i : previousSubjectCodes){
                String[] tempstr = {i};
                Subject tempsubject = findSubjectClass.findSubject(tempstr);
                if(tempsubject != null){
                    previousSubject.add(tempsubject);
                }
            }
        }
    }

    /**
     * 선수과목의 경우엔 간단하게 이름-과목코드만 가진 객체로 생성해서 가지고있도록 함.
     * @param subjectCode
     * @param subjectName
     */
    public Subject(String subjectCode, String subjectName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = -1;//기본값
    }

    /**
     * 과목을 찾을 때 임시로 만드는 것.
     * @param subjectName
     * @param subjectDayTime
     * @param subjectCode
     */
    public Subject(String subjectName, String[] subjectDayTime, String subjectCode) {
        this.subjectDayTime = subjectDayTime;
        this.subjectName = subjectName;
        this.subjectCode = subjectCode;
        this.credit = -1;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "[" + subjectCode + "] " + subjectName + " (" + credit + "학점, " + category + ")";
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Subject subject = (Subject) object;
        return Objects.equals(subjectName, subject.subjectName) && Objects.deepEquals(subjectDayTime, subject.subjectDayTime) && Objects.equals(subjectCode, subject.subjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName, Arrays.hashCode(subjectDayTime), subjectCode);
    }
}