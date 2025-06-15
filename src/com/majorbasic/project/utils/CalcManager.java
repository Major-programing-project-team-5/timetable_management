package com.majorbasic.project.utils;

import com.majorbasic.project.datastructure.*;

import java.util.*;

public class CalcManager {

    // 사용자 입력 처리
    public void calcInput(String userInput) {
        String[] tokens = userInput.trim().split("\\s+");
        if(tokens.length < 2) {
            System.out.println("인자가 올바르지 않습니다.");
            return;
        }

        try {
            switch(tokens[1]) {
                case "term":
                    if(tokens.length != 4) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateTermCredits(tokens[2], tokens[3]);
                    break;
                case "remain":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateRemainingCredits();
                    break;
                case "basic":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateBasicCredits();
                    break;
                case "adv":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateAdvCredits();
                    break;
                case "alloc":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateAllocCredits();
                    break;
                case "ess":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateEssCredits();
                    break;
                case "major":
                    if(tokens.length != 2) {
                        System.out.println("인자가 올바르지 않습니다.");
                        break;
                    }
                    calculateMajorCredits();
                    break;
                default:
                    System.out.println("인자가 올바르지 않습니다.");
            }
        } catch (Exception e) {
            System.out.println("calc 쪽 명령어 처리 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void calculateTermCredits(String year, String semester) {
        if(!TimetableManager.isTimetableCorrect(year, semester)) {
            return;
        }

        Timetable table = TimetableManager.getTimetable(Integer.parseInt(year), Integer.parseInt(semester));
        if(table == null) {
            return;
        }

        // 해당 학기 시간표에서 학점 합계 계산
        int totalCredits = table.getSubjects().entrySet().stream()
                .filter(e -> {
                    String grade = e.getValue();
                    return grade == null || (!grade.equalsIgnoreCase("F") && !grade.equalsIgnoreCase("N"));
                })
                .mapToInt(e -> e.getKey().getCredit())
                .sum();

        System.out.println(year + "년도 " + Integer.parseInt(semester) + "학기");
        System.out.println("수강 학점: " + totalCredits);
    }

    // 1. 남은 학점 계산
    public void calculateRemainingCredits() {
        int total = sumAllCredits();
        if(total < 0) return;

        int required = Graduation.getTotalRequiredCredit();
        int remaining = required - total;
        System.out.println("최저이수학점: " + required);
        System.out.println("수강 학점: " + total);
        System.out.println("남은 학점: " + remaining);
    }

    // 2. 기초교양 학점 계산 및 필수과목 출력
    public void calculateBasicCredits() {
        calculateAreaDetail("기초교양", List.of(
                "기초교양:외국어", "기초교양:글쓰기", "기초교양:sw",
                "기초교양:취창업", "기초교양:인성"  // 오타 수정
        ));
    }

    // 3. 심화교양
    public void calculateAdvCredits() {
        calculateAreaDetail("심화교양", List.of(
                "심화교양:사고력", "심화교양:학문소양", "심화교양:글로벌"
        ));
    }

    // 4. 지정교양
    public void calculateAllocCredits() {
        calculateAreaDetail("지정교양", List.of("지정교양"));
    }

    // 5. 지정필수
    public void calculateEssCredits() {
        calculateAreaDetail("지정필수", List.of("지정필수"));
    }

    // 6. 전공
    public void calculateMajorCredits() {
        calculateAreaDetail("전공", List.of("전공"));
    }

    // 공통 처리 로직
    private void calculateAreaDetail(String reportName, List<String> areaKeys) {
        if(TimetableManager.timetableList.isEmpty()) {
            System.out.println("시간표가 존재하지 않습니다.");
            return;
        }

        int required = Graduation.getEachAreaReqCredit(reportName);
        if(required < 0) return;

        Map<String, Subject> taken = new HashMap<>();
        int sum = 0;

        for(Timetable tt : TimetableManager.timetableList) {
            for(Map.Entry<Subject, String> entry : tt.getSubjects().entrySet()) {
                Subject s = entry.getKey();
                String grade = entry.getValue();

                // grade가 F 또는 N인 경우 제외
                if(grade != null && (grade.equalsIgnoreCase("F") || grade.equalsIgnoreCase("N"))) {
                    continue;
                }

                String area = s.getCategory();
                for(String key : areaKeys) {
                    if(area.equals(key)) {
                        taken.put(s.getCourseCode(), s);
                        sum += s.getCredit();
                        break;
                    }
                }
            }
        }

        System.out.println(reportName + " 이수현황");
        System.out.println("최저이수학점: " + required);
        System.out.println("수강 학점: " + sum);
        System.out.println("남은 학점: " + (required - sum));

        System.out.println("—— 필수 과목 목록 ——");

        // 수정 부분: 영역별 필수 과목 List<String>으로 변경된 부분 반영
        for (String key : areaKeys) {
            List<String> codes = Graduation.getEachAreaReqSubject(key);
            if (codes == null || codes.isEmpty()) continue;

            for (String code : codes) {
                Subject subj = SubjectManager.findSubject(code, 0);
                if (subj == null) {
                    System.out.println(key + " - " + code + ": 정보 없음");
                    continue;
                }
                boolean done = taken.containsKey(code);
                System.out.print(subj.toString() + " 수강여부: " + (done ? "Yee" : "No"));
                if (done) {
                    System.out.println(" 수강 과목: " + taken.get(code).toString());
                } else {
                    System.out.println();
                }
            }
        }
    }

    /**
     * 사용자의 전체 학점 합을 출력함.
     * @return 사용자가 들은 과목의 전체 합 출력함.
     */
    private int sumAllCredits() {
        if(TimetableManager.timetableList.isEmpty()) {
            System.out.println("시간표가 존재하지 않습니다.");
            return -1;
        }

        return TimetableManager.timetableList.stream()
                .flatMap(tt -> tt.getSubjects().entrySet().stream())
                .filter(e -> {
                    String grade = e.getValue();
                    return grade == null || (!grade.equalsIgnoreCase("F") && !grade.equalsIgnoreCase("N"));
                })
                .mapToInt(e -> e.getKey().getCredit())
                .sum();
    }
}
