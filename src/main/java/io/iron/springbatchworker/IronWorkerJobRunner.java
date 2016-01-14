package io.iron.springbatchworker;

import org.springframework.batch.core.launch.support.CommandLineJobRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IronWorkerJobRunner {

    public static void main(String[] args) throws Exception {
        final SpringWorkerHelper springWorkerHelper = SpringWorkerHelper.fromArgs(args);
        final Payload payload = springWorkerHelper.getPayload();

        final Map<String, String> parameters = payload.getParameters();
        final List<String> strings = new ArrayList<String>(){{
            add(payload.getJobPath());
            add(payload.getJobIdentifier());
        }};
        for (Map.Entry<String, String> paramsSet : parameters.entrySet()) {
            strings.add(String.format("%s=%s", paramsSet.getKey(), paramsSet.getValue()));
        }

        CommandLineJobRunner.main(strings.toArray(new String[strings.size()]));
    }
}
