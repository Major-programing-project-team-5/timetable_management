public class PJH_test {
    public class test {
        //명령어마다 입력/출력 결과 경우 전부 나눠서 각각 하나의 함수로 작성
        //일단은 다 나눠서 해놓는 쪽으로 진행중이지만 나중에 조건문 같은거로 합치면 더 깔끔할듯
        public void print_onstart(){
            //'='는 한 줄에 30개
            System.out.println("==============================");
            System.out.println("시간표 관리 프로그램 시작");
            System.out.println("==============================");
            System.out.println("사용 가능한 명령어 목록입니다: ");
            System.out.println();
            System.out.println("help : 도움말 관련 기능을 제공합니다.");
            System.out.println("예) help, help add");
            System.out.println("quit : 프로그램을 종료합니다.");
            System.out.println("add : 시간표 관련 데이터를 추가합니다.");
            System.out.println("예) 시간표 생성, 과목 추가 등을 수행할 수 있습니다.");
            System.out.println("verify : 지난 학기 시간표, 현재 시간표를 확인합니다.");
            System.out.println("calc : 잔여 학점, 현재 학점, 과거 학점을 계산합니다.");
            System.out.println("remove : 시간표 또는 과목 데이터를 삭제합니다.");
            System.out.println("update : 과목 데이터베이스를 최신 상태로 불러옵니다.");
            System.out.println();
            System.out.println("명령어에 대한 자세한 설명은 'help [명령어]'를 입력해 확인하세요.");
            System.out.println("예) help add");
            System.out.println();
            System.out.println("무엇을 도와드릴까요?");
            System.out.print("timetable > "); //timetable은 왜 있는 거지?
        }

        public void print_help(){
            System.out.println("사용 가능한 명령어 목록입니다:");
            System.out.println("help : 도움말 관련 기능을 제공합니다.");
            System.out.println("예) help, help add");
            System.out.println("quit : 프로그램을 종료합니다.");
            System.out.println("add : 시간표 관련 데이터를 추가합니다.");
            System.out.println("예) 시간표 생성, 과목 추가 등을 수행할 수 있습니다.");
            System.out.println("verify : 지난 학기 시간표, 현재 시간표를 확인합니다.");
            System.out.println("calc : 잔여 학점, 현재 학점, 과거 학점을 계산합니다.");
            System.out.println("remove : 시간표 또는 과목 데이터를 삭제합니다.");
            System.out.println("update : 과목 데이터베이스를 최신 상태로 불러옵니다.");
            System.out.println();
            System.out.println("명령어에 대한 자세한 설명은 'help [명령어]' 를 입력해 확인하세요.");
            System.out.println("예) help add");
            System.out.println();
            System.out.println("무엇을 도와드릴까요?");
            System.out.println("timetable >");
        }

        public void print_help_help(){

        }

        public void print_help_quit(){

        }

        public void print_help_add(){
            System.out.println("[ 도움말 - add 명령어 ]");
            System.out.println("명령어: add (동일 명령어: 추가)");
            System.out.println("설명:");
            System.out.println("시간표를 생성하거나, 기존 시간표에 과목을 추가하거나, 과목 자체를 데이터베이스에 등록하는 명령어입니다.");
            System.out.println("─────────────────────────────────────");
            System.out.println("사용 방식:");
            System.out.println("1. 시간표 추가:");
            System.out.println("- add <학년> <학기>");
            System.out.println("예) add 2 1");
            System.out.println("→ 2학년 1학기 시간표 생성");
            System.out.println();
            System.out.println("- add current <학년> <학기>");
            System.out.println("예) add current 3 2");
            System.out.println("→ 3학년 2학기 시간표 생성 + 현재 시간표로 설정");
            System.out.println("2. 기존 시간표에 과목 추가:");
            System.out.println("- add current 과목명 요일 시간대");
            System.out.println("예) add current \"운영체제\" 화 14:00~16:00");
            System.out.println("3. 새 과목 등록 (과목 데이터베이스 추가):");
            System.out.println("- add 과목 <구성 인자 일괄 입력>");
            System.out.println("예)");
            System.out.println("add 과목 \"운영체제\" CS101-01 3 전공 화 14:00~16:00 CS101 3공학관201 없음");
            System.out.println("구성 인자 정의:");
            System.out.println("- 과목명 : 1자 이상의 문자열");
            System.out.println("- 과목 번호 : 예) CS101-01 (과목 코드 + 분반)");
            System.out.println("- 학점 : 1~9 사이의 정수");
            System.out.println("영역 : 예) 전공, 교양, 핵심 등 (졸업 요건 계산에 사용)");
            System.out.println("요일 : 월, 화, 수, 목, 금, 토, 일 중 하나");
            System.out.println("시간대 : HH:MM~HH:MM 형식 (예: 14:00~16:00)");
            System.out.println("학수 번호 : 예) CS101 (과목 식별용 코드)");
            System.out.println("강의실 : 예) 3공학관201");
            System.out.println("선수 과목 : 과목명 또는 ‘없음’ 명시");
            System.out.println("─────────────────────────────────────");
            System.out.println("유의사항:");
            System.out.println("- 과목을 시간표에 추가할 때, 과목 데이터베이스에 존재하는 과목이어야 합니다.");
            System.out.println("- 새 과목 등록 시에는 **모든 인자**가 정확히 제공되어야 하며, 누락 시 오류가 발생합니다.");
            System.out.println();
            System.out.println("비정상 처리:");
            System.out.println("- 인자가 부족하거나 잘못된 형식일 경우, 오류 메시지 출력 후 작업을 수행하지 않습니다.");
            System.out.println("- 존재하지 않는 시간표를 대상으로 할 경우: [존재하지 않는 시간표입니다.]");
            System.out.println("정상 결과:");
            System.out.println("- 시간표 추가: [2학년 1학기 시간표가 생성되었습니다.]");
            System.out.println("- 과목 추가: [운영체제 과목이 현재 시간표에 추가되었습니다.]");
            System.out.println("- 과목 등록: [운영체제 과목이 데이터베이스에 등록되었습니다.]");
            System.out.println("[ 다음 페이지로 이동하려면 '+', 이전 페이지는 '-' 를 입력하세요. 종료하려면 'exit' 또는 'q' 입력 ]");
            System.out.println(">");
        }

        public void print_help_verify(){

        }

        public void print_help_calc(){

        }

        public void print_help_remove(){

        }

        public void print_help_update(){

        }

        public void print_quit(){
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
            System.out.println("모든 변경 사항이 저장되었습니다.");
            System.out.println("프로그램을 종료합니다.");
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━"); //이거 구분하는 선 통일하면 깔끔할듯
        }

        public void print_quit_error(){
            System.out.println("> quit");
            System.out.println("[오류가 발생하여 현재 값을 데이터베이스에 저장하고 종료할 수 없습니다. 초기 파일로 복구한 뒤 종료합니다.]");
        }

        public void print_add(){

        }

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

        public void print_verify(){

        }

        public void print_calc(){

        }

        public void print_remove(){

        }

        public void print_update(){

        }
    }

}
