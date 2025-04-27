package Core.Views;

import Core.DataStructure.Graduation;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.Update.UpdateManager;

public class OnloadProgram {
    Core.DataStructure.subjectManager subjectManager = new subjectManager();
    TimetableManager timetableManager = new TimetableManager();
    Graduation graduation;

    public void run(){

    }
    public void update(){
        UpdateManager updateManager = new UpdateManager();
        this.subjectManager =  updateManager.updateSubjectManager("subject.txt");
        this.timetableManager = updateManager.updateTimetableManager("timetable.txt");
        this.graduation = updateManager.updateGraduate("graduate.txt");
    }

}
