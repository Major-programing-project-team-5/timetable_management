package Core.Views;

import Core.DataStructure.Graduation;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.Update.UpdateManager;

public class OnloadProgram {

    public void run(){
        update();
        help_onStart();
        String input;
        while(true){
            if(getInput()) continue;
            else break;
        }
        Quit();
    }

    private void Quit() {
        //Quit 메서드 넣어서 종료하는 것 다 구현.
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("모든 변경 사항이 저장되었습니다.");
        System.out.println("프로그램을 종료합니다.");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━"); //이거 구분하는 선 통일하면 깔끔할듯

    }

    private boolean getInput() {
    }

    public void update(){
        UpdateManager updateManager = new UpdateManager();
        updateManager.updateSubjectManager("src/resources/subject.txt");
        updateManager.updateTimetableManager("src/resources/timetable.txt");
        updateManager.updateGraduate("src/resources/graduate.txt");
    }
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
        System.out.print("> ");

    }


}
