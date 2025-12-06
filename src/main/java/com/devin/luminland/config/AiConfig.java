package com.devin.luminland.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class AiConfig {

    public static final String API_KEY = System.getenv("AI_DASHSCOPE_API_KEY");

//    @Bean(name = "qwenImageEditModel")
//    public DashScopeImageModel dashScopeImageModel() {
//        DashScopeImageApi dashScopeImageApi = DashScopeImageApi.builder()
//                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
//                .build();
//        return DashScopeImageModel.builder().dashScopeApi(dashScopeImageApi).build();
//
//    }
//    @Bean(name = "qwenImageEditModel")
//    public DashScopeChatModel qwenImageEditModel() {
//        // 创建 DashScope API 实例
//        DashScopeApi dashScopeApi = DashScopeApi.builder()
//                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
//                .build();
//        DashScopeChatOptions options = DashScopeChatOptions.builder()
//                .withModel("qwen-image-edit-plus")           // 模型名称
//                .withMultiModel(true)
//                .withTemperature(0.7)
//                .withTopP(0.9)                     // Top-P 采样
//                .build();
//        // 创建 ChatModel
//        return DashScopeChatModel.builder()
//                .defaultOptions(options)
//
//                .dashScopeApi(dashScopeApi)
//                .build();
//    }
//
//    @Bean(name = "qwen3VlModel")
//    @Primary
//    public DashScopeChatModel qwen3VlModel() {
//        // 创建 DashScope API 实例
//        DashScopeApi dashScopeApi = DashScopeApi.builder()
//                .apiKey(System.getenv("AI_DASHSCOPE_API_KEY"))
//                .build();
//        DashScopeChatOptions options = DashScopeChatOptions.builder()
//                .withModel("qwen3-vl-8b-instruct")           // 模型名称
//                .withMultiModel(true)
//                .withTemperature(0.7)              // 温度参数
//                .withTopP(0.9)                     // Top-P 采样
//                .build();
//        // 创建 ChatModel
//        return DashScopeChatModel.builder()
//                .defaultOptions(options)
//                .dashScopeApi(dashScopeApi)
//                .build();
//    }
    @Bean(name = "qwenOmniModel")
    public DashScopeChatModel qwenOmniModel() {
        // 创建 DashScope API 实例
        DashScopeApi dashScopeApi = DashScopeApi.builder()
                .apiKey(API_KEY)
                .build();
        DashScopeChatOptions options = DashScopeChatOptions.builder()
                .withHttpHeaders(Map.of("X-DashScope-OssResourceResolve", "enable"))
                .withModel("qwen2.5-omni-7b")           // 模型名称
                .withMultiModel(true)
                .withTemperature(0.7)              // 温度参数
                .withTopP(0.9)                     // Top-P 采样
                .build();
        // 创建 ChatModel
        return DashScopeChatModel.builder()
                .defaultOptions(options)
                .dashScopeApi(dashScopeApi)
                .build();
    }


    @Bean(name = "qwenImageEditModel")
    public DashScopeImageModel qwenImageEditModel() {

        DashScopeImageApi dashScopeImageApi = DashScopeImageApi.builder()
                .apiKey(API_KEY)
                .build();
        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel("qwen-image-edit-plus")           // 模型名称
                .build();
        return DashScopeImageModel.builder()
                .dashScopeApi(dashScopeImageApi)
                .defaultOptions(options)
                .build();

    }
    @Bean(name = "qwen3VlModel")
    public DashScopeImageModel qwen3VlModel() {

        DashScopeImageApi dashScopeImageApi = DashScopeImageApi.builder()
                .apiKey(API_KEY)
                .build();
        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel("qwen3-vl-8b-instruct")           // 模型名称
                .build();
        return DashScopeImageModel.builder()
                .dashScopeApi(dashScopeImageApi)
                .defaultOptions(options)
                .build();

    }
}
