package com.SIM.ai.service.impl;

import com.SIM.ai.service.OrderAiService;
import com.SIM.dto.OrderRequest;
import com.SIM.mapper.DishMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DeepseekAiOrderService implements OrderAiService {

    //菜品
    private final DishMapper dishMapper;

    private final ChatClient chatClient;

    //构造方法注入
    public DeepseekAiOrderService(DishMapper dishMapper, ChatClient.Builder chatClientBuilder) {
        this.dishMapper = dishMapper;
        this.chatClient = chatClientBuilder
                .defaultAdvisors(MessageChatMemoryAdvisor
                        .builder(MessageWindowChatMemory.builder().build()).build())
                .defaultOptions(DeepSeekChatOptions.builder().temperature(0.6).build())
                .build();
    }

    //系统的提示词
    private final String SYSTEM_PROMPT = """
            你是一个智能点餐助手，负责将用户的自然语言点餐内容转换为结构化的订单信息。
            可用的菜品列表如下：
             {dishes}
            
             结构化订单信息的格式为：
             {format}                        
            """;

    @Override
    public OrderRequest aiOrder(String language) {
        try {
            //获取所有菜品
            String dishesInfo = dishMapper.selectList(null).stream()
                    .map(dish -> String.format("ID:%d - %s,价格:%.2f元",
                            dish.getId(), dish.getName(), dish.getPrice()))
                    .collect(Collectors.joining("\n"));

            //转换格式
            BeanOutputConverter<OrderRequest> beanOutputConverter = new BeanOutputConverter<>(OrderRequest.class);
            //格式化字符串
            String format = beanOutputConverter.getFormat();
            //菜品的转换
            PromptTemplate promptTemplate = PromptTemplate
                    .builder()
                    .template(SYSTEM_PROMPT)
                    .variables(Map.of("dishes", dishesInfo, "format", format))
                    .build();
            //LLM
            String content = chatClient.prompt()
                    .system(promptTemplate.render())
                    .user(language)
                    .call()
                    .content();

            //json返回
            return beanOutputConverter.convert(content);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
}



























