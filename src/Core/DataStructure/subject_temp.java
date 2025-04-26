package Core.DataStructure;

public class Subject {
    private String subjectName;
    private int credit;
    private String subjectCode;
    private String category; // 예: 전공필수, 교양선택 등

    public Subject(String subjectName, int credit, String subjectCode, String category) {
        this.subjectName = subjectName;
        this.credit = credit;
        this.subjectCode = subjectCode;
        this.category = category;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "[" + subjectCode + "] " + subjectName + " (" + credit + "학점, " + category + ")";
    }
}