package com.joyce.my_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Joyce Zhu
 * @date: 2020/10/4
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    private Long id;
    private String username;
}
