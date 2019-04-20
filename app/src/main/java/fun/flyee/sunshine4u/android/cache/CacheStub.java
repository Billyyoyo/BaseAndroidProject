package fun.flyee.sunshine4u.android.cache;


/**
 * Created by billyyoyo on 16-4-6.
 */
public class CacheStub {

    private static CacheStub stub;

    private ConfigCache configCache;


    private ConfigCacheProxy configCacheProxy;

    private CacheStub(){

    }

    public static CacheStub getInstance(){
        if(stub == null){
            stub = new CacheStub();
        }
        return stub;
    }

    public ConfigCache getConfigCache() {
        return configCache;
    }

    public void setConfigCache(ConfigCache configCache) {
        this.configCache = configCache;
    }

    public ConfigCacheProxy getConfigCacheProxy() {
        return configCacheProxy;
    }

    public void setConfigCacheProxy(ConfigCacheProxy configCacheProxy) {
        this.configCacheProxy = configCacheProxy;
    }
}
