package fun.flyee.sunshine4u.android.cache;

import com.stormagain.easycache.EasyCacheManager;

/**
 * Created by billyyoyo on 16-1-27.
 */
public class ConfigCache {

    public String APP;

    public Long loginUserId = 0l;

    public synchronized static ConfigCache get() {
        if (CacheStub.getInstance().getConfigCache() == null) {
            loadCacheProxy();
            CacheStub.getInstance().setConfigCache(CacheStub.getInstance().getConfigCacheProxy().loadConfiguragion());
        }
        if (CacheStub.getInstance().getConfigCache() == null) {
            CacheStub.getInstance().setConfigCache(new ConfigCache());
        }
        return CacheStub.getInstance().getConfigCache();
    }

    public static void save() {
        loadCacheProxy();
        CacheStub.getInstance().getConfigCacheProxy().cacheConfiguration(CacheStub.getInstance().getConfigCache());
    }

    private static synchronized void loadCacheProxy() {
        if (CacheStub.getInstance().getConfigCacheProxy() == null) {
            CacheStub.getInstance().setConfigCacheProxy(EasyCacheManager.getInstance().getCacheProxy().create(ConfigCacheProxy.class));
        }
    }

    public static boolean isLogined() {
        return get().loginUserId != null && get().loginUserId > 0;
    }

}
