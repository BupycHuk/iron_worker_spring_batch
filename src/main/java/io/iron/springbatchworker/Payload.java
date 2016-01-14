package io.iron.springbatchworker;

import java.util.HashMap;
import java.util.Map;

public class Payload {

    private String jobPath;
    private String jobIdentifier;
    private Map <String, String> parameters;

    public String getJobIdentifier() {
        return jobIdentifier;
    }

    public void setJobIdentifier(String jobIdentifier) {
        this.jobIdentifier = jobIdentifier;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, String> parameters) {
        this.parameters = parameters;
    }

    public String getJobPath() {
        return jobPath;
    }

    public void setJobPath(String jobPath) {
        this.jobPath = jobPath;
    }
}
