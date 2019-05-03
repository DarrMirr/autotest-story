package pol.mirr.data.model;

import java.util.Map;

/**
 * Created by Pol Mirr on 24.09.17.
 *
 * Model dataset of caseID
 */
public class Dataset {
    private Map<String, Object> input;
    private Map<String, Object> output;

    protected Dataset() {
    }

    public Dataset(Map<String, Object> input, Map<String, Object> output) {
        this.input = input;
        this.output = output;
    }

    public Map<String, Object> getInput() {
        return input;
    }

    public Map<String, Object> getOutput() {
        return output;
    }

    public String getInput(String key) {
        return input.getOrDefault(key, "").toString();
    }

    public String getOutput(String key) {
        return output.getOrDefault(key, "").toString();
    }

    @Override
    public String toString() {
        return "Dataset:" +
                " input: " + input.toString() +
                " output: " + output.toString();
    }
}
