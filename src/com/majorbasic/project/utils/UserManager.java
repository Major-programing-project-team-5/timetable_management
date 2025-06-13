package com.majorbasic.project.utils;

import com.majorbasic.project.views.OnloadProgram;

import java.io.*;
import java.util.*;

public class UserManager {
    public static Map<String, String> userDataMap = new HashMap<>();
    public static boolean isAdmin;
    public static String currentUserID;

    private static final Scanner sc = new Scanner(System.in);
    private static final OnloadProgram onload = new OnloadProgram();

    /**
     * 로그인 또는 회원가입을 선택합니다.
     * @return 선택한 메뉴
     */
    public static String showLoginOrRegisterMenu() {
        while (true) {
            System.out.println("로그인을 원하시면 1번을");
            System.out.println("회원가입을 원하시면 2번을 입력해주세요");
            System.out.println("종료를 원하시면 q를 입력해주세요");
            System.out.print("> ");

            switch (sc.nextLine()) {
                case "1":
                    if (loginMenu()) {
                        onload.run();
                    } else {
                        break;
                    }
                case "2":
                    if (registerMenu()) {
                        break;
                    }
                case "q":
                    System.exit(0);
                    return "quit";
                default:
                    break;
            }
        }
    }

    /**
     * 로그인 메뉴입니다.
     * @return 로그인에 성공하면 true, 실패하면 false
     */
    public static boolean loginMenu() {
        String input_id;
        String input_password;

        System.out.println("[로그인]\n");
        System.out.print("id : ");
        input_id = sc.nextLine();
        System.out.print("password : ");
        input_password = sc.nextLine();

        if (input_id.length() >= 20 || input_password.length() >= 20) {
            System.out.println("id와 password는 20자 이상일 수 없습니다.");
            return false;
        } else if (input_id.isEmpty() || input_password.isEmpty()) {
            System.out.println("id 또는 password가 입력되지 않았습니다.");
            return false;
        }

        return tryLogin(input_id, input_password);
    }

    /**
     * input_id와 input_password를 사용해 로그인을 시도합니다.
     * @param input_id 로그인을 위한 id
     * @param input_password 로그인을 위한 password
     * @return 로그인에 성공하면 true, 실패하면 false
     */
    public static boolean tryLogin(String input_id, String input_password) {
        if (userDataMap.containsKey(input_id)) {
            if (userDataMap.get(input_id).equals(input_password)) {
                setCurrentUserData(input_id);
                return true;
            } else {
                System.out.println("password가 일치하지 않습니다.");
                return false;
            }
        } else {
            System.out.println("존재하지 않는 id로 로그인을 시도했습니다.");
            return false;
        }
    }

    /**
     * 회원가입을 진행하는 메뉴입니다.
     * @return 회원가입에 성공하면 true, 실패하면 false
     */
    public static boolean registerMenu() {
        String new_id;
        String new_password;

        System.out.println("[회원가입]\n");
        System.out.println("회원가입 규칙");
        System.out.println("1. id는 다른 회원과 중복되지 않는 유일한 값이어야 합니다.");
        System.out.println("2. id와 비밀번호 모두 20자를 넘지 않는 공백 없는 문자열이어야 합니다.\n");

        while (true) {
            System.out.print("id : ");
            new_id = sc.nextLine();
            System.out.print("password : ");
            new_password = sc.nextLine();

            if (new_id.length() >= 20 || new_password.length() >= 20) {
                System.out.println("id와 password는 20자 이상일 수 없습니다. 다시 입력하세요.");
                return false;
            } else if (new_id.isEmpty() || new_password.isEmpty()) {
                System.out.println("id 또는 password가 입력되지 않았습니다.");
                return false;
            }

            if (!tryRegister(new_id, new_password)) {
                System.out.println("오류, 해당 id는 이미 존재합니다!");
                return false;
            }

            return true;
        }
    }

    /**
     * input_id와 input_password를 사용하여 회원가입을 시도합니다.
     * @param input_id 회원가입을 위한 id
     * @param input_password 회원가입을 위한 password
     * @return 회원가입에 성공하면 true, 실패하면 false
     */
    public static boolean tryRegister(String input_id, String input_password) {
        if (userDataMap.containsKey(input_id)) {
            return false;
        } else {
            userDataMap.put(input_id, input_password);
            try {
                File userTimetable = new File(String.format("./resources/timetable/timetable_%s.txt", input_id));
                if (!userTimetable.createNewFile()) {
                    System.out.println("유저 데이터를 생성하는데 오류가 생겼습니다.");
                    System.out.println("유저 시간표 파일을 생성하지 못했습니다.");
                    return false;
                }
            } catch (Exception e) {
                System.out.println("유저 데이터를 생성하는데 오류가 생겼습니다.");
                System.out.println(e.getMessage());
                return false;
            }
            UpdateManager.saveUserData();
            setCurrentUserData(input_id);
            return true;
        }
    }

    /**
     * user_data.txt 파일에서 데이터를 읽어 userDataMap에 저장합니다
     * @return userDataMap에 저장에 성공하면 true, 실패하면 false
     */
    public static boolean loadUserData() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./resources/userData.txt"));
            String line = br.readLine();
            while (line != null) {
                String[] tokens = line.split(" ");
                userDataMap.put(tokens[0], tokens[1]);
                line = br.readLine();
            }
            br.close();
            return true;
        } catch (Exception e) {
            System.out.println("loadUserData 에러 : " + e.getMessage());
            System.out.println("유저 데이터 파일을 불러올 수 없습니다.");
            return false;
        }
    }

    /**
     * 로그인한 정보를 저장합니다.
     * @param input_id 로그인한 id
     */
    public static void setCurrentUserData(String input_id) {
        isAdmin = userDataMap.get(input_id).equals("admin");
        currentUserID = input_id;
    }

    public static boolean deleteUser(String id) {
        if (id.equals("admin")) {
            System.out.println("관리자 계정은 삭제 불가능 합니다");
            return false;
        } else if (!userDataMap.containsKey(id)) {
            System.out.println("존재하지 않는 사용자를 삭제하려고 시도했습니다.");
            return false;
        } else {
            if (UpdateManager.deleteUserToDatabase(id)) {
                userDataMap.remove(id);
                return true;
            } else {
                System.out.println("유저 데이터베이스에서 삭제하지 못했습니다.");
                return false;
            }
        }
    }
}
