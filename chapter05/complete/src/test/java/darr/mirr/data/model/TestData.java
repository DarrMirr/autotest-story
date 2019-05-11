package darr.mirr.data.model;

import lombok.*;

import java.util.List;

/**
 * Created by Darr Mirr on 30.08.17.
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
