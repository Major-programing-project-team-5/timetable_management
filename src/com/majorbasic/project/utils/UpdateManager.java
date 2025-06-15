package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.Graduation;
import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;
import com.majorbasic.project.views.OnloadProgram;

import java.io.*;
import java.util.*;

public class UpdateManager {

    /**
     * 전체 txt 파일에서 데이터를 읽어서 업데이트합니다.
     */
    public void updateInput(String input) {
        String[] tokens = input.split("\\s+");
        if (tokens.length == 1 && tokens[0].equals("update")) {
            updateAll();
        } else if (tokens.length == 2 && tokens[1].equals("save")) {
            updateSave();
        } else {
            System.out.println("잘못된 update 명령 형식입니다.");
        }
    }

    public void updateAll() {
        updateSubjectManager("./resources/subject.txt");
        //유저일 때만 실행
        if(!UserManager.isAdmin)  updateTimetableManager(String.format("./resources/timetable/timetable_%s.txt", UserManager.currentUserID));
        updateGraduate("./resources/graduate.txt");

        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    public void updateSave() {
       if(UserManager.isAdmin){
           saveSubjectFile();
           saveGraduation();
           saveCurrentSemester();

       }else{
           saveTimetableFile();
           saveUserData();
       }



        System.out.println("데이터 저장을 완료하였습니다.");
    }

    public void saveSubjectFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/subject.txt"))) {
            for (Subject subject : SubjectManager.subjectList) {
                writer.write(subject.toSave());
                writer.newLine();
            }
        } catch (Exception e) {
            System.out.println("saveSubjectFile 에러 : " + e.getMessage());
            System.out.println("과목 데이터 파일을 저장할 수 없습니다.");
        }
    }



    public static void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./resources/userData.txt", false), 1024 * 1024 * 2)) {
            for (Map.Entry<String, String> entry : UserManager.userDataMap.entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue());
                writer.newLine();
            }
            System.out.println("userData 저장 성공");
        } catch (Exception e) {
            System.out.println("saveUserData 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 저장할 수 없습니다.");
        }
    }

    public void updateSubjectManager(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
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

                List<String> previousSubjects = null;
                if (tuples.length > 8) {
                    previousSubjects = Arrays.asList(tuples[8].split(","));
                }

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

                SubjectManager.addSubjectToManager(subject);
            }

            // 디버깅용 출력
            System.out.println(SubjectManager.getAllSubjectInfo());
        } catch (Exception e) {
            System.out.println("updateSubjectManager 에러 : " + e.getMessage());
            System.out.println("과목 데이터 파일을 업데이트할 수 없습니다.");
        }
    }

    public void updateTimetableManager(String filePath) {
        try {
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
                int year = Integer.parseInt(tokens[0]);
                int semester = Integer.parseInt(tokens[1]);

                int subjectAmount = Integer.parseInt(br.readLine());

                // 과목과 성적을 담을 Map 생성
                Map<Subject, String> subjectGradeMap = new HashMap<>();

                for (int i = 0; i < subjectAmount; i++) {
                    line = br.readLine();

                    // 성적 정보가 있는 경우 |로 split
                    String[] splitLine = line.split("\\|");
                    String subjectPart = splitLine[0].trim();
                    String grade = (splitLine.length > 1) ? splitLine[1].trim() : null;

                    // 과목 정보 파싱
                    String[] tuples = subjectPart.split(" ");
                    Subject subject;
                    if(tuples.length == 1){
                        subject = SubjectManager.findSubject(tuples[0]);
                    }else{
                        subject = SubjectManager.findSubject(tuples);
                    }


                    if (subject == null) {
                        System.out.println("과목을 찾을 수 없음: " + Arrays.toString(tuples));
                        continue;
                    }

                    subjectGradeMap.put(subject, grade);
                }

                // 성적이 포함된 시간표 생성자 사용
                Timetable timetable = new Timetable(year, semester, subjectGradeMap);
                TimetableManager.addTimeTabletoManager(timetable);
            }

            br.close();
        } catch (Exception e) {
            System.out.println("updateTimetableManager 에러 : " + e.getMessage());
            System.out.println("시간표 데이터 파일을 업데이트할 수 없습니다.");
        }
    }

    public void saveTimetableFile() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(String.format("./resources/timetable/timetable_%s.txt", UserManager.currentUserID)), 1024 * 1024 * 2)) {

            for (Timetable timetable : TimetableManager.timetableList) {
                writer.write(timetable.getYear() + " " + timetable.getSemester());
                writer.newLine();

                Map<Subject, String> subjects = timetable.getSubjects();
                if (subjects == null || subjects.isEmpty()) {
                    writer.write("0");
                    writer.newLine();
                } else {
                    writer.write(Integer.toString(subjects.size()));
                    writer.newLine();
                    for (Map.Entry<Subject, String> entry : subjects.entrySet()) {
                        Subject subject = entry.getKey();
                        String grade = entry.getValue();
                        // 성적이 null이 아닌 경우 함께 저장, 없으면 빈 문자열로 처리
                        writer.write(subject.toTable() + "|" + (grade != null ? grade : ""));
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("saveTimetableFile 에러 : " + e.getMessage());
            System.out.println("시간표 데이터 파일을 저장할 수 없습니다.");
        }
    }

    public void updateGraduate(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line = br.readLine();
            if(line == null) {
                System.out.println("졸업요건 파일이 비어있습니다.");
                return;
            }

            int totalCreditsRequired = Integer.parseInt(line.trim());
            Graduation.resetGraduation(totalCreditsRequired);

            while (true) {
                String area = br.readLine();
                if (area == null) break;
                area = area.trim();
                if (area.isEmpty()) continue;

                String creditLine = br.readLine();
                if (creditLine == null) break;
                int requiredCredit = Integer.parseInt(creditLine.trim());

                String subjectLine = br.readLine();
                if (subjectLine == null) subjectLine = "";

                List<String> subjectList = new ArrayList<>();
                if (!subjectLine.trim().isEmpty()) {
                    subjectList = Arrays.asList(subjectLine.trim().split("\\s*,\\s*"));
                }

                Graduation.CreditRequiredCreditEachArea.put(area, requiredCredit);
                Graduation.requiredSubject.put(area, subjectList);
            }

            System.out.println("졸업 요건 업데이트 완료.");
        } catch (Exception e) {
            System.out.println("updateGraduate 에러 : " + e.getMessage());
            System.out.println("졸업요건 데이터 파일을 업데이트할 수 없습니다.");
        }
    }
    public void saveGraduation() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("./resources/graduate.txt"))) {
            // 1. 총 이수 학점 출력
            bw.write(Integer.toString(Graduation.totalCreditsRequired));
            bw.newLine();

            // 2. 영역별로 순서대로 출력 (keySet 순서가 정해져 있지 않을 수 있으므로 정렬해서 저장하는 게 좋음)
            List<String> sortedAreas = new ArrayList<>(Graduation.CreditRequiredCreditEachArea.keySet());
            Collections.sort(sortedAreas); // 영역명 알파벳 순 정렬 (필요 시 다른 기준으로 변경 가능)

            for (String area : sortedAreas) {
                bw.write(area);
                bw.newLine();

                Integer credit = Graduation.CreditRequiredCreditEachArea.get(area);
                bw.write(credit.toString());
                bw.newLine();

                List<String> subjectList = Graduation.requiredSubject.get(area);
                if (subjectList == null || subjectList.isEmpty()) {
                    bw.write("");
                } else {
                    bw.write(String.join(",", subjectList));
                }
                bw.newLine();
            }

            System.out.println("졸업 요건 파일 저장 완료.");
        } catch (Exception e) {
            System.out.println("saveGraduation 에러 : " + e.getMessage());
            System.out.println("졸업요건 데이터 파일을 저장할 수 없습니다.");
        }
    }

    public static boolean deleteUserToDatabase(String id) {
        try {
            File file = new File(String.format("./resources/timetable/timetable_%s.txt", id));
            if (file.exists() && !file.delete()) {
                System.out.println("유저 데이터 파일을 삭제할 수 없음.");
                return false;
            }

            return true;
        } catch (Exception e) {
            System.out.println("deleteUserToDatabase 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 삭제하지 못했습니다.");
            return false;
        }
    }

    /**
     * ./resources/userData.txt 에서 유저 데이터를 로딩합니다.
     */
    public static void loadUserData() {
        try (BufferedReader br = new BufferedReader(new FileReader("./resources/userData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(" ");
                if (tokens.length == 2) {
                    UserManager.userDataMap.put(tokens[0], tokens[1]);
                }
            }
            br.close();
            System.out.println("유저 데이터 로딩 완료.");
        } catch (Exception e) {
            System.out.println("loadUserData 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 불러올 수 없습니다.");
        }
    }

    public void saveCurrentSemester() {
        String filePath = "./resources/current.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            bw.write(OnloadProgram.thisYear + " " + OnloadProgram.thisSemester);
            bw.newLine();
            System.out.println("현재 학기 정보가 current.txt에 저장되었습니다.");
        } catch (IOException e) {
            System.out.println("saveCurrentSemester 에러 : " + e.getMessage());
            System.out.println("현재 학기 정보를 저장할 수 없습니다.");
        }
    }
}
