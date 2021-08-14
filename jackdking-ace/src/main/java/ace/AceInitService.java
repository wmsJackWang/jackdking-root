package ace;


import com.google.common.collect.Multimap;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public interface AceInitService {

    public void init();
    public void parseAnnoation();
    public void parseAttributor();
    public void parseClassifier();
    public void parseExecutor();
    public void complete();
}
