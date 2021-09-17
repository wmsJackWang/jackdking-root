package ace.attributor;

import com.google.common.base.Splitter;
import lombok.Data;

public interface IAttributor {

    Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();


    @Data
    static class AttributorValAndPram {

    }
}
