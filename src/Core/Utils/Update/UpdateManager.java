package Core.Utils.Update;

import Core.DataStructure.Subject;
import Core.DataStructure.subjectManager;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.Graduation;
import Core.Utils.findSubjectClass;

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
        String test = "C:/Users/rlawh/IdeaProjects/timetable/";
        System.out.println("데이터 업데이트를 시도합니다.");
        updateSubjectManager(test + "src/resources/subject.txt");
        updateTimetableManager(test + "src/resources/timetable.txt");
        updateGraduate(test + "src/resources/graduate.txt");
        System.out.println("데이터 업데이트를 완료하였습니다.");
    }

    /**
     * subjectManager을 만들어서 리턴합니다.
     * @param filePath
     * @return
     */
    public void updateSubjectManager(String filePath) {


        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            subjectManager.subjectSets = new HashSet<>();
            subjectManager.subjectList = new ArrayList<>();
            subjectManager.subjectSets_fineNameUseCode = new HashMap<>();

            String line;

            while ((line = br.readLine()) != null) {
                String[] tuples = line.split(" ");

                String subjectName = tuples[0];
                String[] subjectDayTime = null;
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
                subjectDayTime = day;

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
                subjectManager.addSubjectToManager(subject);
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
                    Subject subject = findSubjectClass.findSubject(tuples);
                    if(subject == null) {
                        continue;
                    }
                    subjects.add(subject);
                }

                Timetable timetable = new Timetable(year, semester, subjects);
                TimetableManager.addTimeTabletoManager(timetable);
                System.out.println(timetable.toString());
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
            System.out.println("학점 데이터 파일을 업데이트 할 수 없습니다.");
        }
    }
}
