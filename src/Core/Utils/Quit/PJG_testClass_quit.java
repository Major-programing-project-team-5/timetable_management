package Core.Utils.Quit;

//to-do
public class PJG_testClass_quit {

    public void quit(){
        uploadAllFileTodataBase();
    }

    private void uploadAllFileTodataBase() {
        pushSubjectFileTodatabase();
        pushTimetableFileTodatebase();

        System.out.println("프로그램을 종료합니다");
    }

    private void pushSubjectFileTodatabase() {
        System.out.println("서브젝트 파일에 변경사항 업로드 완료 - 구현필요");
    }

    private void pushTimetableFileTodatebase() {
        System.out.println("타임테이블 파일에 변경사항 업로드 완료 - 구현필요");

    }
}
