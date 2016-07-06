package org.yannis.ringtail.registry.bean;

/**
 * Created by dell on 2016/7/6.
 */
public class ServiceBean {
    private String appName;
    private String interfaceName;
    private String version;

    public ServiceBean(String appName, String interfaceName, String version) {
        this.appName = appName;
        this.interfaceName = interfaceName;
        this.version = version;
    }

    public String toPath(){
        return appName + "_" + interfaceName + "_" + version;
    }

    public static String toPath(ServiceBean bean){
        return bean.getAppName() + "_" + bean.getInterfaceName() + "_" + bean.getVersion();
    }

    public static ServiceBean fromPath(String path){
        String[] components = path.split("_");
        if(components == null || components.length != 3){
            throw new IllegalArgumentException("Cannot parse service bean from path: "+ path);
        }

        return new ServiceBean(components[0], components[1], components[2]);
    }

    public String getAppName() {
        return appName;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getVersion() {
        return version;
    }
}
