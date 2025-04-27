package Core.Utils.Add;

import Core.DataStructure.Subject;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.findSubjectClass;
import java.util.Arrays;

public class add_promptSet {
    public void Select_add_prompt(String[] tokens) {
        try {
            if (isNumeric(tokens[1]) && isNumeric(tokens[2])) {
                // 1. 지정 학기 시간표 생성 (학년, 학기)
                int year = Integer.parseInt(tokens[1]);
                int semester = Integer.parseInt(tokens[2]);
                print_add_timetable(year, semester);
            } else if (tokens[1].equals("current") && isNumeric(tokens[2]) && isNumeric(tokens[3])) {
                // 2. 현재 시간표 생성 및 설정
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                print_add_timetable_setcurrent(year, semester);
            } else if (tokens[1].equals("current") && tokens.length > 2) {
                // 3. 시간표에 과목 추가
                String[] subjectInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                print_add_course_current(subjectInfo);
            } else if (tokens[1].equals("subject")) {
                // 4. 데이터베이스에 과목 추가
                String[] lectureInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
                add_utilitySet.addSubjectToDatabase(lectureInfo);
            } else if (tokens[1].equals("past") && tokens.length > 2) {
                // 5. 과거 시간표 추가(명령에 past가 붙어도 일반 테이블 생성처럼 작동)
                int year = Integer.parseInt(tokens[2]);
                int semester = Integer.parseInt(tokens[3]);
                add_utilitySet.createTimetable(year, semester);
            } else {
                System.out.println("잘못된 add 명령 형식입니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void print_add_timetable(int year, int semester) {
        Timetable temp = new Timetable(year, semester);
        TimetableManager.addTimeTabletoManager(temp);
        System.out.println("[ " + year + "학년 " + semester + "학기 시간표가 생성되었습니다. ]");
    }

    public void print_add_timetable_setcurrent(int year, int semester) {
        Timetable temp = new Timetable(year, semester);
        if (!TimetableManager.timetableSets.contains(temp)) {
            TimetableManager.addTimeTabletoManager(temp);
        } else {
            for (var i : TimetableManager.timetableList) {
                if (i.equals(temp)) {
                    temp = i;
                    break;
                }
            }
        }
        add_utilitySet.currentTable = temp;
        System.out.println("[ 현재 시간표가 " + year + "학년 " + semester + "학기로 설정되었습니다. ]");
    }

    public void print_add_course_current(String[] tuples) {
        Subject temp = findSubjectClass.findSubject(tuples);
        if (temp != null) {
            subjectManager.addSubjectToManager(temp);
            System.out.println("[ 과목 '" + temp.toString() + "'가 현재 시간표에 추가되었습니다.]");
        } else {
            System.out.println("잘못된 과목 튜플 입력입니다.");
        }
    }

    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}



