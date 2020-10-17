package com.joyce.reactive_route.model;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import org.joda.time.LocalDateTime;

//import org.springframework.data.relational.core.mapping.Table;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/9
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("t_post")
public class PostModel {

    @Id
    private Long id;

    private String title;

    private String content;

    @Column("create_date")
    @CreatedDate
    private LocalDateTime createDate;

    @Override
    public String toString() {
        return " >>>>>>>>>>>>>>>> PostModel{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                '}';
    }
}
