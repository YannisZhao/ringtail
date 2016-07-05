package org.yannis.ringtail.registry;

/**
 * Created by dell on 2016/7/5.
 */
public interface RegistryFactory {
    /**
     * Get a singleton registry object
     */
    Registry getRegistry();
}
