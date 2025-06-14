package com.majorbasic.project.datastructure;

import java.util.List;
import java.util.Map;

public class Graduation {
    //졸엽오건은 하나만 존재해도 되니까 딱히 매니저같은거 안 만들고 객체 하나만 가지고있도록합니다.
    public static int totalCreditsRequired; // 총 이수 학점 목록
    public static Map<String, Integer> CreditRequiredCreditEachArea; // 각 과목별 이수 학점 목록입니다.
    //string : 영역 interger : 요구학점입니다.
    //영역 모음 : 전공, 지정교양, 지정필수, 기초교양, 기초교양:외국어, 기초교양:글쓰기, 기초교양:sw, 기초교양:취창업, 기초요양:인성, 심화교양, 심화교양:사고력, 심화고양:학문소양, 심화교양:글로벌

    public static Map<String, String>  requiredSubject; // 졸업을 위한 필수 수강 과목 목록입니다
    //string은 영역입니다. 뒤의 String은 subject의 CourseCode와 동일합니다.


    /**
     * 총 최저이수학점 얻기
     * @return 총 최저이수학점 반환
     */
    public static int getTotalRequiredCredit(){
        return totalCreditsRequired;
    }

    /**
     * 영역별 최소이수학점 얻기
     * @param area 영역 이름 / 예시는 위에 있음
     * @return 영역별 최소이주학점
     */

    public static int getEachAreaReqCredit(String area){
        if(!CreditRequiredCreditEachArea.containsKey(area)){
            System.out.println("존재하지 않는 영역입니다");
            return -1;
        }
        return CreditRequiredCreditEachArea.get(area);

    }

    /**
     * 영역별 졸업필수과목 찾기
     * @param area
     * @return 영역별 졸업필수과목
     */
    public static String getEachAreaReqSubject(String area){
        if(!requiredSubject.containsKey(area)){
            System.out.println("존재하지 않는 영역입니다");
            return null;
        }
        return requiredSubject.get(area);
    }


}
