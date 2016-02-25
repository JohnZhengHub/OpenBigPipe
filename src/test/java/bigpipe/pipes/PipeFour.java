package bigpipe.pipes;

import org.opensjp.openbigpipe.annotation.Pagelet;
import org.opensjp.openbigpipe.annotation.PageletSet;
import org.opensjp.openbigpipe.annotation.Param;
import org.opensjp.openbigpipe.annotation.Priority;

import bigpipe.action.User;
import bigpipe.util.SleepUtils;

@PageletSet
public class PipeFour{

    @Param
    private String name;
    @Param
    private User user;
    @Param
    private int age;
    @Param
    private int time;
    
    @Pagelet(key="four",priority=Priority.HEIGHT)
    public String execute() {
        age = 100;
        time = 5000;
        SleepUtils.sleep(time);
        return "demo/one.ftl";
    }


}
