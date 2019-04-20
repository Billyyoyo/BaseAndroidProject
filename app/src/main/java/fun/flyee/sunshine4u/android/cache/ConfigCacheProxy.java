package fun.flyee.sunshine4u.android.cache;

import com.stormagain.easycache.Cache;
import com.stormagain.easycache.EasySpCache;
import com.stormagain.easycache.Key;
import com.stormagain.easycache.LoadCache;

/**
 * SharePreference封装类EasyCache的代理接口
 * Created by billyyoyo on 16-1-19.
 */
@EasySpCache(name = "configuration_cache")
public interface ConfigCacheProxy {

    @Cache
    void cacheConfiguration(@Key(value = "configuration") ConfigCache config);

    @LoadCache(key = "configuration", getClassType = ConfigCache.class)
    ConfigCache loadConfiguragion();

}
