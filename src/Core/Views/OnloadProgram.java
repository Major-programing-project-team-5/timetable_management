package Core.Views;

import Core.DataStructure.Graduation;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.Update.UpdateManager;

import java.util.Scanner;

public class OnloadProgram {

    public void run(){
        update();
        help_onStart();

        String input;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            // 사용자 입력 받기
            System.out.print("> ");
            String userInput = scanner.nextLine();
            String[] inputTuples = userInput.trim().split("\\s+");
            // 'quit' 입력 시 루프 종료
            if (userInput.equals("quit")) {
                break; // 루프 종료
            }else if(inputTuples[0].equals("add")){
                add_prompt();
            }

            // 'quit'이 아니면 그 외의 동작 처리
            System.out.println("입력된 명령어: " + userInput);
            // 여기서 추가적으로 다른 명령어를 처리하거나, 로직을 추가할 수 있음
        }
        Quit();
    }

    private void add_prompt() {

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


    private void Quit() {
        //Quit 메서드 넣어서 종료하는 것 다 구현.
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("모든 변경 사항이 저장되었습니다.");
        System.out.println("프로그램을 종료합니다.");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━"); //이거 구분하는 선 통일하면 깔끔할듯

    }



    public void update(){
        System.out.println("데이터 업데이트를 시도합니다.");
        UpdateManager updateManager = new UpdateManager();
        updateManager.updateSubjectManager("src/resources/subject.txt");
        updateManager.updateTimetableManager("src/resources/timetable.txt");
        updateManager.updateGraduate("src/resources/graduate.txt");
        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    //help 쪽
    private void help_onStart(){
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
    }

}
