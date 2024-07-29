package eu.itreegroup.s2.auth.viisp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

public class JAXBContextCache {

    private static Map<String, JAXBContext> cache = new ConcurrentHashMap<>();

    public static JAXBContext getJAXBContext(String packageName) throws JAXBException {
        JAXBContext context = cache.get(packageName);
        if (null == context) {
            synchronized (cache) {
                context = cache.get(packageName);
                if (null == context) {
                    context = JAXBContext.newInstance(packageName);
                    cache.put(packageName, context);
                }
            }
        }
        return context;
    }
}
