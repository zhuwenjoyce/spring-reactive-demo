package com.joyce.reactive_route.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

//import org.springframework.data.relational.core.mapping.Table;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
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
}
