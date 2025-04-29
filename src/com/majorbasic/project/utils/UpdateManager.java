package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;
import com.majorbasic.project.datastructure.Graduation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class UpdateManager {

    /**
     * 전체 txt 파일에서 데이터를 읽어서 업데이트합니다.
     */

    public void updateInput(String input) {
        String[] tokens = input.split("\\s+");
        if(tokens.length != 1) {
            System.out.println("잘못된 update 명령 형식입니다.");
            return;
        }
        updateAll();
    }

    public void updateAll(){
        System.out.println("데이터 업데이트를 시도합니다.");
        updateSubjectManager("C:\\Users\\rlawh\\IdeaProjects\\asdf\\src\\com\\majorbasic\\project\\resources\\subject.txt");
        updateTimetableManager("C:\\Users\\rlawh\\IdeaProjects\\asdf\\src\\com\\majorbasic\\project\\resources\\timetable.txt");
        updateGraduate("C:\\Users\\rlawh\\IdeaProjects\\asdf\\src\\com\\majorbasic\\project\\resources\\graduate.txt");
        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    /**
     * subjectManager을 만들어서 리턴합니다.
     * @param filePath 파일주소 이름
     */
    public void updateSubjectManager(String filePath) {


        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            SubjectManager.subjectSets = new HashSet<>();
            SubjectManager.subjectList = new ArrayList<>();
            SubjectManager.subjectSets_fineNameUseCode = new HashMap<>();

            String line;

            while ((line = br.readLine()) != null) {
                String[] tuples = line.split(" ");

                String[] day = tuples[1].split(",");
                String[] time = tuples[2].split(",");
                if(day.length == 2){
                    if(time.length == 2){
                        day[0] = day[0] + ","  + time[0];
                        day[1] = day[1] + "," + time[1];
                    }
                    else{
                        day[0] = day[0] + ","  + time[0];
                        day[1] = day[1] + "," + time[0];
                    }
                }else{
                    day[0] = day[0] + ","  + time[0];
                }

                // Subject 객체 생성
                Subject subject = new Subject(
                        tuples[0],                      //subjectName
                        day,                 //subjectDayTime
                        tuples[3],                      //subjectCode
                        Integer.parseInt(tuples[4]),    //credit
                        tuples[5],                      //category
                        tuples[6],                      //courseCode
                        tuples[7],                      //lectureRoom
                        Arrays.asList(tuples[7].split(",")) //previousSubjectCodes
                );

                // subjectManager에 과목 추가
                SubjectManager.addSubjectToManager(subject);
            }
            br.close();
        } catch (Exception e) {
            System.out.println("updateSubjectManager 에러 : " + e.getMessage());
            System.out.println("과목 데이터 파일을 업데이트 할 수 없습니다.");
        }

    }

    public void updateTimetableManager(String filePath) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            TimetableManager.timetableSets = new HashSet<>();
            TimetableManager.timetableList = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {

                String[] tokens = line.split(" ");
                int year = Integer.parseInt(tokens[0]);
                int semester = Integer.parseInt(tokens[1]);
                ArrayList<Subject> subjects = new ArrayList<>();
                int subjectAmount = Integer.parseInt(br.readLine());
                for(int i = 0; i < subjectAmount; i++){
                    line = br.readLine();
                    String[] tuples = line.split(" ");
                    Subject subject = SubjectManager.findSubject(tuples);
                    if(subject == null) {
                        continue;
                    }
                    subjects.add(subject);
                }

                Timetable timetable = new Timetable(year, semester, subjects);
                TimetableManager.addTimeTabletoManager(timetable);
                System.out.println(timetable);
            }
            TimetableManager.presentTimetable = TimetableManager.timetableList.get(0);
            br.close();
        } catch (Exception e) {
            System.out.println("updateTimetableManager 에러 : " + e.getMessage());
            System.out.println("시간표 데이터 파일을 업데이트 할 수 없습니다.");
        }
    }

    public void updateGraduate(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line = br.readLine();
            int totalCreditsRequired = Integer.parseInt(line.trim());
            br.close();
            Graduation.resetGraduation(totalCreditsRequired);
        } catch (Exception e) {
            System.out.println("updateGraduate 에러 : " + e.getMessage());
            System.out.println("학점 데이터 파일을 업데이트 할 수 없습니다.");
        }
    }
}
