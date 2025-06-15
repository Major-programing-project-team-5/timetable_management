package com.majorbasic.project.utils;


import com.majorbasic.project.datastructure.*;

import java.util.*;

//테스트해봐야 할 명령어들
//관리자용
//add subject <과목 정보> = 과목 정보 출력하기.

//사용자용
//add <년도> <학기> -> 1번       ----완
//add current timetable -> 2번   ----완
//add current <과목 번호> -> 3번     ----완
//add <년도> <학기> <과목 번호> -> 4번   ----완
//add <년도> <학기> <과목 튜플> <성적> -> 4번  ----완
//add grade <년도> <학기> <과목튜플> <성적> -> 신규 6번
// 과목 튜플: 과목명, 학수번호, 학점 or 과목코드
// 과목 정보: 과목명, 요일, 시간, 과목번호, 학점, 이수구분, 학수번호, 강의실, 선수과목 과목코드

public class AddManager{
    public void addMain(String args) {
        String[] tokens = args.split("\\s+");
        Select_add_prompt(tokens);
    }

    public void Select_add_prompt(String[] tokens) {
        try {
            if (tokens.length < 3) {
                System.out.println("잘못된 add 명령 형식입니다.");
            }
            //관리자용 명령어
            else if (tokens[1].equals("subject") && (tokens.length == 10 || tokens.length == 11)) {
                //add subject -> 과목 데이터베이스에 과목 추가
                if(UserManager.isAdmin){
                    String[] lectureInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                    add_course_database(lectureInfo);
                }else{
                    System.out.println("권한이 부족합니다.");
                }

            }
            //사용자용.
            else if ( tokens.length == 3 && isNumeric(tokens[1]) && isNumeric(tokens[2])  ) {
                // 1. 지정 학기 시간표 생성 (년도, 학기)
                //add <년도> <학기>
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                if (!(TimetableManager.isTimetableCorrect(tokens[1], tokens[2]))) {
                    return;
                }
                add_timetable(year, semester);
            } else if (tokens[1].equals("current") && tokens[2].equals("timetable") && tokens.length == 3) {
                // 2. 현재 시간표 생성
//                int year = Integer.parseInt(tokens[2]);
//                int semester = Integer.parseInt(tokens[3]);
//                print_add_timetable_setcurrent(year, semester);
                add_current_timetable();
            } else if (tokens[1].equals("current") && isNumeric(tokens[2]) && tokens.length == 3) {
                // 3. current 시간표에 과목 추가
                add_course_current(tokens[2]);
            } else if (isNumeric(tokens[1]) && isNumeric(tokens[2]) && (tokens.length == 4 || tokens.length == 6)) {
                // 4. add 학년 학기 <과목 정보> 로 입력시 해당 시간표에 과목 추가
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                String[] lectureInfo = Arrays.copyOfRange(tokens, 3, tokens.length);
                add_course_timetable(year, semester, lectureInfo);
            } else if (tokens[1].equals("grade") && isNumeric(tokens[2]) && isNumeric(tokens[3])){
                // 6. 성적 추가
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                String[] subjectInfo = Arrays.copyOfRange(tokens, 4, tokens.length - 1);
                //토큰의 맨 마지막은 학점일 것.
                add_grade_timetable(year, semester, subjectInfo, tokens[tokens.length-1]);

            }
//            else if (tokens[1].equals("past") && isNumeric(tokens[2]) && isNumeric(tokens[3]) && tokens.length == 4) {
//                int year = Integer.parseInt(tokens[2]);
//                int semester = Integer.parseInt(tokens[3]);
//                Timetable tmp = TimetableManager.getTimetable(year, semester);
//                if (tmp == null) {
//                    System.out.println("대상 시간표가 존재하지 않습니다.");
//                    return;
//                }
//                TimetableManager.presentTimetable = TimetableManager.getTimetable(year, semester);
//                System.out.println("[ 현재 시간표가 " + year + "학년 " + semester + "학기로 설정되었습니다. ]");
//            }
            else {
                System.out.println("잘못된 add 명령 형식입니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다 " + e.toString());
        }
    }



    public void add_timetable(int year, int semester) {
        Timetable temp;
        if(semester == 3 || semester == 4){
           temp = new Timetable(year, semester, true);
        }else{
            temp = new Timetable(year, semester);
        }

        if (TimetableManager.timetableSets.contains(temp)) {
            System.out.println("이미 존재하는 시간표입니다");
        } else {
            TimetableManager.addTimeTabletoManager(temp);

            String semesterName;
            switch (semester) {
                case 1:
                    semesterName = "1학기";
                    break;
                case 2:
                    semesterName = "2학기";
                    break;
                case 3:
                    semesterName = "여름 계절학기";
                    break;
                case 4:
                    semesterName = "겨울 계절학기";
                    break;
                default:
                    semesterName = semester + "학기";
            }

            System.out.println("[ " + year + "학년 " + semesterName + " 시간표가 생성되었습니다. ]");
        }
    }

    public void add_current_timetable() {
        Timetable temp = TimetableManager.presentTimetable;    // 나중에 current로 설정된 year와 semester를 받아오도록 수정
        if (!TimetableManager.timetableSets.contains(temp)) {
            TimetableManager.addTimeTabletoManager(temp);
            System.out.println("[ 현재 학기 시간표가 생성되었습니다. ]");
        } else {
//            for (Timetable i : TimetableManager.timetableList) {
//                if (i.equals(temp)) {
//                    temp = i;
//                    break;
//                }
//            }
            System.out.println("[이미 현재 시간표가 존재합니다.]");
        }

    }

    public void add_course_current(String subjectCode) {
        Subject temp = SubjectManager.findSubject(subjectCode);
        if (temp == null) {
            System.out.println("잘못된 과목코드 입력입니다.");
        } else if (isTimeConflicted(temp, TimetableManager.presentTimetable)) {
            System.out.println("겹치는 강의가 있습니다.");
        } else {
            TimetableManager.presentTimetable.addSubject(temp);
            System.out.println("[ 과목 '" + temp.toString() + "'가 현재 시간표에 추가되었습니다.]");
        }
    }

    public void add_course_database(String[] lectureInfo) {
        if (lectureInfo.length < 8) {
            System.out.println("[과목 정보의 요소가 부족합니다.]");
            return;
        }

        List<String> previousSubjectCode = new ArrayList<>();
        if (lectureInfo.length > 8) {
            previousSubjectCode = Arrays.asList(lectureInfo).subList(8, lectureInfo.length);
            for(int i = 0; i < previousSubjectCode.size(); i++) {
                if(SubjectManager.findSubject(previousSubjectCode.get(i)) == null) {
                    System.out.println("과목코드가 올바르지 않습니다.");
                    return;
                }
            }
        }

        String[] lectureDate = new String[]{lectureInfo[1], lectureInfo[2]};

        Subject subject = new Subject(
                lectureInfo[0],              // 과목명
                lectureDate,                 // 요일,시간 쌍 배열
                lectureInfo[3],              // 과목코드
                Integer.parseInt(lectureInfo[4]), // 학점
                lectureInfo[5],              // 이수구분
                lectureInfo[6],              // 학수번호
                lectureInfo[7],              // 강의실
                previousSubjectCode.isEmpty() ? null : previousSubjectCode
        );
        System.out.println(subject);

        boolean success = SubjectManager.addSubjectToManager(subject);
        if (success) {
            System.out.println("[과목이 데이터베이스에 추가되었습니다.]");
        } else {
            System.out.println("[과목 추가에 실패했습니다.]");
        }
    }

    public void add_course_timetable(int year, int semester, String[] lectureInfo) {
        Subject temp;
        if(lectureInfo.length == 1){
           temp = SubjectManager.findSubject(lectureInfo[0]);
        }else{
           temp = SubjectManager.findSubject(lectureInfo);
        }
        if (temp == null) {
            System.out.println("[해당하는 과목을 찾을 수 없습니다.]");
            return;
        }

        Timetable table = TimetableManager.getTimetable(year, semester);
        if (table == null) {
            System.out.println("[" + year + "년도 " + semester + "학기 시간표가 없습니다.]");
        } else if (isTimeConflicted(temp, table)) {
            System.out.println("겹치는 강의가 있습니다.");
        } else {
            table.addSubject(temp);
            System.out.println("[ 과목 '" + temp.toString() + "'가 시간표에 추가되었습니다.]");
        }

//        if(table == null){
//            System.out.println("존재하지 않는 시간표입니다.");
//            return;
//        } else if(temp == null){
//            System.out.println("잘못된 과목 튜플 입력입니다.");
//            return;
//        } else if (isTimeConflicted(temp, table)) {
//            System.out.println("겹치는 강의가 있습니다.");
//        } else {
//            table.addSubject(temp);
//            System.out.println("[ 과목 '" + temp.toString() + "'가 시간표에 추가되었습니다.]");
//        }

    }

    public void add_grade_timetable(int year, int semester, String[] subjectInfo, String grade) {
        TimetableManager.editSubject(year, semester, subjectInfo, grade);

    }

    public static boolean isTimeConflicted(Subject subject, Timetable timetable) {
        if (timetable == null) {
            return false;
        }
        // 2. 시간 중복 체크(현재 시간표 기준)
        for (Subject existingSubject : timetable.getSubjects().keySet()) {
            for (DayTime newDT : subject.getSubjectDayTimes()) {
                for (DayTime existingDT : existingSubject.getSubjectDayTimes()) {
                    if (newDT.getDay().equals(existingDT.getDay()) &&
                            DayTime.isOverlapping(newDT, existingDT)) {
                        return true;
                    }
                }
            }
        }
        //3. 총 학점 체쿠ㅡ.
        int currentTotalCredits = 0;
        for (Subject s : timetable.getSubjects().keySet()) {
            currentTotalCredits += s.getCredit();  // 과목의 학점 합산
        }
        int maxCredit = timetable.getMax_credit();
        int newTotal = currentTotalCredits + subject.getCredit();

        if (newTotal > maxCredit) {
            System.out.printf("학점 초과: 현재 총 학점 %d + 추가 과목 학점 %d = %d학점, 최대 허용 학점 %d학점 초과%n",
                    currentTotalCredits, subject.getCredit(), newTotal, maxCredit);
            return true;  // 최대 학점 초과 시 충돌로 간주
        }
        //4. 선수과목 미수강 체크
        List<Subject> prereqSubjects = subject.getPreviousSubject();
        if (prereqSubjects != null) {
            for (Subject prereq : prereqSubjects) {
                String prereqCode = prereq.getCourseCode();
                boolean taken = false;

                // 모든 시간표에서 해당 선수과목 수강 여부 탐색
                for (Timetable t : TimetableManager.timetableList) {
                    for (Subject s : t.getSubjects().keySet()) {
                        if (s.getCourseCode().equals(prereqCode)) {
                            String grade = t.getSubjects().get(s);
                            if (grade != null && !grade.equalsIgnoreCase("F") && !grade.equalsIgnoreCase("N")) {
                                taken = true;
                                break;
                            }
                        }
                    }
                    if (taken) break;
                }

                if (!taken) {
                    System.out.println("선수과목 [" + prereq.getSubjectName() + "(" + prereqCode + ")]을(를) 수강하지 않았습니다.");
                    return true;  // 선수과목 미수강 시 충돌(true)
                }
            }
        }
        //마지막 : 재수강 여부 판정.

        Set<String> retakableGrades = new HashSet<>(Arrays.asList("C+", "C", "D+", "D", "F", "N"));

        for (Timetable t : TimetableManager.timetableList) {
            if (t.equals(TimetableManager.presentTimetable)) continue;
            for (Subject s : t.getSubjects().keySet()) {
                if (s.getCourseCode().equals(subject.getCourseCode())) {
                    String grade = t.getSubjects().get(s);

                    if (grade == null) {
                        System.out.printf("%d년 %s%n", t.getYear(),
                                t.getSemester() == 3 ? "여름 계절학기" :
                                        t.getSemester() == 4 ? "겨울 계절학기" :
                                                t.getSemester() + "학기");
                        System.out.println(s + " 과목에 성적이 없습니다. 성적을 입력해야 재수강 여부를 판단할 수 있습니다.");
                        return true;
                    }

                    if (retakableGrades.contains(grade.toUpperCase())) {
                        System.out.println("재수강 대상 과목이므로 이전 시간표에서 드랍합니다: " + s);
                        t.getSubjects().remove(s);
                        t.getRetakeMap().remove(s);
                    } else {
                        System.out.println("재수강 불가: 기존 성적 = " + grade + " / 과목: " + s);
                        return true;
                    }
                }
            }
        }



        return false;
    }


    private static boolean isOverlapping(String timeRange1, String timeRange2) {
        int start1 = toMinutes(timeRange1.split("~")[0]);
        int end1 = toMinutes(timeRange1.split("~")[1]);

        int start2 = toMinutes(timeRange2.split("~")[0]);
        int end2 = toMinutes(timeRange2.split("~")[1]);

        return start1 < end2 && start2 < end1;
    }

    private static int toMinutes(String time) {
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        return hour * 60 + minute;
    }


    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}






