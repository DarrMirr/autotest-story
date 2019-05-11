package darr.mirr.utils.case_providers;

import darr.mirr.data.SystemProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darr Mirr on 12.09.17.
 *
 * This class should use when CASE_ID_PROPERTY set
 */
public class SimpleCaseProvider implements CaseProvider {
    private static final Logger logger = LoggerFactory.getLogger(SimpleCaseProvider.class);

    @Override
    public List<String> getCaseID() {
        if (isValidCaseID()) {
            List<String> caseIDs = new ArrayList<>();
            caseIDs.add(SystemProperties.CASE_ID_PROPERTY);
            return caseIDs;
        }
        logger.error("Incorrect case ID : {}", SystemProperties.CASE_ID_PROPERTY);
        return null;
    }

    private boolean isValidCaseID() {
        return SystemProperties.CASE_ID_PROPERTY != null && SystemProperties.CASE_ID_PROPERTY.length() > 0;
    }
}
