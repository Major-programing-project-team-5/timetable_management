package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Graduation;
import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;

import java.io.*;
import java.util.*;

public class UpdateManager {

    /**
     * 전체 txt 파일에서 데이터를 읽어서 업데이트합니다.
     */

    public void updateInput(String input) {
        String[] tokens = input.split("\\s+");
        if(tokens.length == 1 && tokens[0].equals("update")) {
            updateAll();
        } else if(tokens.length == 2 && tokens[1].equals("save")) {
            updateSave();
        } else {
            System.out.println("잘못된 update 명령 형식입니다.");
        }
    }

    public void updateAll(){
        updateSubjectManager("./resources/subject.txt");
        updateTimetableManager(String.format("./resources/timetable/%s/timetable.txt", UserManager.currentUserID));
        updateGraduate("./resources/graduate.txt");
        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    public void updateSave(){
        saveSubjectFile();
        saveTimetableFile();
        System.out.println("데이터 저장을 완료하였습니다.");
    }

    public void saveSubjectFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/subject.txt"));
            for(Subject subject : SubjectManager.subjectList) {
                writer.write(subject.toSave());
                writer.newLine();
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("saveSubjectFile 에러 : " + e.getMessage());
            System.out.println("과목 데이터 파일을 저장 할 수 없습니다.");
        }
    }

    public void saveTimetableFile() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("./resources/timetable/timetable_%s.txt", UserManager.currentUserID), false), 1024 * 1024 * 2);
            for(Timetable timetable : TimetableManager.timetableList) {
                writer.write(timetable.getYear() + " " + timetable.getSemester());
                writer.newLine();
                writer.write(Integer.toString(timetable.getSubjects().size()));
                writer.newLine();
                for(Subject subject : timetable.getSubjects()) {
                    writer.write(subject.toTable());
                    writer.newLine();
                }
            }
            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("saveTimetableFile 에러 : " + e.getMessage());
            System.out.println("시간표 데이터 파일을 저장할 수 없습니다.");
        }
    }

    public static void saveUserData() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(String.format("./resources/userdata/userdata_%s.txt", UserManager.currentUserID), false), 1024 * 1024 * 2);

            for(Map.Entry<String, String> entry : UserManager.userDataMap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }

            writer.flush();
            writer.close();
        } catch (Exception e) {
            System.out.println("saveUserData 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 저장할 수 없습니다.");
        }
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
                String[] subjectDayTime = new String[]{tuples[1], tuples[2]};
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
            ///---------디버깅용 코드-----------
            System.out.println(SubjectManager.getAllSubjectInfo());
            ///-------------------------
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
            System.out.println("졸업요건 데이터 파일을 업데이트 할 수 없습니다.");
        }
    }

    public static boolean deleteUserToDatabase(String id) {
        try {
            File file = new File(String.format("./src/com/majorbasic/project/resources/timetable/%s/timetable.txt", id));
            File folder = new File(String.format("./src/com/majorbasic/project/resources/timetable/%s", id));
            if (file.exists()) {
                if(file.delete())  {
                    System.out.println("유저 데이터 파일을 삭제하였습니다.");
                }
                else {
                    System.out.println("유저 데이터 파일을 삭제할 수 없음.");
                    return false;
                }
            }
            if (folder.exists()) {
                if(folder.delete()) {
                    System.out.println("유저 데이터 폴더을 삭제하였습니다.");
                    return true;
                }
                else {
                    System.out.println("유저 데이터 폴더을 삭제할 수 없음.");
                    return false;
                }
            }
        } catch (Exception e) {
            System.out.println("updateUserToDatabase 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 업데이트할 수 없습니다.");
            return false;
        }

        return false;
    }
}
