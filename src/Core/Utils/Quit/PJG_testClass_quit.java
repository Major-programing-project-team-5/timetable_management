package Core.Utils.Quit;

import Core.DataStructure.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class PJG_testClass_quit {

    private final List<Subject> subjectBuffer;
    private final Timetable timetableBuffer;

    public PJG_testClass_quit() {
        this.subjectBuffer = subjectManager.subjectList;
        this.timetableBuffer = TimetableManager.timetableList;
    }

    public void quit() {
        uploadAllFileToDatabase();
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);  // 프로그램 완전 종료
    }

    private void uploadAllFileToDatabase() {
        pushSubjectFileToDatabase();
        pushTimetableFileToDatabase();
        // 나중에 graduate.txt 저장 추가 가능
    }

    private void pushSubjectFileToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("subject.txt"))) {
            for (Subject subject : subjectBuffer) {
                writer.write(subject.toString());
                writer.newLine();
            }
            System.out.println("subject.txt에 저장 완료");
        } catch (IOException e) {
            System.out.println("subject.txt 저장 실패: " + e.getMessage());
        }
    }

    private void pushTimetableFileToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("timetable.txt"))) {
            for (Subject subject : timetableBuffer.getSubjects()) {
                writer.write(subject.toString());
                writer.newLine();
            }
            System.out.println("timetable.txt에 저장 완료");
        } catch (IOException e) {
            System.out.println("timetable.txt 저장 실패: " + e.getMessage());
        }
    }
}