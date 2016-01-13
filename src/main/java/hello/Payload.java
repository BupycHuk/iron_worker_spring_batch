package hello;

import java.util.HashMap;
import java.util.Map;

public class Payload {

    private String jobName;
    private Map <String, String> parameters;

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }
}
