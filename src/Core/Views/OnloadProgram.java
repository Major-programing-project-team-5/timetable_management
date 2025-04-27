package Core.Views;

import java.util.Scanner;

import Core.Utils.Add.add_promptSet;
import Core.Utils.Calc.calc_utilitySet;
import Core.Utils.Quit.quit_utilitySet;
import Core.Utils.Update.UpdateManager;
import Core.Utils.Verify.verify_utilitySet;
import Core.Utils.Remove.remove_utilitySet;

public class OnloadProgram {
    private final Scanner sc = new Scanner(System.in);
    private helpPrompt help = new helpPrompt();
    private add_promptSet add = new add_promptSet();
    private calc_utilitySet calc = new calc_utilitySet();
    private quit_utilitySet quit = new quit_utilitySet();
    private UpdateManager update = new UpdateManager();

    public void run(){
        update.updateAll();
        help_onStart();
        getInput();
    }

    public void help_onStart(){

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

    public void getInput () {
        sc.skip("\r\n");
        String ans = sc.nextLine();
        String[] args = ans.split("[ \t\n\r\f\u000B]");

        switch (args[0]) {
            case "help":
            case "Help":
            case "HELP":
            case "도움말":
            case "도움":
            case "명령어":
            case "목록":
            case "?":
                help.helpMain(ans);
                break;
            case "quit":
            case "Quit":
            case "QUIT":
            case "종료":
                quit.quit();
                break;
            case "add":
            case "Add":
            case "ADD":
            case "추가":
                add.addMain(ans);
                break;
            case "verify":
            case "Verify":
            case "VERIFY":
            case "확인":
            case "불러오기":
            case "표시":
                verify_utilitySet.verifyMain(ans);
                break;
            case "calc":
            case "Calc":
            case "CALC":
            case "계산":
            case "학점":
                calc.calcInput(ans);
                break;
            case "remove":
            case "Remove":
            case "REMOVE":
            case "삭제":
            case "제거":
                remove_utilitySet.removeMain(ans);
                break;
            case "update":
            case "Update":
            case "UPDATE":
            case "갱신":
            case "업데이트":
                update.updateInput(ans);
                break;
        }
    }

}
