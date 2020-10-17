package com.joyce.reactive_jasync_mysql.model;

import com.alibaba.fastjson.JSON;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import org.joda.time.LocalDateTime;

//import org.springframework.data.relational.core.mapping.Table;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
@Slf4j
@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_post")
public class PostModel {

    @Id
    private Long id;

    private String title;

    private String content;

    @CreatedDate
    private LocalDateTime createDate;

    public static void main(String[] args) {
        PostModel m = PostModel.builder().id(10L).title("title").content("c").createDate(LocalDateTime.now()).build();
        log.info("m ===1=== {}", JSON.toJSONString(m));
        log.info("m ===2=== {}", m);
    }
}
