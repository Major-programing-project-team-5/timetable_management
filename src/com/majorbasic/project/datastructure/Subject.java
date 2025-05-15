package com.majorbasic.project.datastructure;

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

    private DayTime[] subjectDayTimes;

    private String subjectCode; // 과목코드

    private int credit;// 학점
    /*
    1~9 사이여야 함.
    만약 임시로 생성된 변수라면 -1로 고정.
     */

    public String getLectureRoom() {
        return lectureRoom;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String[] getSubjectDayTime() {
        return subjectDayTime;
    }

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
        setSubjectDayTimes(subjectDayTime);
        this.subjectCode = subjectCode;
        this.credit = credit;
        this.category = category;
        this.courseCode = courseCode;
        this.lectureRoom = lectureRoom;
        if(previousSubjectCodes != null){
            for(String i : previousSubjectCodes){
                String[] tempstr = {i};
                Subject tempsubject = SubjectManager.findSubject(tempstr);
                if(tempsubject != null){
                    previousSubject.add(tempsubject);
                }
            }
        }
    }

    public void setSubjectDayTimes(String[] dayTimes){
        String[] days = dayTimes[0].split(",");
        String[] times = dayTimes[1].split(",");
        if(days.length == 2){
            DayTime temp1;
            DayTime temp2;
            if(times.length == 2){
                temp1 = new DayTime(days[0], times[0]);
                temp2 = new DayTime(days[1], times[1]);
            }else{
                temp1 = new DayTime(days[0], times[0]);
                temp2 = new DayTime(days[1], times[0]);
            }
            this.subjectDayTimes = new DayTime[]{temp1, temp2};
        }else if(days.length == 1){
           this.subjectDayTimes = new DayTime[]{new DayTime(days[0], times[0])};
        }

    }
    public DayTime[] getSubjectDayTimes(){
        return subjectDayTimes;
    }

    /**
     * 선수과목의 경우엔 간단하게 이름-과목코드만 가진 객체로 생성해서 가지고있도록 함.
     * @param subjectCode 과목코드
     * @param subjectName 과목이름
     */
    public Subject(String subjectCode, String subjectName) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.credit = -1;//기본값
    }

    /**
     * 과목을 찾을 때 임시로 만드는 것.
     * @param subjectName 과목이름
     * @param subjectDayTime 과목시간 형식 ["요일,시간", "요일,시간"]
     * @param subjectCode 과목코드
     */
    public Subject(String subjectName, String[] subjectDayTime, String subjectCode) {
        setSubjectDayTimes(subjectDayTime);
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

    public String toSave() {
        StringBuilder dayBuilder = new StringBuilder();
        StringBuilder timeBuilder = new StringBuilder();

        for (int i = 0; i < subjectDayTime.length; i++) {
            String[] parts = subjectDayTime[i].split(",", 2);
            if (parts.length == 2) {
                dayBuilder.append(parts[0]);
                timeBuilder.append(parts[1]);
                if (i != subjectDayTime.length - 1) {
                    dayBuilder.append(",");
                    timeBuilder.append(",");
                }
            }
        }

        return subjectName + " " + dayBuilder + " " + timeBuilder + " " + subjectCode + " " + credit + " " + category + " " + courseCode + " " + lectureRoom;
    }


    public String toTable() {
        if (subjectDayTime.length < 2) {
            return subjectName + " " + subjectDayTime[0].charAt(0) + " " + subjectDayTime[0].split(",")[1] + " " +
                    subjectCode;
        }
        else {
            return subjectName + " " + subjectDayTime[0].charAt(0) + "," + subjectDayTime[1].charAt(0) + " " + subjectDayTime[0].split(",")[1] + "," + subjectDayTime[1].split(",")[1] + " " +
                    subjectCode;
        }
    }

    @Override
    public String toString() {
        return "[" + subjectCode + "] " + subjectName + " (" + credit + "학점, " + category + ")";
    }

    /**
     * equals는 1차 구현물 때의 버전을 따릅니다 -> 과목명, 시간, 과목 코드.
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Subject subject = (Subject) object;
        return Objects.equals(subjectName, subject.subjectName) && Objects.deepEquals(subjectDayTimes, subject.subjectDayTimes) && Objects.equals(subjectCode, subject.subjectCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subjectName, Arrays.hashCode(subjectDayTimes), subjectCode);
    }
}