package jackdking.multids.properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "multi.ds")
public class MultiJdbcProperties {

  private List<DsConfig> dsConfigs;

  private String defaultDs;

  public static class DsConfig {

    private String dsName;

    private String driverClassName;

    private String jdbcUrl;

    private String username;

    private String password;

    public String getDsName() {
      return dsName;
    }

    public void setDsName(String dsName) {
      this.dsName = dsName;
    }

    public String getDriverClassName() {
      return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
      this.driverClassName = driverClassName;
    }

    public String getJdbcUrl() {
      return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
      this.jdbcUrl = jdbcUrl;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    @Override
    public String toString() {
      return "DsConfig [dsName="
          + dsName
          + ", driverClassName="
          + driverClassName
          + ", jdbcUrl="
          + jdbcUrl
          + ", username="
          + username
          + ", password="
          + password
          + "]";
    }
  }

  public List<DsConfig> getDsConfigs() {
    return dsConfigs;
  }

  public void setDsConfigs(List<DsConfig> dsConfigs) {
    this.dsConfigs = dsConfigs;
  }

  public String getDefaultDs() {
    return defaultDs;
  }

  public void setDefaultDs(String defaultDs) {
    this.defaultDs = defaultDs;
  }

  @Override
  public String toString() {
    return "MultiJdbcProperties [dsConfigs=" + dsConfigs + ", defaultDs=" + defaultDs + "]";
  }
}
