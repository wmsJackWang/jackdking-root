/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jackdking.dt.common.toolkit;

import jackdking.dt.common.monitor.AbstractMessage;
import jackdking.dt.common.monitor.Message;
import jackdking.dt.common.monitor.MessageWrapper;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Message convert.
 */
public class MessageConvert {

    /**
     * {@link Message} to {@link MessageWrapper}.
     *
     * @param message
     * @return
     */
    public static MessageWrapper convert(Message message) {
        MessageWrapper wrapper = new MessageWrapper();
        wrapper.setResponseClass(message.getClass());
        wrapper.setMessageType(message.getMessageType());
        List<Map<String, Object>> messageMapList = new ArrayList();
        List<Message> messages = message.getMessages();
        messages.forEach(each -> {
            String eachVal = JSONUtil.toJSONString(each);
            Map mapObj = JSONUtil.parseObject(eachVal, Map.class);
            messageMapList.add(mapObj);
        });
        wrapper.setContentParams(messageMapList);
        return wrapper;
    }

    /**
     * {@link MessageWrapper} to {@link Message}.
     *
     * @param messageWrapper
     * @return
     */
    @SneakyThrows
    public static Message convert(MessageWrapper messageWrapper) {
        AbstractMessage message = (AbstractMessage) messageWrapper.getResponseClass().getDeclaredConstructor().newInstance();
        List<Map<String, Object>> contentParams = messageWrapper.getContentParams();
        List<Message> messages = new ArrayList();
        contentParams.forEach(each -> {
            String eachVal = JSONUtil.toJSONString(each);
            Message messageObj = JSONUtil.parseObject(eachVal, messageWrapper.getResponseClass());
            messages.add(messageObj);
        });
        message.setMessages(messages);
        message.setMessageType(messageWrapper.getMessageType());
        return message;
    }
}
