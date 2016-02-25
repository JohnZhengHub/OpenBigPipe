package bigpipe.pipes;

import org.opensjp.openbigpipe.annotation.Pagelet;
import org.opensjp.openbigpipe.annotation.PageletSet;
import org.opensjp.openbigpipe.annotation.Param;

import bigpipe.action.User;
import bigpipe.util.SleepUtils;

@PageletSet
public class UserPipes {
	@Param
    private String name;
	@Param
    private User user;
	@Param
    private int age;
	@Param
    private int time;
	
	@Pagelet(key="one")
	public String pipeOne(){
		time = 3000;
        if (name.equals("down")) {
            user = null;
            user.setName("");
        }
        SleepUtils.sleep(time);		
		return "demo/one.ftl";
	}
	
	@Pagelet(key="two")
	public String pipeTwo(){
		time = 50;
        SleepUtils.sleep(time);
        
        return "demo/two.ftl";
	}
	
	@Pagelet(key="three")
	public String pipeThree(){
		age = 50;
        time = 1000;
        SleepUtils.sleep(4);
        
        return "demo/three.ftl";
	}
}
