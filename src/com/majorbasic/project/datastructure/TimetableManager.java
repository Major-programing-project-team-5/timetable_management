package com.majorbasic.project.datastructure;

import com.majorbasic.project.exception.AddException;
import com.majorbasic.project.views.OnloadProgram;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class TimetableManager {
    public static HashSet<Timetable> timetableSets;
    public static List<Timetable> timetableList;
    public static Timetable presentTimetable;
    /**
     * TimeTableManager에 시간표를 추가하는 메소드입니다.
     * @param timetable 시간표에 추가할 시간표입니다
     */
    public static void addTimeTabletoManager(Timetable timetable){
        try{
            if (timetableSets.contains(timetable)) {
                throw new AddException("TimetableManager - addTimeTabletoManager : 시간표가 이미 존재함.");
            }
            else{
                // 계절학기 아닌 시간표들을 year, semester 순으로 정렬
                List<Timetable> nonSeasonTimetables = timetableSets.stream()
                        .filter(t -> !t.isSeason())
                        .sorted(Comparator.comparingInt(Timetable::getYear)
                                .thenComparingInt(Timetable::getSemester))
                        .toList();

                // 8번째 시간표가 존재하면 분기점으로 사용
                if (nonSeasonTimetables.size() >= 8) {
                    Timetable threshold = nonSeasonTimetables.get(7); // index 7 = 8번째
                    boolean isOverThreshold = false;

                    if (!timetable.isSeason()) {
                        if (timetable.getYear() > threshold.getYear()) {
                            isOverThreshold = true;
                        } else if (timetable.getYear() == threshold.getYear() &&
                                timetable.getSemester() > threshold.getSemester()) {
                            isOverThreshold = true;
                        }
                    }

                    if (isOverThreshold) {
                        System.out.printf("※ 초과학기 시간표입니다. (기준: %d년 %d학기)\n",
                                threshold.getYear(), threshold.getSemester());
                    }
                }

                timetableSets.add(timetable);
                timetableList.add(timetable);
                if(timetable.getSemester() == OnloadProgram.thisSemester && timetable.getYear() == OnloadProgram.thisYear){
                    presentTimetable = timetable;
                    System.out.println("현재 시간표 : " + timetable.toString());
                }
            }
        }catch (AddException e){
            System.out.println(e.getMessage());
        }
    }
    public static Timetable getTimetable(int year, int semester) {
        Timetable temptimeTable = new Timetable(year, semester);
            for (Timetable temp : timetableList) {
                if (temp.equals(temptimeTable)) {
                    return temp;
                }
            }
            return null;
    }

    /**
     * 타임테이블의 학년과 학기의 값이 유효한지 검사
     * @param year 학년
     * @param semester 학기
     * @return 학년과 학기의 값이 유효한지 여부
     */
    public static boolean isTimetableCorrect(String year, String semester) {
        // 입력값이 정수로 변환 가능한지 확인
        try {
            int yearInt = Integer.parseInt(year);
            int semesterInt = Integer.parseInt(semester);
            
            // 변환된 정수 값이 유효한 범위인지 확인
            if(yearInt > 2025 || yearInt < 1930 || semesterInt > 4 || semesterInt < 1) {
                System.out.println("학년 또는 학기의 숫자가 범위를 넘어섰습니다");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            System.out.println("학년 또는 학기가 유효한 숫자 형식이 아닙니다");
            return false;
        }
    }

    /**
     * @param year 년도
     * @param semester 학기
     * @param subjectInfo 과목 정보.
     * @param grade 성적
     */
    public static void editSubject(int year, int semester, String[] subjectInfo, String grade) {

        //만일 이 시간표가 현재 시간표였을 경우
        if(year == OnloadProgram.thisYear && semester == OnloadProgram.thisSemester){
            System.out.println("현재 시간표에는 성적을 설정할 수 없습니다!");
            return;
        }
        Timetable targetTimetable = getTimetable(year, semester);

        if (targetTimetable == null) {
            System.out.println(year + "년" + semester + "학기 시간표를 찾을 수 없습니다.");
            return;
        }

        //timetable의 addGrade 작동시키기
        if(subjectInfo.length == 1){
            targetTimetable.setGrade(subjectInfo[0], grade);
        }else{
            targetTimetable.setGrade(subjectInfo, grade);
        }
        System.out.println("성적이 추가되었습니다");
    }

    /**
     * timetable에 과목을 추가해줍니다.
     * @param year
     * @param semester
     * @param subjectInfo
     */
    public static void addSubjectToTimetable(int year, int semester, String[] subjectInfo){
        Timetable targetTimetable = getTimetable(year, semester);

        if (targetTimetable == null) {
            System.out.println(year + "년" + semester + "학기 시간표를 찾을 수 없습니다.");
            return;
        }


        //timetable의 addSubject 작동시키기
        if(subjectInfo.length == 1){
            Subject temp_sub = SubjectManager.findSubject(subjectInfo[0]);
            targetTimetable.addSubject(temp_sub);
        }else{
            Subject temp_sub = SubjectManager.findSubject(subjectInfo);
            targetTimetable.addSubject(temp_sub);
        }

    }
    //성적 포함.
    public static void addSubjectToTimetable(int year, int semester, String[] subjectInfo, String grade){
        Timetable targetTimetable = getTimetable(year, semester);
        if(year == OnloadProgram.thisYear && semester == OnloadProgram.thisSemester){
            System.out.println("현재 시간표에는 성적을 설정할 수 없습니다!");
            return;
        }
        if (targetTimetable == null) {
            System.out.println(year + "년" + semester + "학기 시간표를 찾을 수 없습니다.");
            return;
        }

        //timetable의 addSubject 작동시키기
        if(subjectInfo.length == 1){
            Subject temp_sub = SubjectManager.findSubject(subjectInfo[0]);
            targetTimetable.addSubject(temp_sub, grade);
        }else{
            Subject temp_sub = SubjectManager.findSubject(subjectInfo);
            targetTimetable.addSubject(temp_sub, grade);
        }

    }

}
