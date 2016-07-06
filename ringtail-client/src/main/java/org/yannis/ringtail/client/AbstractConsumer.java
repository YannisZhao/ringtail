package org.yannis.ringtail.client;

import org.yannis.ringtail.client.config.ReferenceConfig;

/**
 * Created by dell on 2016/6/30.
 */
public abstract class AbstractConsumer implements Consumer {

    @Override
    public void subscribe(ReferenceConfig config) throws Exception {
        doSubscribe(config);
    }

    @Override
    public void Unsubscribe(ReferenceConfig config) {
        doUnsubscribe(config);
    }

    protected abstract void doSubscribe(ReferenceConfig config) throws Exception;
    protected abstract void doUnsubscribe(ReferenceConfig config);
}
