package property;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "propspring.my-example")
@Data
public class PropProperty {

    private Map<String,String> mapvalue;

    private List<String> listvalue;

    private List<String> listArray;
}
