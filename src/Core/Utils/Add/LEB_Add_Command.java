package Core.Utils.Add;

public class LEB_Add_Command {
    1. 지정 학기 시간표 생성(e.g.  add 2 1 (2학년 1학기 시간표),
    2. add current 3 1 (2학년 1학기 시간표 생성 및 현재 시간표로 설정)
    3. 시간표에 과목 추가 add current 운영체제 화 14:00~16:00 (현재 시간표에 과목 추가)
    4. 데이터 베이스에 과목 등록 add 과목 <강의 정보>

    // 명령어가 add인 것을 외부에서 확인 후 접근.
    public static void AddCommand(String input){
        String[] tokens = input.trim();

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
            createAndSetCurrentTimetable(year, semester);
        } else if (tokens[1].equals("current") && !isNumeric(tokens[2])){
            // 3. 시간표에 과목 추가
            String subjectName = tokens[2];
            String day = tokens[3];
            String time = tokens[4];
            addSubjectToCurrentTimetable(subjectName, day, time);
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
    }

    public static void createAndSetCurrentTimetable(int year, int semester) {
        // year와 semester로 구분되는 시간표 만들고, current함을 식별할 수 있는 무언가를 추가.
    }

    public static void addSubjectToCurrentTimetable(String subject, String day, String time) {
        // 파라미터들을 DB에서 비교하여 해당 과목을 특정.
        // 그 과목에서 시간표에 필요한 데이터만 가져옴.
        // current 시간표 불러와서 데이터 집어넣음.
    }

    public static void addSubjectToDatabase(String[] lectureInfo) {
        // lectureInfo의 값들이 DB에 저장된 데이터의 형식과 같은지 체크.
        // lectureInfo의 값들을 DB파일에 저장.
    }

    // 유틸 함수
    private static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
}