package com.devin.luminland.ai.alibaba.model;

import com.alibaba.dashscope.aigc.multimodalconversation.*;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.JsonUtils;
import com.devin.luminland.config.AiConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class MultiModalConversationUsage {

    private static final String modelName = "qwen-vl-plus";


    public static MultiModalConversationResult simpleMultiModalConversationCall(List<String> imageUrls,String  message) throws ApiException, NoApiKeyException, UploadFileException {
        if(CollectionUtils.isEmpty(imageUrls) || StringUtils.isBlank(message)){
            throw new IllegalArgumentException("images and message can not be empty");
        }
        MultiModalConversation conv = new MultiModalConversation();
        List<MultiModalMessageItemBase> contents = new ArrayList<>();
        MultiModalMessageItemText userText = new MultiModalMessageItemText(message);
        for (String imageUrl : imageUrls) {
            MultiModalMessageItemImage itemImage = new MultiModalMessageItemImage(imageUrl);
            contents.add(itemImage);
        }
        contents.add(userText);


        MultiModalConversationMessage userMessage = MultiModalConversationMessage.builder()
                .role(Role.USER.getValue())
                .content(contents)
                .build();
        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .model(MultiModalConversationUsage.modelName)
                .apiKey(AiConfig.API_KEY)
                .vlHighResolutionImages(true)
                .vlEnableImageHwOutput(true)
//                .incrementalOutput(true)
                .message(userMessage).build();
        MultiModalConversationResult result = conv.call(param);
        System.out.print(JsonUtils.toJson(result));
        return result;

    }

//    public static void main(String[] args) {
//        try {
//            simpleMultiModalConversationCall();
//        } catch (ApiException | NoApiKeyException | UploadFileException /*| IOException*/ e) {
//            System.out.println(e.getMessage());
//        }
//        System.exit(0);
//    }

}