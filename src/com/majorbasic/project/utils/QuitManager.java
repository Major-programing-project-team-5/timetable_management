package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class QuitManager {
    public void quit() {
        UpdateManager updateManager = new UpdateManager();
        updateManager.updateSave();
//        uploadAllFileToDatabase();
        System.out.println("프로그램을 종료합니다.");
        System.exit(0);  // 프로그램 완전 종료
    }
}