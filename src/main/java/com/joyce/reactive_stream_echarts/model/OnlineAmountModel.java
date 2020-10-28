package com.joyce.reactive_stream_echarts.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineAmountModel {
    private String name;

    private Integer amount;

    private Instant time;
}
