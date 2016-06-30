package org.yannis.ringtail.registry;

/**
 * Created by dell on 2016/6/30.
 */
public abstract class AbstractRegistry implements Registry {
    @Override
    public void register() {
        doRegister();
    }

    @Override
    public void unregister() {
        doUnregister();
    }

    protected abstract void doRegister();
    protected abstract void doUnregister();
}
