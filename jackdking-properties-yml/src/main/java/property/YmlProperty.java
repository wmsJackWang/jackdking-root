package property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "ymlspring.my-example")
@Data
public class YmlProperty {

    private Map<String,String> mapvalue;

    private List<String> listvalue;
}
