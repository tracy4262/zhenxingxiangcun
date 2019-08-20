
package com.ovit.nswy.wiki.config.search;

import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Created by ${huipei.x} on 2017-10-18.
 */

@ConfigurationProperties(prefix = "spring.elasticsearch")
public class ElasticsearchProperties {

    private String esHost;
    private int esPort;
    private String esClusterName;

    public String getEsHost() {
        return esHost;
    }

    public void setEsHost(String esHost) {
        this.esHost = esHost;
    }

    public int getEsPort() {
        return esPort;
    }

    public void setEsPort(int esPort) {
        this.esPort = esPort;
    }

    public String getEsClusterName() {
        return esClusterName;
    }

    public void setEsClusterName(String esClusterName) {
        this.esClusterName = esClusterName;
    }
}

