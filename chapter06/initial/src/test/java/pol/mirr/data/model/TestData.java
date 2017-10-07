package pol.mirr.data.model;

import java.util.List;

/**
 * Created by Pol Mirr on 30.08.17.
 *
 * Model test data which keep caseID and dataset list
 */
public class TestData {
    private String caseID;
    private List<Dataset> sets;

    protected TestData() {
    }

    public TestData(String caseID, List<Dataset> sets) {
        this.caseID = caseID;
        this.sets = sets;
    }

    public String getCaseID() {
        return caseID;
    }

    public List<Dataset> getSets() {
        return sets;
    }

    @Override
    public String toString() {
        return "CaseID=" + caseID;
    }
}
