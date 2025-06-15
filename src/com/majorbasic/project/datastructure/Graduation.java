package com.majorbasic.project.datastructure;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Graduation {
    // 졸업 요건은 하나만 존재
    public static int totalCreditsRequired; // 총 이수 학점 목록
    public static Map<String, Integer> CreditRequiredCreditEachArea = new HashMap<>(); // 영역별 최소 이수 학점

    // 필수 수강 과목 목록 (영역 -> 필수 과목 코드 리스트)
    public static Map<String, List<String>> requiredSubject = new HashMap<>();

    /**
     * 총 최저 이수 학점 반환
     */
    public static int getTotalRequiredCredit() {
        return totalCreditsRequired;
    }

    /**
     * 졸업 정보 리셋 (총학점, 영역별 학점, 필수과목 모두 초기화)
     */
    public static void resetGraduation(int totalCreditsRequired) {
        Graduation.totalCreditsRequired = totalCreditsRequired;
        CreditRequiredCreditEachArea.clear();
        requiredSubject.clear();
    }

    /**
     * 영역별 최소 이수 학점 조회
     */
    public static int getEachAreaReqCredit(String area) {
        if (!CreditRequiredCreditEachArea.containsKey(area)) {
            System.out.println("존재하지 않는 영역입니다: " + area);
            return -1;
        }
        return CreditRequiredCreditEachArea.get(area);
    }

    /**
     * 영역별 졸업 필수 과목 목록 조회
     */
    public static List<String> getEachAreaReqSubject(String area) {
        if (!requiredSubject.containsKey(area)) {
            System.out.println("존재하지 않는 영역입니다: " + area);
            return null;
        }
        return requiredSubject.get(area);
    }

    /**
     * 영역별 필수 과목 추가
     */
    public static void addReqSubject(String area, String code) {
        if (!requiredSubject.containsKey(area)) {
            System.out.println("해당 영역이 존재하지 않습니다: " + area);
            return;
        }

        try {
            List<String> subjects = requiredSubject.get(area);
            if (subjects == null) {
                // 해당 영역에 대한 리스트가 null인 경우 새 리스트 생성
                subjects = new java.util.ArrayList<>();
                requiredSubject.put(area, subjects);
            } else {
                // 어떤 타입이든 새 ArrayList로 복사하여 변경 가능하게 만듦
                subjects = new java.util.ArrayList<>(subjects);
                requiredSubject.put(area, subjects);
            }

            // 이미 존재하는 코드인지 확인
            if (!subjects.contains(code)) {
                subjects.add(code);
                System.out.println(area + " 영역에 " + code + " 과목이 추가되었습니다.");
            } else {
                System.out.println(area + " 영역에 이미 " + code + " 과목이 존재합니다.");
            }
        } catch (Exception e) {
            System.out.println("과목 추가 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
