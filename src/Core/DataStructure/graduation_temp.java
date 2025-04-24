package Core.DataStructure;

import java.util.*;

public class graduation_temp {
//    4.4.1 총 학점
//    자료형: int
//    설명: 졸업을 위해 이수해야 하는 전체 학점 수
//
//4.4.2 영역별 필수 학점
//    자료형: Map<String, Integer>
//    설명: 각 영역(전공, 교양 등)별로 반드시 이수해야 하는 학점 수
//    의미 규칙
//    각 영역별 학점은 0 이상의 정수여야 하며, 총 학점을 넘을 수 없음
    public int maxCredit;
    public Map<String, Integer> requiredCreditsByArea;
    public graduation_temp(Map<String, Integer> requiredCreditsByArea, int maxCredit) {
        this.requiredCreditsByArea = requiredCreditsByArea;
        this.maxCredit = maxCredit;
    }

    public graduation_temp() {
    }
}
