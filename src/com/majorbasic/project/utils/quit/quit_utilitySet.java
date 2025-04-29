package com.majorbasic.project.utils.quit;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;
import com.majorbasic.project.datastructure.subjectManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class quit_utilitySet {
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
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/subject.txt"))) {
            for (Subject subject : subjectManager.subjectList) {
                writer.write(subject.toSave());
                writer.newLine();
            }
            System.out.println("subject.txt에 저장 완료");
            writer.flush();
        } catch (IOException e) {
            System.out.println("subject.txt 저장 실패: " + e.getMessage());
        }
    }

    private void pushTimetableFileToDatabase() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./data/timetable.txt"))) {
            for (Timetable timetable : TimetableManager.timetableList) {
                writer.write(timetable.getYear() + " " + timetable.getSemester());
                writer.newLine();
                writer.write(Integer.toString(timetable.getSubjects().size()));
                writer.newLine();
                for (Subject subject : timetable.getSubjects()) {
                    writer.write(subject.toTable());
                    writer.newLine();
                }
            }
            System.out.println("timetable.txt에 저장 완료");
            writer.flush();
        } catch (IOException e) {
            System.out.println("timetable.txt 저장 실패: " + e.getMessage());
        }
    }
}