package Core.Utils.Update;

import Core.DataStructure.Subject;
import Core.DataStructure.subjectManager;
import Core.DataStructure.Timetable;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.Graduation;
import Core.Utils.findSubjectClass;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.HashMap;

public class UpdateManager {

    /**
     * 전체 txt 파일에서 데이터를 읽어서 업데이트합니다.
     */

    public void updateAll(){

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
                    previousSubjects = List.of(tuples[7].split(","));
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
                    subjects.add(subject);
                }

                Timetable timetable = new Timetable(year, semester, subjects);
                TimetableManager.addTimeTabletoManager(timetable);
                System.out.println(timetable.toString());
            }
            br.close();
        } catch (Exception e) {
            System.out.println("updateTimetableManager 에러 : " + e.getMessage());
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
        }
    }
}
