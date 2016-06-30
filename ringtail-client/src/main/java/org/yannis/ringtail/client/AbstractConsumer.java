package org.yannis.ringtail.client;

/**
 * Created by dell on 2016/6/30.
 */
public abstract class AbstractConsumer implements Consumer {

    @Override
    public void subscribe() {

    }

    @Override
    public void Unsubscribe() {

    }

    protected abstract void doSubscribe() throws Exception;
    protected abstract void doUnsubscribe();
}
