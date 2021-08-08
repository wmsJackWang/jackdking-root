package ace.attributor;

import com.google.common.base.Splitter;

public interface IAttributor {

    Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();
}
