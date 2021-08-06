package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace;

import com.taobao.tddl.common.model.Atom;

import java.util.concurrent.atomic.AtomicBoolean;

public interface AceInitService {

    public void init();
    public void parseAnnoation();
    public void parseAttributor();
    public void parseClassifier();
    public void parseExecutor();
    public void complete();
}
