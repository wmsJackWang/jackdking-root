package com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.attributor;

import com.alibaba.fastjson.JSONObject;
import com.autonavi.aos.tmp.order.kernel.api.model.OrderDetailInfo;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceContext;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.AceResult;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.anoation.Attributor;
import com.autonavi.aos.tmp.settle.hotel.gather.prepay.ace.anoation.Ruler;
import com.autonavi.aos.tmp.settle.voucher.gather.common.VirtualOrderCenterInfo;

/*
 * 消息tag校验器
 * 该校验器，检测消息的tag。
 */
@Attributor(name = "messageTagAttributor" , fieldName = "tag")
public class TopicTagAttributor implements IAttributor{

    @Ruler(name = "checkTopicTag")
    public AceResult attribute(AceContext aceContext) {
        JSONObject contextDataParam = (JSONObject) aceContext.getDataParam();
        VirtualOrderCenterInfo upperEntity = contextDataParam.getObject("upperEntity",VirtualOrderCenterInfo.class);
        OrderDetailInfo orderDetail = contextDataParam.getObject("orderDetail",OrderDetailInfo.class);
        String topicTags = contextDataParam.getString("topicTags");
        boolean result =  false;
        if(SPLITTER.splitToList(topicTags).contains(upperEntity.getTags())) {
            result = true;
        }

        AceResult checkTopicTagResult = new AceResult(result,null);

        return checkTopicTagResult;
    }

}