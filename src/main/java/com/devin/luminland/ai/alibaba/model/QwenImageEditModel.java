package com.devin.luminland.ai.alibaba.model;

import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversation;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationParam;
import com.alibaba.dashscope.aigc.multimodalconversation.MultiModalConversationResult;
import com.alibaba.dashscope.common.MultiModalMessage;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.alibaba.dashscope.exception.UploadFileException;
import com.alibaba.dashscope.utils.Constants;
import com.alibaba.dashscope.utils.JsonUtils;
import com.devin.luminland.config.AiConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.*;

/**
 * @author devin
 */
public class QwenImageEditModel {

    static {
        // 以下为中国（北京）地域url，若使用新加坡地域的模型，需将url替换为：https://dashscope-intl.aliyuncs.com/api/v1
        Constants.baseHttpApiUrl = "https://dashscope.aliyuncs.com/api/v1";
    }

    public static MultiModalConversationResult call(List<String> images,String message,String negativePrompt,int n, String size) throws ApiException, NoApiKeyException, UploadFileException, IOException {

        if(CollectionUtils.isEmpty(images) || StringUtils.isBlank(message)){
            throw new IllegalArgumentException("images and message can not be empty");
        }

        MultiModalConversation conv = new MultiModalConversation();
        List<Map<String, Object>> content = new ArrayList<>();
        for (String image : images) {
            content.add(Collections.singletonMap("image",image));
        }
        content.add(Collections.singletonMap("text", message));

        // 模型支持输入1-3张图片
        MultiModalMessage userMessage = MultiModalMessage.builder().role(Role.USER.getValue())
                .content(content)
                .build();
        // qwen-image-edit-plus支持输出1-6张图片，此处以2张为例
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("watermark", false);
        parameters.put("negative_prompt", negativePrompt == null ? "" : negativePrompt);
        parameters.put("n", n);
        parameters.put("prompt_extend", true);
        // 仅当输出图像数量n=1时支持设置size参数，否则会报错
        if(n == 1 && size == null){
            parameters.put("size", "1024*1024");
        }
         parameters.put("size", size);

        MultiModalConversationParam param = MultiModalConversationParam.builder()
                .apiKey(AiConfig.API_KEY)
                .model("qwen-image-edit-plus")
                .messages(Collections.singletonList(userMessage))
                .parameters(parameters)
                .build();

        return conv.call(param);
    }

    public static void main(String[] args) {
        try {
            MultiModalConversationResult result =call(Arrays.asList("https://help-static-aliyun-doc.aliyuncs.com/file-manage-files/zh-CN/20250925/thtclx/input1.png"
                    , "https://help-static-aliyun-doc.aliyuncs.com/file-manage-files/zh-CN/20250925/iclsnx/input2.png"),
                    "图1中的女生穿着图2中的黑色裙子飞起来扣篮",null, 1, "1024*1024");
            System.out.println(JsonUtils.toJson( result));
        } catch (ApiException | NoApiKeyException | UploadFileException | IOException e) {
            System.out.println(e.getMessage());
        }
    }
}