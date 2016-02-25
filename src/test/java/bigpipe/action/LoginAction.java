package bigpipe.action;

import org.opensjp.openbigpipe.annotation.BigPipe;
import org.opensjp.openbigpipe.annotation.ExecuteType;
import org.opensjp.openbigpipe.annotation.Pagelet;
import org.opensjp.openbigpipe.annotation.Param;
import org.opensjp.openbigpipe.core.BigPipeController;
import org.springframework.beans.factory.annotation.Autowired;


public class LoginAction{
    String name;
    @Autowired
    private User user;
    @Param
    private BigPipeController  bigPipeExecutor;
    
    @BigPipe(type = ExecuteType.BIGPIPE)
    public String login() throws Exception {
    	user = new User();
    	user.setName("John");
    	user.setRole("admin");
    	if(user != null){
    		if(user.getRole().equals("admin")){
    			String[] pageletKeys = new String[]{"one","two","three","four"};
    			return bigPipeExecutor.execute("index.ftl", pageletKeys);
    		}else{
    			String[] pageletKeys = new String[]{"one","two","three","four"};
    			return bigPipeExecutor.execute("index.ftl", pageletKeys);
    		}
    	}
        return "error";
    }
    
    @Pagelet(key="test") 
    public void test(){
    	
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
