package Core.Utils.Add;

public class add_promptSet {
    public void print_add_timetable(int year, int semester){
        System.out.println("[ " + year + "학년 " + semester + "학기 시간표가 생성되었습니다. ]");
    }

    public void print_add_timetable_setcurrent(int year, int semester){
        System.out.println("[ 현재 시간표가 " + year + "학년 " + semester + "학기로 설정되었습니다. ]");
    }

    public void print_add_course_current(String coursename){
        System.out.println("[ 과목 '" + coursename + "'가 현재 시간표에 추가되었습니다.]");
    }

    public void print_add_course_database(String coursename){
        System.out.println("[ 과목 '" + coursename + "'가 데이터베이스에 등록되었습니다. ]");
    }
}
