package Core.Utils.Add;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LEB_Add_Command {
    1. 지정 학기 시간표 생성(e.g.  add 2 1 (2학년 1학기 시간표),
    2. add current 3 1 (2학년 1학기 시간표 생성 및 현재 시간표로 설정)
    3. 시간표에 과목 추가 add current 운영체제 화 14:00~16:00 (현재 시간표에 과목 추가)
    4. 데이터 베이스에 과목 등록 add 과목 <강의 정보>

    // 명령어가 add인 것을 외부에서 확인 후 접근.
    public static void AddCommand(String input){
        String[] tokens = input.trim();
        String currentTable;

        if (!tokens[0].equals("add")) {
            System.out.println("Invalid command.");
            return;
        }

        if (isNumeric(tokens[1]) && isNumeric(tokens[2])){
            // 1. 지정 학기 시간표 생성
            int year = Integer.parseInt(tokens[1]);
            int semester = Integer.parseInt(tokens[2]);
            creatTimetable(year, semester);
        } else if (tokens[1].equals("current") && isNumeric(tokens[2]) && isNumeric(tokens[3])){
            // 2. 현재 시간표 생성
            int year = Integer.parseInt(tokens[2]);
            int semester = Integer.parseInt(tokens[3]);
            String currentTable = createAndSetCurrentTimetable(year, semester);
        } else if (tokens[1].equals("current") && !isNumeric(tokens[2])){
            // 3. 시간표에 과목 추가
            String[] subjectInfo = Array.copyOfRange(tokens, 2, tokens.length)
            addSubjectToCurrentTimetable(subjectInfo, currentTable);
        } else if (tokens[1].equals("과목")){
            // 4. 데이터베이스에 과목 추가
            String[] lectureInfo = Arrays.copyOfRange(tokens, 2, tokens.length);
            addSubjectToDatabase(lectureInfo);
        } else {
            System.out.println("잘못된 add 명령 형식입니다.");
        }
    }

    public static void createTimetable(int year, int semester) {
        // year와 semester로 구분되는 시간표 파일을 생성.
        String dirName = "data";
        String fileName = year + "_" + semester + "_timetable.csv";
        File dir = new File(dirName);
        File file = new File(dirName + "/" + fileName);

        // 디렉토리가 없다면 생성
        if (!dir.exists()) {
            dir.mkdir();
        }
        // 이미 파일이 있다면 생성하지 않음
        if (file.exists()) {
            System.out.println("이미 존재하는 시간표입니다: " + file.getName());
            return;
        }

        try (FileWriter writer = new FileWriter(file)) {
            // 비어있는 타임테이블 파일 생성
            System.out.println(year + "학년 " + semester + "학기 시간표가 생성되었습니다.");
        } catch (IOException e) {
            System.out.println("시간표 파일 생성 중 오류 발생: " + e.getMessage());
        }
    }

    public static void createAndSetCurrentTimetable(int year, int semester) {
        // year와 semester로 구분해서 시간표 만들고, current 시간표 반환.
        String dirName = "data";
        String fileName = year + "_" + semester + "_timetable.csv";
        File dir = new File(dirName);
        File file = new File(dirName + / fileName);

        if (!dir.exists()){
            dir.mkdir();
        }

        if (!file.exists()){
            try (FileWriter writer = new FileWriter(file)) {
                System.out.println(year + "학년 " + semester + "학기 시간표가 현재 시간표로 설정됨.");
                return fileName;
            } catch (IOException e){
                System.out.println("파일 생성 중 오류: " + e.getMessage());
                return null;
            }
        } else {
            System.out.println(year + "학년 " + semester + "학기 시간표가 현재 시간표로 설정됨.");
            return fileName;
        }
    }

    public static void addSubjectToCurrentTimetable(String[] subjectInfo, String current) {
        // 파라미터들을 DB에서 비교하여 해당 과목을 특정.
        // 그 과목에서 시간표에 필요한 데이터만 가져옴.
        // current 시간표 불러옴.
        // 추가하려는 과목이 해당 시간표의 다른 과목과 겹치지 않는지 검사 후 추가.
        String dbPath = "data/database.csv";
        File dbFile = new File(dbPath);
        String targetLine = null;

        // 1. 데이터베이스에서 과목 찾기
        try (BufferedReader reader = new BufferedReader(new FileReader(dbFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] dbData = line.trim();
                if (dbData.length < subjectInfo.length) continue;

                boolean match = true;
                for (int i=0; i<subjectInfo.length; i++){
                    if (!dbData[i].trim().equals(subjectInfo[i].trim())){
                        match = false;
                        break;
                    }
                }

                // 과목명, 요일, 시간만 저장
                if (dbSubjectName.equals(subjectName)) {
                    targetLine = dbData[0].trim() + "," + dbTokens[1].trim() + "," + dbData[2].trim();
                    break;
                }
            }
        } catch (IOException e) {
            System.out.println("데이터베이스 파일 읽기 오류: " + e.getMessage());
            return;
        }

        if (targetLine == null) {
            System.out.println("일치하는 과목을 데이터베이스에서 찾을 수 없습니다.");
            return;
        }

        String tablePath = "data/" + current;
        try (FileWriter writer = new FileWriter(tabelPath, true)) {
            writer.write(targetLine + "\n");
            System.out.println("시간표에 과목이 추가되었습니다: " + targetLine);
        } catch (IOException e) {
            System.out.println("시간표에 과목 추가 오류: " + e.getMessage());
        }
    }

    public static void addSubjectToDatabase(String[] lectureInfo) {
        // lectureInfo의 값들이 DB에 저장된 데이터의 형식과 같은지 체크.
        // lectureInfo의 값들을 DB파일에 저장.
        String dbPath = "data/database.csv";

        // 파라미터와 데이터베이스의 데이터 형식 검사
        if (!checkFormat(lectureInfo)){
            System.out.println("강의 정보 형식이 올바르지 않습니다.");
            return;
        }

        // 공백 구분 문자열로 변환
        StringBuilder line = new StringBuilder();
        for (int i=0; i<lectureInfo.length; i++){
            line.append(lectureInfo[i].trim());
            if (i < lectureInfo.length-1) {
                line.append(" ");
            }
        }

        // database에 강의 추가
        try (FileWriter writer = new FileWriter(dbPath, true)) {
            writer.write(line.toString() + "\n");
            System.out.println("강의 정보가 데이터베이스에 추가되었습니다.");
        } catch (IOExcption e) {
            System.out.println("데이터베이스 쓰기 오류: " + e.getMessage());
        }
    }

    // 유틸 함수
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    public static boolean checkFormat(String[] lectureInfo) {
        // db에 강의 추가할 때, 형식이 올바른지 검사(우선 단순하게 각 데이터의 타입 검사로 구현함)
        // 길이 확인
        if (lectureInfo.length != 9) {
            System.out.println("데이터베이스에 강의를 등록하기 위한 데이터가 부족합니다.");
            return false;
        }

        // 인덱스 4, 7: int 타입 체크
        try {
            Integer.parseInt(lectureInfo[4]); // 학점
            Integer.parseInt(lectureInfo[7]); // 학수번호
        } catch (NumberFormatException e) {
            System.out.println("학점 또는 학수번호가 숫자가 아닙니다.");
            return false;
        }

        // 나머지는 String이므로 별도 검사 없이 true 반환
        return true;
    }

}