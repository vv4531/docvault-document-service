// ── AzureStorageProperties.java ──────────────────────────────────────────
package com.docvault.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "azure.storage")
public class AzureStorageProperties {

    private String accountName;
    private String accountKey;
    private String endpoint;
    private Container container = new Container();
    private Sas sas = new Sas();
    private Rehydration rehydration = new Rehydration();

    public static class Container {
        private String hot  = "documents-hot";
        private String cold = "documents-cold";
        public String getHot()          { return hot; }
        public void   setHot(String v)  { this.hot  = v; }
        public String getCold()         { return cold; }
        public void   setCold(String v) { this.cold = v; }
    }
    public static class Sas {
        private int expiryMinutes = 15;
        public int  getExpiryMinutes()      { return expiryMinutes; }
        public void setExpiryMinutes(int v) { this.expiryMinutes = v; }
    }
    public static class Rehydration {
        private String defaultPriority = "Standard";
        public String getDefaultPriority()         { return defaultPriority; }
        public void   setDefaultPriority(String v) { this.defaultPriority = v; }
    }

    public String    getAccountName()              { return accountName; }
    public void      setAccountName(String v)      { this.accountName = v; }
    public String    getAccountKey()               { return accountKey; }
    public void      setAccountKey(String v)       { this.accountKey = v; }
    public String    getEndpoint()                 { return endpoint; }
    public void      setEndpoint(String v)         { this.endpoint = v; }
    public Container getContainer()                { return container; }
    public void      setContainer(Container v)     { this.container = v; }
    public Sas       getSas()                      { return sas; }
    public void      setSas(Sas v)                 { this.sas = v; }
    public Rehydration getRehydration()            { return rehydration; }
    public void        setRehydration(Rehydration v) { this.rehydration = v; }
}
