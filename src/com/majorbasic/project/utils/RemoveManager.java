package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.SubjectManager;
import com.majorbasic.project.datastructure.Subject;
import com.majorbasic.project.datastructure.Timetable;
import com.majorbasic.project.datastructure.TimetableManager;
import com.majorbasic.project.exception.RemoveException;

import java.io.*;
import java.util.Arrays;

/*
수정판
//이 주석은 코드의 기능 파악을 돕기 위한 주석 + a
//@@@@@이 주석은 코드 수정 내역
*/
public class RemoveManager {
    public void removeMain(String input) {
        try {
            String[] tokens = input.split("\\s+");

            if (tokens.length < 3) {
                System.out.println("인자가 올바르지 않습니다.");
                return;
            }

            /*
            if (tokens[1].equals("subject") && tokens[2].equals("all")) {
                removeAllSubjectToTimetable(TimetableManager.presentTimetable);
            } else if (tokens[1].equals("subject") && tokens[tokens.length - 1].equals("database")) {
                Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 2, tokens.length - 1));
                removeSubjectToDatabase(token);
            } else if (tokens[1].equals("subject") && tokens.length > 5) {
                Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 2, tokens.length - 1));
                removeSubjectToTimetable(token, TimetableManager.presentTimetable);
            }
            */ //@@@@@subject 인자 검사를 상위 if 문으로 통일하여 아래의 코드로 수정함

            if (tokens[1].equals("subject")) { //remove 다음의 인자가 subject 인 경우
                if (tokens[2].equals("all")) { //subject 다음의 인자가 all 인 경우
                    removeAllSubjectToTimetable(TimetableManager.presentTimetable);
                } else if (tokens[tokens.length - 1].equals("database")) { //subject 다음, 마지막 인자가 database 인 경우
                    Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 2, tokens.length - 1));
                    removeSubjectToDatabase(token);
                } else { //나머지 경우(subject 다음, 일반적인 과목 튜플이 들어온 경우), 이 경우에서의 잘못된 입력은 일단 findSubject 에서 다루게 함
                    //@@@@@원래 tokens[1].equals("subject") && tokens.length > 5라는 조건이었으나, > 5라는 조건이 불명확해 보여 subject 인자를 다루는 if문 내에서 else 로 바꿈
                    Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 2, tokens.length - 1));
                    removeSubjectToTimetable(token, TimetableManager.presentTimetable);
                }
            } else if (tokens[1].matches("\\d+") && tokens[2].matches("\\d")) { //remove 다음의 인자가 연도, 학기인 경우
                Timetable timetable = TimetableManager.getTimetable(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));

                if(Integer.parseInt(tokens[1]) < 1931 || Integer.parseInt((tokens[1])) > 2025){
                    System.out.println("올바른 연도 입력이 아닙니다.");
                    return;
                }
                if(Integer.parseInt(tokens[2]) < 1 || Integer.parseInt(tokens[2]) > 4){
                    System.out.println("올바른 학기 입력이 아닙니다.");
                    return;
                } // (일단 임시, 나중에 Timetable 쪽에 기능 이전해도 좋을 것 같음)연도/학기 입력이 유효한지 체크

                /*
                if (tokens[3].equals("subject") && tokens[4].equals("all")) {
                    removeAllSubjectToTimetable(timetable);
                } else if (tokens[3].equals("subject")) {
                    Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 4, tokens.length - 1));
                    removeSubjectToTimetable(token, timetable);
                }
                */ //@@@@@subject 인자 검사를 상위 if 문으로 통일하여 아래의 코드로 수정함

                if (tokens[3].equals("subject")) { //연도, 학기 다음의 인자가 subject 인 경우
                    if (tokens[4].equals("all")) { //subject all 의 경우
                        removeAllSubjectToTimetable(timetable);
                    } else { //subject <과목 튜플> 의 경우
                        Subject token = SubjectManager.findSubject(Arrays.copyOfRange(tokens, 4, tokens.length - 1));
                        removeSubjectToTimetable(token, timetable);
                    }
                } else if (tokens[3].equals("timetable")) { //연도, 학기 다음의 인자가 timetable 인 경우
                    removeTimetableToManager(timetable);
                } else { //연도, 학기 다음의 인자가 비정상적인 경우
                    System.out.println("인자가 올바르지 않습니다.");
                }
            } else { //remove 다음의 인자가 비정상적인 경우
                System.out.println("인자가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("명령어 처리 중 오류가 발생했습니다 " + e);
        }
    }
    /**
     * 시간표를 매니저에서 삭제하는 메소드입니다.
     * @param timetable 삭제할 시간표
     */
    public void removeTimetableToManager(Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new RemoveException("removeUtilitySet - removeTimetableToManager : 존재하지 않는 시간표입니다.");
            }
            TimetableManager.timetableSets.remove(timetable);
            TimetableManager.timetableList.remove(timetable);
            System.out.println("시간표를 지웠습니다.");
        } catch (RemoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 특정 시간표에서 과목 하나를 삭제하는 메소드입니다.
     * @param subject   삭제할 과목
     * @param timetable 과목이 들어있는 시간표
     */
    public void removeSubjectToTimetable(Subject subject, Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new RemoveException("removeUtilitySet - removeSubjectToTimetable : 해당 시간표가 존재하지 않습니다.");
            }
            for (Timetable t : TimetableManager.timetableList) {
                if (t.equals(timetable)) {
                    if (t.getSubjects().contains(subject)) {
                        t.getSubjects().remove(subject);
                        System.out.println("현재 시간표에서 과목을 삭제하였습니다.");
                        return;
                    } else {
                        throw new RemoveException("removeUtilitySet - removeSubjectToTimetable : 시간표에 해당 과목이 없습니다.");
                    }
                }
            }
        } catch (RemoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 특정 시간표에서 모든 과목을 삭제하는 메소드입니다.
     * @param timetable 과목을 모두 삭제할 시간표
     */
    public void removeAllSubjectToTimetable(Timetable timetable) {
        try {
            if (!TimetableManager.timetableSets.contains(timetable)) {
                throw new RemoveException("removeUtilitySet - removeAllSubjectToTimetable : 존재하지 않는 시간표입니다.");
            }
            for (Timetable t : TimetableManager.timetableList) {
                if (t.equals(timetable)) {
                    t.getSubjects().clear();
                    System.out.println("시간표에 있는 모든 과목을 지웠습니다.");
                    return;
                }
            }
        } catch (RemoveException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 데이터베이스(파일)에서 과목을 삭제하는 메소드입니다.
     * @param subject 삭제할 과목
     */
    public void removeSubjectToDatabase(Subject subject) {
        try {
            if (!SubjectManager.subjectSets.contains(subject)) {
                throw new RemoveException("removeUtilitySet - removeSubjectToDatabase : 존재하지 않는 과목입니다.");
            }

            // 메모리 상에서도 삭제
            SubjectManager.subjectSets.remove(subject);
            SubjectManager.subjectList.remove(subject);
            SubjectManager.subjectSets_fineNameUseCode.remove(subject.getSubjectCode());

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

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
