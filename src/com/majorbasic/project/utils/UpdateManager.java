package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Graduation;
import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;

import java.io.BufferedReader;
import java.io.File;
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
        updateSubjectManager("src/resources/subject.txt");
        updateTimetableManager("src/resources/timetable.txt");
        updateGraduate("src/resources/graduate.txt");
        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    /**
     * subjectManager을 만들어서 리턴합니다.
     * @param filePath 저장파일 위치
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

                String subjectName = tuples[0];
                String[] subjectDayTime = Util.dayTimeArr(tuples);
                String subjectCode = tuples[3];
                int credit = Integer.parseInt(tuples[4]);
                String category = tuples[5];
                String courseCode = tuples[6];
                String lectureRoom = tuples[7];

                // 선수 과목 처리
                List<String> previousSubjects = null;
                if (tuples.length > 8) {
                    previousSubjects = Arrays.asList(tuples[7].split(","));
                }

                // Subject 객체 생성
                Subject subject = new Subject(
                        subjectName,
                        subjectDayTime,
                        subjectCode,
                        credit,
                        category,
                        courseCode,
                        lectureRoom,
                        previousSubjects
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
            // 파일 존재 여부 확인
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("파일이 존재하지 않습니다: " + filePath);
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(filePath));
            TimetableManager.timetableSets = new HashSet<>();
            TimetableManager.timetableList = new ArrayList<>();

            String line;
            while ((line = br.readLine()) != null) {

                String[] tokens = line.split(" ");
                System.out.println("연도: " + tokens[0] + ", 학기: " + tokens[1]);  // 디버깅 로그 추가
                int year = Integer.parseInt(tokens[0]);
                int semester = Integer.parseInt(tokens[1]);
                ArrayList<Subject> subjects = new ArrayList<>();
                int subjectAmount = Integer.parseInt(br.readLine());
                for(int i = 0; i < subjectAmount; i++){
                    line = br.readLine();
                    String[] tuples = line.split(" ");
                    Subject subject = SubjectManager.findSubject(tuples);
                    if(subject == null) {
                        System.out.println("과목을 찾을 수 없음: " + Arrays.toString(tuples));
                        continue;
                    }
                    subjects.add(subject);
                }

                Timetable timetable = new Timetable(year, semester, subjects);
                TimetableManager.addTimeTabletoManager(timetable);
                System.out.println(timetable);
            }
            br.close();
        } catch (Exception e) {
//            System.out.println("updateTimetableManager 에러 : " + e.getMessage());
            e.printStackTrace();
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
