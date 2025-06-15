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

    private DayTime[] subjectDayTimes; // 요일, 시간을 더 편하게 쓰기 위해 만든 구조.

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
    예시 : 전공, 지정교양, 지정필수, 기초교양, 기초교양:외국어, 기초교양:글쓰기, 기초교양:sw, 기초교양:취창업, 기초요양:인성, 심화교양, 심화교양:사고력, 심화고양:학문소양, 심화교양:글로벌
     */
    private String courseCode; // 학수번호


    private String lectureRoom; // 강의실
    /*
    302호, 301호, 공B305 등등..
     */
    private List<Subject> previousSubject;// 선수과목, 과목코드로 입력되어 있어야 함.

    private String grade;

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
                String tempstr = i;
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
     * 과목코드 이용한 과목 찾기 용으로 오로지 과목코드만 존재하는 과목을 만듭니다.
     * @param subjectCode 과목코드
     */
    public Subject(String subjectCode){
        this.subjectCode = subjectCode;
    }

    /**
     * 과목명, 학수번호, 학점으로 과거 과목을 찾을 때 임시로 만듬.
     * @param subjectName 과목명
     * @param courseCode 학수번호
     * @param credit 학점, int타입임!!
     */
    public Subject(String subjectName, String courseCode, int credit) {
        this.subjectName = subjectName;
        this.courseCode = courseCode;
        this.credit = credit;
    }

    /**
     * 시간표에 성적 추가할 때 사용
     * @param subjectName 과목이름
     * @param courseCode 학수번호
     * @param grade 성적
     */
    public Subject(String subjectName, String courseCode, String grade) {
        this.courseCode = courseCode;
        this.subjectName = subjectName;
        this.grade = ""; //기본값
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
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

    public List<Subject> getPreviousSubject() {
        return previousSubject;
    }

    public String toSave() {
        StringBuilder sb = new StringBuilder();

        // 과목명
        sb.append(subjectName).append(" ");

        // 요일 및 시간 (DayTime[])
        if (subjectDayTimes != null && subjectDayTimes.length > 0) {
            StringBuilder days = new StringBuilder();
            StringBuilder times = new StringBuilder();

            for (int i = 0; i < subjectDayTimes.length; i++) {
                days.append(subjectDayTimes[i].getDay());
                times.append(subjectDayTimes[i].getTime());
                if (i < subjectDayTimes.length - 1) {
                    days.append(",");
                    times.append(",");
                }
            }
            sb.append(days).append(" ").append(times).append(" ");
        }

        // 과목코드
        sb.append(subjectCode != null ? subjectCode : "").append(" ");

        // 학점
        sb.append(credit).append(" ");

        // 이수구분
        sb.append(category != null ? category : "").append(" ");

        // 학수번호
        sb.append(courseCode != null ? courseCode : "").append(" ");

        // 강의실
        sb.append(lectureRoom != null ? lectureRoom : "");

        // 선수과목 (쉼표 없이 나열)
        if (previousSubject != null && !previousSubject.isEmpty()) {
            sb.append(" ");
            for (Subject s : previousSubject) {
                sb.append(s.getSubjectCode());
            }
        }

        return sb.toString().trim();
    }


    public String toTable() {
        // 과목코드가 있는 경우에는 subjectCode만 저장
        if (subjectCode != null && !subjectCode.isEmpty()) {
            return subjectCode;
        } else {
            // 과목코드가 없는 경우 과목명, 학수번호, 학점 저장
            return subjectName + " " + courseCode + " " + credit;
        }
//        if (subjectDayTime.length < 2) {
//            return subjectName + " " + subjectDayTime[0].charAt(0) + " " + subjectDayTime[0].split(",")[1] + " " +
//                    subjectCode;
//        }
//        else {
//            return subjectName + " " + subjectDayTime[0].charAt(0) + "," + subjectDayTime[1].charAt(0) + " " + subjectDayTime[0].split(",")[1] + "," + subjectDayTime[1].split(",")[1] + " " +
//                    subjectCode;
//        }
    }

    @Override
    public String toString() {
        return "[" + subjectCode + "] " + subjectName + " (" + credit + "학점, " + category + ")";
    }


    /**
     * 주어진 학수번호와 그 과목의 학수번호가 같은지 확인.
     * @param CourseCode
     * @return
     */
    public boolean equalsUseCourseCode(String CourseCode){
        return this.courseCode.equals(CourseCode);
    }

    /**
     * 기본적인 equals = 학수번호가 같은지 여부를 확인해서 리턴합니다.
     * @param object
     * @return
     */
    @Override
    public boolean equals(Object object) {

        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Subject subject = (Subject) object;

        if (this.subjectCode == null || subject.subjectCode == null) {
            // 둘 중 하나만 subjectCode가 null이라면 false
//            if (this.subjectCode != null || subject.subjectCode != null) {
//                return false;
//            }
            // 둘 다 subjectCode가 null이면 과목명, 학수번호,  학점을 이용해 비교
            return Objects.equals(subjectName, subject.subjectName)
                    && Objects.equals(courseCode, subject.courseCode)
                    && Objects.equals(credit, subject.credit);
        } else {
            // 둘 다 subjectCode가 null이 아니면 subjectCode로 비교
            return Objects.equals(this.subjectCode, subject.subjectCode);
        }
    }


    @Override
    public int hashCode() {
        if (subjectCode != null) {
            // 과목코드가 있는 경우에는 과목코드로만 해시코드 생성
            return Objects.hash(subjectCode);
        } else {
            // 과목코드가 없는 경우에는 subjectName, courseCode, credit을 기반으로 해시코드 생성
            return Objects.hash(subjectName, courseCode, credit);
        }
    }
}