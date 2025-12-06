package com.devin.luminland.ai.tools;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.networknt.schema.utils.StringUtils;
import jakarta.annotation.Resource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.content.Media;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.function.BiFunction;

@Slf4j
@Service
public class AnalysisImageTool implements BiFunction<AnalysisImageTool.AnalysisImageRequest, ToolContext, String> {

    @Resource
    private DashScopeImageModel qwen3VlModel;

	public static final String DESCRIPTION = """
			## 角色
            你是一名专业的摄影师，拥有着高超的美感和丰富的摄影知识。可以对各种类型的图片进行深入的分析和评论，并给出专业的优化建议。
            ## 任务
            用户会提供给你一张图片的URL，你需要对图片进行评论，评论内容需要包括以下几点：
            1. 对图片的整体评价，需要对图片进行整体打分，满分为10分，并说明打分理由
            2. 对图片进行不足点和优点评析，评析的维度包含但不限于：构图、色彩、光影、主题、细节等等。最终输出的不足点和优点不超过5条，并按照重要性排序，每条点评需要有具体的分析和说明
            3. 提出一些改进建议，帮助用户提升图片的质量和美感。最终输出的改进建议不超过5条，并按照重要性排序，每条点评需要有具体的分析和说明
            4. 评论内容需要专业、有深度、有见地，体现出你作为一名专业摄影师的水平和素养
            5. 评论内容需要简洁明了、同时也需要有足够的锐评和深度。
            ## 输出格式
            总体分数: X/10
            不足点评析
            1. xxx
            2. xxx
            优点评析
            1. xxx
            2. xxx
            改进建议
            1. xxx
            2. xxx
			""";

	@Override
	public String apply(AnalysisImageRequest request, ToolContext toolContext) {
		if (StringUtils.isBlank(request.getImageUrl())) {
			return "Error: Image url cannot be empty";
		}
        List<Media> mediaList = null;
        try {
            mediaList = List.of(
                    new Media(
                            MimeTypeUtils.IMAGE_PNG,
                            new URI("https://dashscope.oss-cn-beijing.aliyuncs.com/images/dog_and_girl.jpeg").toURL()
                                    .toURI()
                    )
            );
        } catch (URISyntaxException | MalformedURLException e) {
            throw new RuntimeException(e);
        }

        UserMessage message = UserMessage.builder().text(DESCRIPTION).media(mediaList).build();

//        ImagePrompt imagePrompt = new ImagePrompt(List.of(message));
//        qwen3VlModel.call()
        return null;

	}


    @Data
	public static class AnalysisImageRequest {
		@JsonProperty(required = true)
		@JsonPropertyDescription("The Image URL to analyze")
		public String imageUrl;

		public AnalysisImageRequest() {
		}

		public AnalysisImageRequest(String imageUrl) {
			this.imageUrl = imageUrl;
		}
	}
}

