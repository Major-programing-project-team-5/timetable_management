package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.*;
import com.majorbasic.project.views.OnloadProgram;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ChangeManager {
    // UserManager.isAdmin 체크는 별도로 구현 필요
    public void changeMain(String input) {
        String[] tokens = input.trim().split("\\s+");
        if (tokens.length < 2) {
            System.out.println("명령어가 너무 짧습니다.");
            return;
        }
        // 현재 로그인한 사용자 아이디 가져오기
        if (!UserManager.isAdmin) {
            System.out.println("권한이 없습니다. 관리자만 실행할 수 있습니다.");
            return;
        }

        String commandType = tokens[1].toLowerCase();

        try {
            switch (commandType) {
                case "subject":
                    if (tokens.length < 5) {
                        System.out.println("subject 명령어 인자가 부족합니다.");
                        return;
                    }

                    String[] subjectIdentifier;
                    if (tokens[2].contains(",")) {
                        subjectIdentifier = tokens[2].split(",");
                        if (subjectIdentifier.length != 3) {
                            System.out.println("subject 식별자는 1개(과목코드) 또는 3개(과목명,학수번호,학점)여야 합니다.");
                            return;
                        }
                    } else {
                        subjectIdentifier = new String[]{tokens[2]};
                    }

                    int attrValCount = tokens.length - 3;
                    if (attrValCount % 2 != 0) {
                        System.out.println("속성 목록과 값 목록의 개수가 일치하지 않습니다.");
                        return;
                    }
                    int half = attrValCount / 2;
                    String[] targetAtr = new String[half];
                    String[] alterValue = new String[half];
                    System.arraycopy(tokens, 3, targetAtr, 0, half);
                    System.arraycopy(tokens, 3 + half, alterValue, 0, half);

                    changeSubjectAtr(subjectIdentifier, targetAtr, alterValue);
                    System.out.println("과목 속성 변경 완료.");
                    break;

                case "graduate":
                    if (tokens.length < 5) {
                        System.out.println("graduate 명령어 인자가 부족합니다.");
                        return;
                    }
                    String gradCmd = tokens[2].toLowerCase();
                    String area = tokens[3];
                    switch (gradCmd) {
                        case "add":
                            change_addReqSubtoGrad(area, tokens[4]);
                            break;
                        case "delete":
                            change_deleteReqSubtoGrad(area, tokens[4]);
                            System.out.println("졸업 필수 과목 삭제 완료.");
                            break;
                        case "modify":
                            int newCredit;
                            try {
                                newCredit = Integer.parseInt(tokens[4]);
                            } catch (NumberFormatException e) {
                                System.out.println("modify 명령어의 학점은 정수여야 합니다.");
                                return;
                            }
                            change_ModifyMinCreditToGrad(area, newCredit);
                            System.out.println("졸업 영역 최소 학점 변경 완료.");
                            break;
                        default:
                            System.out.println("지원하지 않는 graduate 명령어: " + gradCmd);
                            break;
                    }
                    break;

                case "time":
                    if (tokens.length != 4) {
                        System.out.println("time 명령어는 정확히 2개의 인자(년도 학기)를 필요로 합니다.");
                        return;
                    }
                    int year, semester;
                    try {
                        year = Integer.parseInt(tokens[2]);
                        semester = Integer.parseInt(tokens[3]);
                    } catch (NumberFormatException e) {
                        System.out.println("년도와 학기는 정수여야 합니다.");
                        return;
                    }
                    changeCurrentTime(year, semester);
                    break;

                default:
                    System.out.println("지원하지 않는 change 명령어 종류: " + commandType);
                    break;
            }
        } catch (IllegalArgumentException | IOException e) {
            System.out.println("명령 실행 중 오류: " + e.getMessage());
        }
    }
    /**
     * subjectIdentifier
     * - 길이 1 : 과목 코드 (String)
     * - 길이 3 : {과목명, 학수번호, 학점} 튜플
     *
     * targetAtr, alterValue는 각각 변경할 속성 이름과 값 배열
     */
    public void changeSubjectAtr(String[] subjectIdentifier, String[] targetAtr, String[] alterValue) {
        if (targetAtr.length != alterValue.length) {
            throw new IllegalArgumentException("수정할 속성 개수와 값 개수가 다릅니다.");
        }

        Subject targetSubject = null;

        if (subjectIdentifier.length == 1) {
            targetSubject = SubjectManager.findSubject(subjectIdentifier[0]);
        } else if (subjectIdentifier.length == 3) {
            targetSubject = SubjectManager.findSubject(subjectIdentifier);
        } else {
            throw new IllegalArgumentException("subjectIdentifier 배열 길이는 1 또는 3이어야 합니다.");
        }

        if (targetSubject == null) {
            throw new IllegalArgumentException("해당 과목을 찾을 수 없습니다.");
        }

        for (int i = 0; i < targetAtr.length; i++) {
            String attr = targetAtr[i];
            String val = alterValue[i];

            switch (attr) {
                case "과목명":
                    targetSubject.setSubjectName(val);
                    break;
                case "학점":
                    try {
                        int creditVal = Integer.parseInt(val);
                        targetSubject.setCredit(creditVal);
                    } catch (NumberFormatException e) {
                        throw new IllegalArgumentException("학점은 정수여야 합니다.");
                    }
                    break;
                case "시간":
                    String[] parts = val.split(";");
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("시간 형식이 올바르지 않습니다. 예: '월,화 13:00~15:00,17:00~18:00'");
                    }
                    targetSubject.setSubjectDayTimes(parts);
                    break;
                case "이수구분":
                    targetSubject.setCategory(val);
                    break;
                case "강의실":
                    try {
                        Field lectureRoomField = Subject.class.getDeclaredField("lectureRoom");
                        lectureRoomField.setAccessible(true);
                        lectureRoomField.set(targetSubject, val);
                    } catch (Exception e) {
                        throw new RuntimeException("강의실 필드 수정 실패: " + e.getMessage());
                    }
                    break;
                case "선수과목":
                    String[] codes = val.split(",");
                    List<Subject> newPrevSubjects = new ArrayList<>();
                    for (String code : codes) {
                        Subject prev = SubjectManager.findSubject(code.trim());
                        if (prev != null) {
                            newPrevSubjects.add(prev);
                        }
                    }
                    try {
                        Field prevSubjectsField = Subject.class.getDeclaredField("previousSubject");
                        prevSubjectsField.setAccessible(true);
                        prevSubjectsField.set(targetSubject, newPrevSubjects);
                    } catch (Exception e) {
                        throw new RuntimeException("선수과목 필드 수정 실패: " + e.getMessage());
                    }
                    break;
                default:
                    throw new IllegalArgumentException("지원하지 않는 속성: " + attr);
            }
        }
    }

    /**
     * 졸업 필수 과목 영역에 새로운 필수과목 추가
     * @param area 영역 이름 (예: "기초교양:글쓰기", "전공", "지정교양", "심화교양:창의적전문인")
     * @param courseCode 과목 코드
     */
    public void change_addReqSubtoGrad(String area, String courseCode) {
        Subject subject = SubjectManager.findSubject(courseCode, 0);
        if (subject == null) {
            throw new IllegalArgumentException("존재하지 않는 과목 코드입니다: " + courseCode);
        }
        Graduation.addReqSubject(area, courseCode);
    }

    /**
     * 영역별 최소 이수 학점 변경
     */
    public void change_ModifyMinCreditToGrad(String area, int newCredit) {
        if (newCredit < 0) {
            throw new IllegalArgumentException("최소 이수 학점은 음수일 수 없습니다.");
        }
        Graduation.CreditRequiredCreditEachArea.put(area, newCredit);
        System.out.println("영역 '" + area + "'의 최소 이수 학점이 " + newCredit + "으로 수정되었습니다.");
    }

    /**
     * 영역별 필수 과목 삭제
     */
    public void change_deleteReqSubtoGrad(String area, String courseCode) {
        if (!Graduation.requiredSubject.containsKey(area)) {
            System.out.println("삭제하려는 영역이 존재하지 않습니다: " + area);
            return;
        }

        List<String> subjects = Graduation.requiredSubject.get(area);
        if (subjects == null || subjects.isEmpty()) {
            System.out.println("해당 영역에 필수 과목이 없습니다: " + area);
            return;
        }

        boolean removed = subjects.remove(courseCode);
        if (!removed) {
            System.out.println("해당 과목은 필수 과목 목록에 없습니다: " + courseCode);
            return;
        }

        System.out.println("영역 '" + area + "'에서 필수 과목 '" + courseCode + "'가 삭제되었습니다.");
    }
    /**
     * 현재 시간 변경
     * @param year 변경할 연도
     * @param semester 변경할 학기
     * @throws IOException 파일 입출력 오류
     * @throws IllegalArgumentException 유효하지 않은 값인 경우
     */
    public void changeCurrentTime(int year, int semester) throws IOException {
        if (semester <= 0) {
            throw new IllegalArgumentException("학기는 1 이상이어야 합니다.");
        } else if(semester > 4) {
            throw new IllegalArgumentException("학기는 4 이하여야 합니다.");
        }

        // 1. userData.txt에서 사용자 목록 읽기 (admin 제외)
        List<String> userIds = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("./resources/userData.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String userId = line.trim().split("\\s+")[0];
                if (!userId.isEmpty() && !userId.equalsIgnoreCase("admin")) {
                    userIds.add(userId);
                }
            }
        }

        // 2. 모든 사용자 시간표 파일 읽고 첫 줄(년도 학기) 확인 후 최대값 찾기
        int maxYear = 0;
        int maxSemester = 0;

        for (String userId : userIds) {
            Path timetablePath = Paths.get("./resources/timetable", "timetable_" + userId + ".txt");
            if (!Files.exists(timetablePath)) {
                System.out.println("시간표 파일이 없습니다: " + timetablePath.toString());
                continue;
            }

            try (BufferedReader br = Files.newBufferedReader(timetablePath)) {
                String firstLine = br.readLine();
                if (firstLine == null || firstLine.trim().isEmpty()) {
                    continue;  // 빈 파일 무시
                }

                String[] parts = firstLine.trim().split("\\s+");
                if (parts.length != 2) {
                    System.out.println("잘못된 시간표 형식(년도 학기): " + timetablePath.toString());
                    continue;
                }

                int fileYear;
                int fileSemester;
                try {
                    fileYear = Integer.parseInt(parts[0]);
                    fileSemester = Integer.parseInt(parts[1]);
                } catch (NumberFormatException e) {
                    System.out.println("년도 또는 학기 숫자 변환 실패: " + timetablePath.toString());
                    continue;
                }

                // 최대값 갱신
                if (fileYear > maxYear || (fileYear == maxYear && fileSemester > maxSemester)) {
                    maxYear = fileYear;
                    maxSemester = fileSemester;
                }
            }
        }

        // 3. 입력받은 (year, semester)가 모든 사용자 최대값보다 이전이면 오류
        if (year < maxYear || (year == maxYear && semester < maxSemester)) {
            throw new IllegalArgumentException(
                    "입력한 년도/학기는 모든 사용자의 최신 시간표보다 과거입니다. 현재 최대: " + maxYear + "년 " + maxSemester + "학기"
            );
        }

        // 4. onloadProgram 현재 시간 갱신
        OnloadProgram.thisYear = year;
        OnloadProgram.thisSemester = semester;

        System.out.println("현재 시간 변경 완료: " + year + "년 " + semester + "학기");
    }
}
