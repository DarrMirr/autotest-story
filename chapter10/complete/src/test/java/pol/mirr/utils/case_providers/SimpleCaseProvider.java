package pol.mirr.utils.case_providers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static pol.mirr.data.SystemProperties.CASE_ID_PROPERTY;

/**
 * Created by Pol Mirr on 12.09.17.
 *
 * This class should use when CASE_ID_PROPERTY set
 */
public class SimpleCaseProvider implements CaseProvider {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCaseProvider.class);

    @Override
    public List<String> getCaseID() {
        if (isValidCaseID()) {
            List<String> caseIDs = new ArrayList<>();
            caseIDs.add(CASE_ID_PROPERTY);
            return caseIDs;
        }
        logger.error("Incorrect case ID : {}", CASE_ID_PROPERTY);
        return null;
    }

    private boolean isValidCaseID() {
        return CASE_ID_PROPERTY != null && CASE_ID_PROPERTY.length() > 0;
    }
}
