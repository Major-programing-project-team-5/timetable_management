package com.majorbasic.project.datastructure;

import java.util.List;
import java.util.Map;

public class Graduation {
    //졸엽오건은 하나만 존재해도 되니까 딱히 매니저같은거 안 만들고 객체 하나만 가지고있도록합니다.
    public static int totalCreditsRequired; // 총 이수 학점 목록
    public static Map<String, Integer> CreditRequiredEachMajor; // 각 과목별 이수 학점 목록입니다.
    /*
    이번 구현(1차)에섡 쓰이지 않습니다.
     */
    public static List<Subject> requiredSubject; // 졸업을 위한 필수 수강 과목 목록
    /*
    아번 구현(1차) 에선 쓰이지 않습니다.
     */


    /**
     * 이번 구현에선 그냥 총 학점만 받아서 초기화하는 식입니다..
     */
    public static void resetGraduation(int input) {
        totalCreditsRequired = input;
    }

}
