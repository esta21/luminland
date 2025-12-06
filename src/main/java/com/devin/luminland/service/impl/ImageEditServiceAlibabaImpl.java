package com.devin.luminland.service.impl;

import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.graph.OverAllState;
import com.alibaba.cloud.ai.graph.RunnableConfig;
import com.alibaba.cloud.ai.graph.agent.ReactAgent;
import com.alibaba.cloud.ai.graph.agent.flow.agent.SequentialAgent;
import com.devin.luminland.ai.alibaba.oss.AliPublicUrlHandler;
import com.devin.luminland.service.ImageEditService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ImageEditServiceAlibabaImpl implements ImageEditService {

    private String apiKey = System.getenv("AI_DASHSCOPE_API_KEY");

    @Resource(name = "qwenOmniModel")
    private ChatModel qwenOmniModel;

    @Resource(name = "qwen3VlModel")
    private ChatModel qwen3VlModel;

    @Resource(name = "qwenImageEditModel")
    private ChatModel qwenImageEditModel;

    public void beautifyImageAuto(List<String> imagePaths) {
        // 调用AliPublicUrlHandler 上传图片，获取图片的公共访问URL
        try {
            List<String> publicUrls = AliPublicUrlHandler.uploadImagesAndGetPublicUrls(qwen3VlModel.getDefaultOptions().getModel(),imagePaths);
            // publicUrls 转换成List<Media> 类型
            List<Media> medias = publicUrls.stream().map(url -> {
                return Media.builder().mimeType(MimeTypeUtils.IMAGE_JPEG).data(url)
                        .build();
            }).toList();
            log.info("图片上传成功，共" + publicUrls.size() + "张图片");

            UserMessage userMsg = UserMessage.builder()
                    .text("请对图片进行评论并美化")
                    .media(medias)
                    .build();

            ReactAgent commentImageAgent = ReactAgent.builder()
                    .name("comment_image_agent")
                    .model(qwen3VlModel)
                    .description("一个可以对图片进行评论的agent，输入是一张图片的URL，输出是对图片的评论文字")
                    .chatOptions(DashScopeChatOptions.fromOptions((DashScopeChatOptions) qwen3VlModel.getDefaultOptions()))
                    .enableLogging(true)
                    .instruction("""
                            
                            """)

                    .returnReasoningContents(false)
                    .outputKey("comment_image")
                    .build();



//            ReactAgent beautifyImageAgent = ReactAgent.builder()
//                    .name("beautify_image_agent")
//                    .model(qwenImageEditModel)
//                    .description("一个可以对图片进行美化的agent，输入是一张图片的URL和评论文字，输出是评论文字和美化后的图片URL")
//                    .enableLogging(true)
//                    .instruction("""
//                            ## 角色
//                            你是一名专业的摄影师，拥有着高超的修图技术，能够基于给出的建议完成图片的优化。
//
//                            ## 任务
//                            基于不足点评析、优点评析、改进建议，对图片进行美化处理。请根据评论内容，调整图片的构图、色彩、光影、细节等方面，使图片达到最佳效果
//
//                            ## 输出要求
//                            1. 图片美化后，需要生成一张新的图片，并提供该图片的公共访问URL
//                            2. 图片的尺寸、分辨率、格式等需要保持和原图一致
//                            3. 美化后的图片需要符合专业摄影的标准，具有较高的质量，图片质量不被降低，并尽可能高清
//
//
//                            ## 输出格式
//                            总体分数: X/10
//                            不足点评析
//                            1. xxx
//                            2. xxx
//                            优点评析
//                            1. xxx
//                            2. xxx
//                            改进建议
//                            1. xxx
//                            2. xxx
//                            美化后图片URL: <图片的公共访问URL>
//                            """)
//
//                    .returnReasoningContents(false)
//                    .outputKey("beautify_image")
//                    .build();


            // 每个子agent的推理内容，下一个执行的子agent会看到上一个子agent的推理内容
            SequentialAgent blogAgent = SequentialAgent.builder()
                    .name("beautify_image_auto_agent")
                    .subAgents(List.of(commentImageAgent))
                    .build();

            Optional<OverAllState> result = blogAgent.invoke(userMsg, RunnableConfig.builder().addMetadata("multiModel",true).build());

            if (result.isPresent()) {
                // 消息历史将包含所有工具调用和推理过程
                List<Message> messages = (List<Message>) result.get().value("messages").orElse(List.of());
                System.out.println("消息数量: " + messages.size()); // 包含所有中间步骤
            }



        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("美化图片失败", e);
        }

    }
}
