package pol.mirr.data.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Created by Pol Mirr on 24.09.17.
 *
 * Model dataset of caseID
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dataset {
    @Getter private Map<String, Object> input;
    @Getter private Map<String, Object> output;
    @Getter private Map<String, Object> database;

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
