package org.yannis.ringtail.rpc.proxy.data;

/**
 * Created by dell on 2016/7/4.
 */
public class Dog implements Animal {

    @Override
    public String speek(int vtype, String words) {
        return String.format("Dog say %s with voice type %d", words, vtype);
    }
}
