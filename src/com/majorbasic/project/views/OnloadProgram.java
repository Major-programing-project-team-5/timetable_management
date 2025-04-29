package com.majorbasic.project.views;

import java.util.Scanner;

import com.majorbasic.project.utils.AddManager;
import com.majorbasic.project.utils.CalcManager;
import com.majorbasic.project.utils.QuitManager;
import com.majorbasic.project.utils.UpdateManager;
import com.majorbasic.project.utils.VerifyManager;
import com.majorbasic.project.utils.RemoveManager;

public class OnloadProgram {
    private final Scanner sc = new Scanner(System.in);
    private final HelpPrompt help = new HelpPrompt();
    private final AddManager add = new AddManager();
    private final CalcManager calc = new CalcManager();
    private final QuitManager quit = new QuitManager();
    private final UpdateManager update = new UpdateManager();

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
    }

    public void getInput () {
        while(true){
            System.out.print("> ");
            String ans = sc.nextLine();
            String[] args = ans.split("\\s+");

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
                    if (args.length > 1) {
                        System.out.println("올바른 인자가 아닙니다.");
                        break;
                    }
                    quit.quit();
                    return;
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
                    VerifyManager.verifyMain(ans);
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
                    RemoveManager.removeMain(ans);
                    break;
                case "update":
                case "Update":
                case "UPDATE":
                case "갱신":
                case "업데이트":
                    update.updateInput(ans);
                    break;
                default:
                    System.out.println("올바르지 않은 명령어 입니다. help 목록을 불러옵니다.");
                    help.helpNoarg();
            }
        }
    }
}
