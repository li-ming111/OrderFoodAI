package com.SIM.ai.service;

import com.SIM.dto.OrderRequest;

public interface OrderAiService {
    //自然语音订单
    OrderRequest aiOrder(String language);
}
