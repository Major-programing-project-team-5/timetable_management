package Core.Views;

import Core.DataStructure.Graduation;
import Core.DataStructure.TimetableManager;
import Core.DataStructure.subjectManager;
import Core.Utils.Update.UpdateManager;

public class OnloadProgram {

    public void run(){
        update();
    }
    public void update(){
        UpdateManager updateManager = new UpdateManager();
        updateManager.updateSubjectManager("src/resources/subject.txt");
        updateManager.updateTimetableManager("src/resources/timetable.txt");
        updateManager.updateGraduate("src/resources/graduate.txt");
    }

}
