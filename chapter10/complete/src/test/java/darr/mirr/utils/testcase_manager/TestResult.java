package darr.mirr.utils.testcase_manager;

/**
 * Created by Pol Mir on 27.09.17.
 * <p>
 * Класс описывающий результат прохождения теста
 */
public class TestResult {
    private String caseID;
    private String status;
    private String description;

    protected TestResult() {
    }

    public TestResult(String caseID, String status, String description) {
        this.caseID = caseID;
        this.status = status;
        this.description = description;
    }

    public String getCaseID() {
        return caseID;
    }

    public String getStatus() {
        return status;
    }

    public String getDescription() {
        return description;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "caseID: " + caseID + ", " +
                "status: " + status + ", " +
                " description: " + description;
    }
}
