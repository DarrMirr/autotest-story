package pol.mirr.data.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Created by Pol Mirr on 30.08.17.
 *
 * Model test data which keep caseID and dataset list
 */
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TestData {
    @Getter private String caseID;
    @Getter private List<Dataset> sets;

    @Override
    public String toString() {
        return "CaseID=" + caseID;
    }
}
