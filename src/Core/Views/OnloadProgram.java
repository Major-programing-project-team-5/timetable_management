package Core.Views;

import Core.DataStructure.Graduation;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.Update.UpdateManager;

public class OnloadProgram {

    public void run(){

    }
    public void update(){
        UpdateManager updateManager = new UpdateManager();
        updateManager.updateSubjectManager("subject.txt");
        updateManager.updateTimetableManager("timetable.txt");
        updateManager.updateGraduate("graduate.txt");
    }

}
