package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.attributor;

import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceContext;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceResult;
import com.google.common.base.Splitter;

public interface IAttributor {

    Splitter SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();
}
