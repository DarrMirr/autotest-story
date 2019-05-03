package pol.mirr.utils.case_providers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pol Mirr on 15.09.17.
 *
 * This class mock interaction filterWith Test Management System
 */
public class MockTestManager implements CaseProvider {

    @Override
    public List<String> getCaseID() {
        List<String> caseIDs = new ArrayList<>();
        caseIDs.add("1");
        caseIDs.add("3");
        caseIDs.add("5");
        return caseIDs;
    }
}
