package lt.project.common.boot;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.jsr107.Eh107Configuration;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

    protected List<String> getCacheNames() {
        return Arrays.asList("roleActions", "userActions", "userSessions", "userMenu", "publicMenu", "dbPropertyManager", "refCodesManager",
                "refCodesManagerJSON", "userRolesManager");
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {

        CacheConfiguration<Object, Object> config = CacheConfigurationBuilder
                .newCacheConfigurationBuilder(Object.class, Object.class, ResourcePoolsBuilder.newResourcePoolsBuilder().heap(1000, EntryUnit.ENTRIES))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(200))).build();

        return cm -> {
            for (String cacheName : getCacheNames()) {
                cm.createCache(cacheName, Eh107Configuration.fromEhcacheCacheConfiguration(config));
                cm.enableStatistics(cacheName, true);
            }
        };
    }
}
