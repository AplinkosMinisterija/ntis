package eu.itreegroup.spark.modules.admin.logic.forms;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.management.JMX;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.ehcache.core.statistics.CacheStatistics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import eu.itreegroup.spark.app.common.FormBase;
import eu.itreegroup.spark.app.tools.DBPropertyManager;
import eu.itreegroup.spark.modules.admin.logic.forms.model.CacheInfo;

/**
 * Class implements "Cache management" form logic.
 * 
 *
 */
@Component
public class SprCacheManager extends FormBase {

    private static final Logger log = LoggerFactory.getLogger(SprCacheManager.class);

    public static String CLEAR_CACHE = "CLEAR_CACHE";

    public static String CLEAR_CACHE_DESC = "Action will clear selected cache";

    public static String CHANGE_CACHE_STATUS = "CHANGE_CACHE_STATUS";

    public static String CHANGE_CACHE_STATUS_DESC = "Acction will allow turn off/on cache usage (disable/enable cache usage)";

    public static String ENABLED_PROPERTY_SUFFIX = "_ENABLED";

    @Autowired
    CacheManager cacheManager;

    @Autowired
    DBPropertyManager dbPropertyManager;

    /**
     * Method will return Angula form name. Same name should be defined in DB as well (in case if enabled data synchronization with db 
     * it name will be used for form definition in DB).
     */
    @Override
    public String getFormName() {
        return "SPR_CACHE_MANAGER";
    }

    /**
     * Function will define setup information that is related with current form. In this form should be defined:
     *       1) from name (for form definition should be used method "setFormInfo")
     *       2) form available actions (for action definition should be used method "addFormAction"). In case of define standard CRUD form
     *          action can be used method by name: addFormActionCRUD. 
     */
    @Override
    public void defineFormAndActions() {
        setFormInfo(getFormName(), "Chache list used in spark java back end", "Chache list used in spark java back end");
        addFormActions(FormBase.ACTION_READ);
        addFormAction(CLEAR_CACHE, CLEAR_CACHE_DESC, CLEAR_CACHE_DESC);
        addFormAction(CHANGE_CACHE_STATUS, CHANGE_CACHE_STATUS_DESC, CHANGE_CACHE_STATUS_DESC);
    }

    public CacheStatistics getCacheStatisticsMXBean(final String cacheName) throws MalformedObjectNameException {
        final MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("*:type=CacheStatistics,*,Cache=" + cacheName);

        Set<ObjectName> beans = mbeanServer.queryNames(name, null);
        if (beans.isEmpty()) {
            return null;
        }
        ObjectName[] objArray = beans.toArray(new ObjectName[beans.size()]);
        return JMX.newMBeanProxy(mbeanServer, objArray[0], CacheStatistics.class);
    }

    /**
     * Function will extract information about caches used in system. Will be formated list of information about used caches.
     * @param conn - connection to the db.
     * @return list of caches used in current system.
     * @throws Exception
     */
    public ArrayList<CacheInfo> getAllUsedCaches(Connection conn) throws Exception {
        checkIsFormActionAssigned(conn, SprPropertiesBrowse.ACTION_READ);
        log.debug("=======>Start cache status<========");
        Collection<String> definedCaches = this.cacheManager.getCacheNames();
        ArrayList<CacheInfo> cacheList = new ArrayList<CacheInfo>();
        for (String cacheName : definedCaches) {
            CacheInfo cacheInfo = new CacheInfo();
            cacheInfo.setName(cacheName);
            CacheStatistics cacheStatisticsMXBean = this.getCacheStatisticsMXBean(cacheName);
            if (cacheStatisticsMXBean != null) {
                cacheInfo.setEvictions(cacheStatisticsMXBean.getCacheEvictions());
                long expirations;
                try {
                    expirations = cacheStatisticsMXBean.getCacheExpirations();
                } catch (Exception e) {
                    expirations = 0;
                }
                cacheInfo.setExpirations(expirations);
                cacheInfo.setGets(cacheStatisticsMXBean.getCacheGets());
                cacheInfo.setHitPercentage(BigDecimal.valueOf(cacheStatisticsMXBean.getCacheHitPercentage()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                cacheInfo.setHits(cacheStatisticsMXBean.getCacheHits());
                cacheInfo.setMisses(cacheStatisticsMXBean.getCacheMisses());
                cacheInfo.setMissPercentage(BigDecimal.valueOf(cacheStatisticsMXBean.getCacheMissPercentage()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                cacheInfo.setPuts(cacheStatisticsMXBean.getCachePuts());
                cacheInfo.setRemovals(cacheStatisticsMXBean.getCacheRemovals());
            }
            cacheList.add(cacheInfo);
        }
        return cacheList;
    }

    /**
     * Function removes clears specific cache by name
     * @param cacheName - name of the cache that should be cleared
     */
    public void clearCache(Connection conn, String cacheName) throws Exception {
        log.debug("clearCache: " + cacheName);
        this.checkIsFormActionAssigned(conn, CLEAR_CACHE);
        Cache cache = this.cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.invalidate();
            CacheStatistics cacheStatisticsMXBean = this.getCacheStatisticsMXBean(cacheName);
            if (cacheStatisticsMXBean != null) {
                cacheStatisticsMXBean.clear();
            }
        }
    }

    /**
     * Function will clear all cache used in system
     * @param conn - connection to the DB
     * @throws Exception
     */
    public void clearAllCaches(Connection conn) throws Exception {
        this.checkIsFormActionAssigned(conn, CLEAR_CACHE);
        Collection<String> definedCaches = this.cacheManager.getCacheNames();
        for (String cacheName : definedCaches) {
            this.clearCache(conn, cacheName);
        }
    }

}
