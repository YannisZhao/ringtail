package org.yannis.ringtail.client;

import org.yannis.ringtail.client.config.ReferenceConfig;

/**
 * Created by dell on 2016/6/30.
 */
public interface Consumer {
    void subscribe(ReferenceConfig config) throws Exception;
    void Unsubscribe(ReferenceConfig config);
}
