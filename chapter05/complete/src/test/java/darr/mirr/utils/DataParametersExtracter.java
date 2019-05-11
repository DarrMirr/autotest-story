package darr.mirr.utils;

import com.tngtech.java.junit.dataprovider.DataProviderFrameworkMethod;
import org.junit.runners.model.FrameworkMethod;

import java.lang.reflect.Field;

/**
 * Created by Darr Mirr on 03.09.17.
 *
 * Extract field value from FrameworkMethod
 */
public class DataParametersExtracter {

    private DataParametersExtracter() {
    }

    public static Object[] extract(FrameworkMethod frameworkMethod) {
        if (frameworkMethod instanceof DataProviderFrameworkMethod) {
            Object parametersRaw = getFieldValue(frameworkMethod, "parameters");
            return parametersRaw instanceof Object[] ? (Object[]) parametersRaw : null;
        }
        return null;
    }

    private static Object getFieldValue(Object instance, String fieldName) {
        try {
            Class klass = instance.getClass();
            Field field = klass.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
