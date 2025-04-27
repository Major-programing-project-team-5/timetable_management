package Core.Utils.Remove;

import Core.DataStructure.*;
import Core.Utils.findSubjectClass;
import Core.Exception.removeException;

import java.io.*;
import java.util.Arrays;

public class removeUtilitySet {
    public static void removeMain(String input) {
        String[] tokens = input.split("\\s+");

        if (tokens[1].equals("subject") || tokens[2].equals("all")) {
            removeAllSubjectToTimetable();
        } else if(tokens[1].equals("subject") || tokens[-1].equals("database")) {
            Subject token = findSubjectClass.findSubject(Arrays.copyOfRange(tokens, 2, tokens.length - 1));
            removeSubjectToDatabase(token);
        } else if(tokens[1].matches("\\d+") && tokens[2].matches("\\d+")) {
            Timetable timetable = TimetableManager.getTimetable(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

            if (tokens[3].equals("subject") && tokens[4].equals("all")) {
                removeAllSubjectToTimetable(timetable);
            }
            else if(tokens[3].equals("subject") && !tokens[4].equals("all")) {
                Subject token = findSubjectClass.findSubject(Arrays.copyOfRange(tokens, 4, tokens.length - 1));
                removeSubjectToTimetable(token, timetable);
            }
            else if(tokens[3].equals("timetable")) {
                removeTimetableToManager(timetable);
            }
        }
    }
    /**
     * 시간표를 매니저에서 삭제하는 메소드입니다.
     * @param timetable 삭제할 시간표
     * @return 삭제 성공 여부
     */
    public static boolean removeTimetableToManager(Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new removeException("removeUtilitySet - removeTimetableToManager : 존재하지 않는 시간표입니다.");
            }
            TimetableManager.timetableSets.remove(timetable);
            TimetableManager.timetableList.remove(timetable);
            return true;
        } catch (removeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 특정 시간표에서 과목 하나를 삭제하는 메소드입니다.
     * @param subject 삭제할 과목
     * @param timetable 과목이 들어있는 시간표
     * @return 삭제 성공 여부
     */
    public static boolean removeSubjectToTimetable(Subject subject, Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new removeException("removeUtilitySet - removeSubjectToTimetable : 해당 시간표가 존재하지 않습니다.");
            }
            for (Timetable t : TimetableManager.timetableList) {
                if (t.equals(timetable)) {
                    if (t.getSubjects().contains(subject)) {
                        t.getSubjects().remove(subject);
                        return true;
                    } else {
                        throw new removeException("removeUtilitySet - removeSubjectToTimetable : 시간표에 해당 과목이 없습니다.");
                    }
                }
            }
            return false;
        } catch (removeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 특정 시간표에서 모든 과목을 삭제하는 메소드입니다.
     * @param timetable 과목을 모두 삭제할 시간표
     * @return 삭제 성공 여부
     */
    public static boolean removeAllSubjectToTimetable(Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new removeException("removeUtilitySet - removeAllSubjectToTimetable : 존재하지 않는 시간표입니다.");
            }
            for (Timetable t : TimetableManager.timetableList) {
                if (t.equals(timetable)) {
                    t.getSubjects().clear();
                    return true;
                }
            }
            return false;
        } catch (removeException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    /**
     * 데이터베이스(파일)에서 과목을 삭제하는 메소드입니다.
     * @param subject 삭제할 과목
     * @return 삭제 성공 여부
     */
    public static boolean removeSubjectToDatabase(Subject subject) {
        try {
            if (!subjectManager.subjectSets.contains(subject)) {
                throw new removeException("removeUtilitySet - removeSubjectToDatabase : 존재하지 않는 과목입니다.");
            }

            // 메모리 상에서도 삭제
            subjectManager.subjectSets.remove(subject);
            subjectManager.subjectList.remove(subject);
            subjectManager.subjectSets_fineNameUseCode.remove(subject.getSubjectCode());

            // 파일에서도 삭제
            File inputFile = new File("subject.txt");
            File tempFile = new File("subject_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.contains(subject.getSubjectCode())) {
                    writer.write(line + System.lineSeparator());
                }
            }
            writer.close();
            reader.close();

            if (!inputFile.delete()) {
                throw new IOException("원본 파일 삭제 실패.");
            }
            if (!tempFile.renameTo(inputFile)) {
                throw new IOException("임시 파일을 원본 파일로 교체 실패.");
            }

            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

}
