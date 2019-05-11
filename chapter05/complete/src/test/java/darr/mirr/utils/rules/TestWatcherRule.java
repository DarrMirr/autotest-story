package darr.mirr.utils.rules;

import org.junit.internal.AssumptionViolatedException;
import org.junit.rules.MethodRule;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.MultipleFailureException;
import org.junit.runners.model.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Darr Mirr on 03.09.17.
 *
 * This is clone TestWatcher class from org.junit.rules package.
 * Main difference is interface which current class implement
 */
public abstract class TestWatcherRule implements MethodRule {

    @Override
    public Statement apply(Statement statement, FrameworkMethod frameworkMethod, Object o) {
        return new Statement() {
            public void evaluate() throws Throwable {
                List<Throwable> errors = new ArrayList();
                TestWatcherRule.this.startingQuietly(frameworkMethod, errors);

                try {
                    statement.evaluate();
                    TestWatcherRule.this.succeededQuietly(frameworkMethod, errors);
                } catch (AssumptionViolatedException var7) {
                    errors.add(var7);
                } catch (Throwable var8) {
                    errors.add(var8);
                    TestWatcherRule.this.failedQuietly(var8, frameworkMethod, errors);
                } finally {
                    TestWatcherRule.this.finishedQuietly(frameworkMethod, errors);
                }

                MultipleFailureException.assertEmpty(errors);
            }
        };
    }

    private void succeededQuietly(FrameworkMethod frameworkMethod, List<Throwable> errors) {
        try {
            this.succeeded(frameworkMethod);
        } catch (Throwable var4) {
            errors.add(var4);
        }

    }

    private void failedQuietly(Throwable e, FrameworkMethod frameworkMethod, List<Throwable> errors) {
        try {
            this.failed(e, frameworkMethod);
        } catch (Throwable var5) {
            errors.add(var5);
        }

    }

    private void startingQuietly(FrameworkMethod frameworkMethod, List<Throwable> errors) {
        try {
            this.starting(frameworkMethod);
        } catch (Throwable var4) {
            errors.add(var4);
        }

    }

    private void finishedQuietly(FrameworkMethod frameworkMethod, List<Throwable> errors) {
        try {
            this.finished(frameworkMethod);
        } catch (Throwable var4) {
            errors.add(var4);
        }

    }

    protected void succeeded(FrameworkMethod frameworkMethod) {
    }

    protected void failed(Throwable e, FrameworkMethod frameworkMethod) {
    }


    protected void starting(FrameworkMethod frameworkMethod) {
    }

    protected void finished(FrameworkMethod frameworkMethod) {
    }
}
