// 터미널 크기 120 * 30 가정한 크기
package Core.Views;
import java.util.Scanner;

public class helpPrompt {
    private final Scanner sc = new Scanner(System.in);

    public void helpMain(String input) {
        String[] inputList = input.split("[ \t\n\r\f\u000B]");
        clear();

        if(inputList.length == 1) {
            helpNoarg();
            return;
        }

        switch(inputList[1]) {
            case "help":
                helpHelp();
                break;
            case "quit":
                helpQuit();
                break;
            case "add":
                helpAdd();
                break;
            case "verify":
                helpVerify();
                break;
            case "calc":
                helpCalc();
                break;
            case "remove":
                helpRemove();
                break;
            case "update":
                helpUpdate();
                break;
            default:
                helpHelp();
        }
    }
    public void clear() {
        try {
            String os = System.getProperty("os.name");

            if (os.contains("Windows")) {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process start = pb.inheritIO().start();
                start.waitFor();
            } else {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process start = pb.inheritIO().start();

                start.waitFor();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void helpNoarg() {
        System.out.println("사용 가능한 명령어 목록입니다:\n");
        System.out.println("help: 도움말 관련 기능을 제공합니다.");
        System.out.println("quit: 프로그램을 종료합니다.");
        System.out.println("add: 시간표 관련 데이터를 추가합니다.");
        System.out.println("verify: 시간표를 확인합니다.");
        System.out.println("calc: 학점을 계산합니다.");
        System.out.println("remove: 시간표 또는 과목 데이터를 삭제합니다.");
        System.out.println("update: 과목 데이터베이스를 최신 상태로 불러옵니다.");
        System.out.println("\n명령어에 대한 자세한 설명은 `help [명령어]` 를 입력해 확인하세요");
        System.out.println("예) help help");
    }
    private void helpHelp() {
        System.out.println("[ 도움말 - help 명령어 ]");
        System.out.println("명령어: help (동일 명령어: 도움말, 도움, 명령어, 목록, ?)");
        System.out.println("설명:");
        System.out.println("이 프로그램에서 사용하는 명령어들의 사용법을 알려주는 명령어입니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 명령어 사용법 설명");
        System.out.println("help");
        System.out.println("예) help");
        System.out.println("-> 모든 명령어에 대한 도움말 출력");
        System.out.println("\nhelp <명령어>");
        System.out.println("예) help help");
        System.out.println("-> help에 대한 명령어 도움말 출력");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 명령어는 존재하는 명령어 중에 입력해야 합니다. 만약 올바르지 않은 명령어를 입력하면 help help를 입력한 것과 같은 결과가 출력됩니다.");
        System.out.println("- 인자의 명령어는 표준형태, 즉 소문자 영어 형태여야 합니다. 나머지 동일한 명령어는 지원되지 않습니다.");
    }
    private void helpQuit() {
        System.out.println("[ 도움말 - quit 명령어 ]");
        System.out.println("명령어: quit (동일 명령어: 종료)");
        System.out.println("설명: 프로그램을 종료합니다. 만일 데이터 변경사항이 있으면 변경 사항을 데이터베이스에 반영합니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 프로그램 종료");
        System.out.println("quit");
        System.out.println("예) quit");
        System.out.println("-> 변경 사항 저장후 프로그램 종료");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 만약 프로그램의 데이터에 오류가 생겼을 경우 초기 파일로 복구 후 종료하게 됩니다.");
    }
    private void helpAdd() {
        System.out.println("[ 도움말 - add 명령어 ]");
        System.out.println("명령어: add (동일 명령어: 추가)");
        System.out.println("설명: 시간표 또는 시간표 내부에 과목을 추가하거나 과목 데이터를 추가할 수 있습니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 시간표 추가 및 지정");
        System.out.println("add <학년> <학기>");
        System.out.println("예) add 2 1");
        System.out.println("-> 2학년 1학기 시간표 생성");
        System.out.println("\nadd current <학년> <학기>");
        System.out.println("예) add current 2 1");
        System.out.println("-> 2학년 1학기 시간표를 생성하고 현재 시간표로 설정");
        System.out.println("add past <학년> <학기>");
        System.out.println("예) add past 2 1");
        System.out.println("-> 2학년 1학기 시간표가 존재하면 그 시간표를 현재 시간표로 설정");
        System.out.println("\n2. 기존 시간표에 과목 추가");
        System.out.println("add current <과목 튜플>");
        System.out.println("예) add current 운영체제 화 14:00~16:00");
        System.out.println("-> 현재 시간표에 운영체제 화요일 14:00~16:00 수업 등록");
        System.out.println("\nadd past <학년> <학기> <과목 튜플>\n\n\n\n\n");

        String ans;
        while(true) {
            System.out.println("다음 페이지로 이동하려면 '+', 이전 페이지는 '-', 종료하려면 'exit' 또는 'q'를 입력하세요 > ");
            ans = sc.next();

            if(ans.equals("+") || ans.equals("+1") || ans.equals("2")) {
                clear();
                helpAdd2();
                break;
            } else if(ans.equals("q") || ans.equals("exit")) {
                clear();
                break;
            } else {
                System.out.println("페이지 번호를 입력하거나 현재 페이지거나 해당 페이지가 존재하지 않습니다");
            }
        }
    }
    private void helpAdd2() {
        System.out.println("예) add past 2 1 운영체제 화 14:00~16:00");
        System.out.println("-> 2학년 1학기 시간표를 현재 시간표로 설정하고 운영체제 화요일 14:00~16:00 수업 등록");
        System.out.println("\n3. 데이터베이스에 과목 등록");
        System.out.println("add subject <강의정보>");
        System.out.println("예) add subject 과목 운영체제 CS101-01 3 전공 화 14:00~16:00 CS101 3공학관201");
        System.out.println("-> 운영체제 과목을 데이터베이스에 등록");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 과목을 시간표에 추가할 때, 그 과목이 데이터베이스에 존재하여야 합니다.");
        System.out.println("새 과목을 시간표에 추가할 때는, 모든 인자가 정확히 제공되어야 하며, 누락 시 오류가 발생합니다.\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        String ans;
        while(true) {
            System.out.println("다음 페이지로 이동하려면 '+', 이전 페이지는 '-', 종료하려면 'exit' 또는 'q'를 입력하세요 > ");
            ans = sc.next();

            if(ans.equals("-") || ans.equals("-1") || ans.equals("1")) {
                clear();
                helpAdd();
                break;
            } else if(ans.equals("q") || ans.equals("exit")) {
                clear();
                break;
            } else {
                System.out.println("페이지 번호를 입력하거나 현재 페이지거나 해당 페이지가 존재하지 않습니다");
            }
        }
    }
    private void helpVerify() {
        System.out.println("[ 도움말 - verify 명령어 ]");
        System.out.println("명령어: verify (동일 명령어: 확인, 불러오기, 표시)");
        System.out.println("설명: 현재 학기 또는 지난 학기의 시간표를 출력하거나 과목의 정보를 확인합니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 현재 학기의 시간표 출력");
        System.out.println("verify");
        System.out.println("예) verify");
        System.out.println("-> 현재 학기의 시간표 출력");
        System.out.println("\n2. 지난 학기의 특정 시간표 출력");
        System.out.println("verify <학년> <학기>");
        System.out.println("예) verify 2 1");
        System.out.println("-> 지난 학기의 특정 시간표 출력");
        System.out.println("\n3. 해당 과목이 존재하는지 확인");
        System.out.println("verify subject <과목 튜플>");
        System.out.println("예) verify subject 운영체제 화 14:00~16:00");
        System.out.println("-> 운영체제가 데이터베이스에 존재하는지 확인하고 존재하면 과목의 정보 출력");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 시간표가 존재하지 않는 학년 또는 학기를 입력하면 해당 학기가 존재하지 않는다고 출력합니다.");
        System.out.println("- 존재하지 않는 과목을 입력하게 되면 해당 과목이 존재하지 않는다고 출력합니다.");
    }
    private void helpCalc() {
        System.out.println("[ 도움말 - calc 명령어 ]");
        System.out.println("명령어: calc (동일 명령어: 계산, 학점)");
        System.out.println("설명: 총 학점, 특정 학기 학점 등 학점을 계산 후 출력합니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 총 학점 계산");
        System.out.println("calc total");
        System.out.println("예) calc total");
        System.out.println("-> 전체 학기의 학점 계산");
        System.out.println("\n2. 특정 학기 학점 계산 후 출력");
        System.out.println("calc term <학년> <학기>");
        System.out.println("예) calc term 2 1");
        System.out.println("-> 2학년 1학기의 학점 계산 후 출력");
        System.out.println("\n3. 잔여 학점 계산");
        System.out.println("calc remain");
        System.out.println("예) calc remain");
        System.out.println("-> 남은 학기의 학점 계산 후 출력");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 만약 해당 학년 또는 학기가 존재하지 않는다면 해당 학기가 존재하지 않는다고 출력합니다.");
        System.out.println("- 만약 모종의 이유로 학점 게산에 실패하게 되면 계산에 실패했다고 출력합니다.");
    }

    private void helpRemove() {
        System.out.println("[ 도움말 - remove 명령어 ]");
        System.out.println("명령어: remove (동일 명령어: 삭제, 제거)");
        System.out.println("설명: 시간표에서 과목 또는 시간표, 데이터베이스에서 과목을 삭제합니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 현재 학기의 과목 삭제");
        System.out.println("remove subject <과목 튜플>");
        System.out.println("예) remove subject 운영체제 화 14:00~16:00");
        System.out.println("-> 현재 시간표에서 운영체제 과목 삭제");
        System.out.println("\nremove subject all");
        System.out.println("예) remove subject all");
        System.out.println("-> 현재 시간표에서 모든 과목 삭제");
        System.out.println("\n2. 특정 학기의 과목 삭제");
        System.out.println("remove <학년> <학기> subject <과목 튜플>");
        System.out.println("예) remove 2 1 subject 운영체제 화 14:00~16:00");
        System.out.println("-> 2학년 1학기 시간표에서 운영체제 과목 삭제");
        System.out.println("\nremove <학년> <학기> subject all");
        System.out.println("예) remove 2 1subject all");
        System.out.println("-> 2학년 1학기 시간표에서 모든 과목 삭제\n\n\n\n\n\n");

        String ans;
        while(true) {
            System.out.println("페이지 번호를 입력하거나 다음 페이지로 이동하려면 '+', 이전 페이지는 '-', 종료하려면 'exit' 또는 'q'를 입력하세요 > ");
            ans = sc.next();

            if(ans.equals("+") || ans.equals("+1") || ans.equals("2")) {
                clear();
                helpRemove2();
                break;
            } else if(ans.equals("q") || ans.equals("exit")) {
                clear();
                break;
            } else {
                System.out.println("현재 페이지거나 해당 페이지가 존재하지 않습니다");
            }
        }
    }

    private void helpRemove2() {
        System.out.println("\n3. 특정 학기의 시간표 삭제");
        System.out.println("remove <학년> <학기> timetable");
        System.out.println("예) remove 2 1 timetable");
        System.out.println("-> 2학년 1학기 시간표를 삭제");
        System.out.println("\n4. 데이터베이스에서 과목 삭제");
        System.out.println("remove subject <과목 튜플> database");
        System.out.println("예) remove subject 운영체제 화 14:00~16:00 database");
        System.out.println("-> 운영체제 과목을 모든 시간표에서 삭제 후 데이터베이스에서 삭제");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 해당하는 과목이나 시간표가 없다면 각각 과목이 없습니다, 시간표가 없습니다를 출력합니다.");
        System.out.println("- 만일 모종의 이유로 삭제에 실패하게 되면 삭제에 실패했다고 출력합니다.\n\n\n\n\n\n\n\n\n\n\n\n\n\n");

        String ans;
        while(true) {
            System.out.println("페이지 번호를 입력하거나 다음 페이지로 이동하려면 '+', 이전 페이지는 '-', 종료하려면 'exit' 또는 'q'를 입력하세요 > ");
            ans = sc.next();

            if(ans.equals("-") || ans.equals("-1") || ans.equals("1")) {
                clear();
                helpRemove();
                break;
            } else if(ans.equals("q") || ans.equals("exit")) {
                clear();
                break;
            } else {
                System.out.println("현재 페이지거나 해당 페이지가 존재하지 않습니다");
            }
        }
    }

    private void helpUpdate() {
        System.out.println("[ 도움말 - update 명령어 ]");
        System.out.println("명령어: update (동일 명령어: 업데이트, 갱신)");
        System.out.println("설명: 전체 수업 데이터 파일을 불러옵니다.");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("사용 방식");
        System.out.println("1. 데이터 불러오기");
        System.out.println("update");
        System.out.println("예) update");
        System.out.println("-> 전체 수업 데이터 파일을 불러옴");
        System.out.println("________________________________________________________________________________________________________________________");
        System.out.println("유의사항");
        System.out.println("- 데이터에 중대한 오류가 발생하거나 불러올 수 없는 경우, 오류가 발생해 데이터를 불러오는데 실패했다는 메시지를 출력합니다.");
    }
}
