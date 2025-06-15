package com.majorbasic.project.views;

import java.util.Scanner;

import com.majorbasic.project.utils.*;

public class OnloadProgram {
    private final Scanner sc = new Scanner(System.in);
    private final HelpPrompt help = new HelpPrompt();
    private final AddManager add = new AddManager();
    private final CalcManager calc = new CalcManager();
    private final VerifyManager verify = new VerifyManager();
    private final QuitManager quit = new QuitManager();
    private final RemoveManager remove = new RemoveManager();
    private final UpdateManager update = new UpdateManager();
    private final ChangeManager change = new ChangeManager();

    public static int thisYear; // 현재 학기
    public static int thisSemester; // 현재 시간

    public void run(){
        loadCurrentSemester();
        update.updateAll();
        if(UserManager.isAdmin){
            help_forAdmin();//관리자용

        }else{
            //유저용
            help_onStart();
        }

        getInput();
    }
    public void help_forAdmin(){
        System.out.println("환영합니다.");
        //change 총 명령어
        //remove subject database
        //remove userData
        //add subject database
        //change graduation

    }
    private void loadCurrentSemester() {
        String filePath = "./resources/current.txt";

        try (Scanner fileScanner = new Scanner(new java.io.File(filePath))) {
            if (!fileScanner.hasNextInt()) {
                throw new IllegalArgumentException("current.txt에 유효한 연도가 없습니다.");
            }
            int year = fileScanner.nextInt();

            if (!fileScanner.hasNextInt()) {
                throw new IllegalArgumentException("current.txt에 유효한 학기가 없습니다.");
            }
            int semester = fileScanner.nextInt();

            if (semester < 1 || semester > 4) {
                throw new IllegalArgumentException("학기는 1~4 사이의 값이어야 합니다.");
            }

            thisYear = year;
            thisSemester = semester;
            System.out.println("==============================");
            System.out.println("현재 학기 정보:");
            if (semester == 3) {
                System.out.printf("%d년 여름 계절학기%n", year);
            } else if (semester == 4) {
                System.out.printf("%d년 겨울 계절학기%n", year);
            } else {
                System.out.printf("%d년 %d학기%n", year, semester);
            }
            System.out.println("==============================");

        } catch (Exception e) {
            System.out.println("현재 학기 정보를 불러오는 데 실패했습니다: " + e.getMessage());
            // 기본값 설정 또는 프로그램 종료 등 선택적 대응
            return;
        }
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

            if (args.length == 0 || args[0].isEmpty()) {
                System.out.println("올바르지 않은 명령어 입니다. help 목록을 불러옵니다.");
                help.helpNoarg();
                continue;
            }

            switch (args[0].toLowerCase()) {
                case "help":
                case "도움말":
                case "도움":
                case "명령어":
                case "목록":
                case "?":
                    help.helpMain(ans);
                    break;
                case "quit":
                case "종료":
                    if (args.length > 1) {
                        System.out.println("올바른 인자가 아닙니다.");
                        break;
                    }
                    quit.quit();
                    return;
                case "add":
                case "추가":
                    add.addMain(ans);
                    break;
                case "verify":
                case "확인":
                case "불러오기":
                case "표시":
                    verify.verifyMain(ans);
                    break;
                case "calc":
                case "계산":
                case "학점":
                    calc.calcInput(ans);
                    break;
                case "remove":
                case "삭제":
                case "제거":
                    remove.removeMain(ans);
                    break;
                case "update":
                case "갱신":
                case "업데이트":
                    update.updateInput(ans);
                    break;
                case "change":     // 새로 추가
                    change.changeMain(ans);
                    break;
                default:
                    System.out.println("올바르지 않은 명령어 입니다. help 목록을 불러옵니다.");
                    help.helpNoarg();
                    break;
            }
        }
    }
}
