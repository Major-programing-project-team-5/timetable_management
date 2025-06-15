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
        requiredSubject.get(area).add(code);
    }
}
