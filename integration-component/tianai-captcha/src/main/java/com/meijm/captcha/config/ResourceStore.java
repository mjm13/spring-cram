package com.meijm.captcha.config;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.generator.common.constant.SliderCaptchaConstant;
import cloud.tianai.captcha.generator.impl.StandardSliderImageCaptchaGenerator;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.impl.DefaultResourceStore;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static cloud.tianai.captcha.generator.impl.StandardSliderImageCaptchaGenerator.DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH;

/**
 * @Author: 天爱有情
 * @date 2022/7/11 14:22
 * @Description 负责模板和背景图存储的地方
 */
@Component
public class ResourceStore extends DefaultResourceStore {

    public ResourceStore() {
        // 滑块验证码 模板 (系统内置)
        Map<String, Resource> markTemplate = new HashMap<>(4);
        markTemplate.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/1/active.png")));
        markTemplate.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/1/fixed.png")));
        markTemplate.put(SliderCaptchaConstant.TEMPLATE_MATRIX_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/1/matrix.png")));

        Map<String, Resource> markTemplate2 = new HashMap<>(4);
        markTemplate2.put(SliderCaptchaConstant.TEMPLATE_ACTIVE_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/2/active.png")));
        markTemplate2.put(SliderCaptchaConstant.TEMPLATE_FIXED_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/2/fixed.png")));
        markTemplate2.put(SliderCaptchaConstant.TEMPLATE_MATRIX_IMAGE_NAME, new Resource(ClassPathResourceProvider.NAME, DEFAULT_SLIDER_IMAGE_TEMPLATE_PATH.concat("/2/matrix.png")));

        // 1. 添加一些模板
        addTemplate(CaptchaTypeConstant.SLIDER, markTemplate);
        addTemplate(CaptchaTypeConstant.SLIDER, markTemplate2);

        // 2. 添加自定义背景图片
        addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "bgimages/1.png"));
        addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "bgimages/2.png"));
        addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "bgimages/3.png"));
        addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "bgimages/4.png"));
        addResource(CaptchaTypeConstant.SLIDER, new Resource("classpath", "bgimages/5.png"));
    }
}
