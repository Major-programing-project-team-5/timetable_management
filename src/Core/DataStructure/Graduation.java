package Core.DataStructure;

public class Graduation {
    private int totalCreditsRequired;
    private int majorCreditsRequired;
    private int liberalArtsCreditsRequired;

    public Graduation(int totalCreditsRequired, int majorCreditsRequired, int liberalArtsCreditsRequired) {
        this.totalCreditsRequired = totalCreditsRequired;
        this.majorCreditsRequired = majorCreditsRequired;
        this.liberalArtsCreditsRequired = liberalArtsCreditsRequired;
    }

    public int getTotalCreditsRequired() {
        return totalCreditsRequired;
    }

    public int getMajorCreditsRequired() {
        return majorCreditsRequired;
    }

    public int getLiberalArtsCreditsRequired() {
        return liberalArtsCreditsRequired;
    }

    @Override
    public String toString() {
        return "졸업 요건: 총 학점 " + totalCreditsRequired + "점, 전공 학점 " + majorCreditsRequired + "점, 교양 학점 " + liberalArtsCreditsRequired + "점";
    }
}
