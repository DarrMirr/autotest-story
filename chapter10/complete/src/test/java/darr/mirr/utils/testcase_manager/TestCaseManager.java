package darr.mirr.utils.testcase_manager;

/**
 * Created by Darr Mirr on 27.09.17.
 *
 * Интерфейс описывающий взаимодействие с системой Тест кейс менеджера
 */
public interface TestCaseManager<T> {

    void addResult(T result, String screenshotPath);
}
