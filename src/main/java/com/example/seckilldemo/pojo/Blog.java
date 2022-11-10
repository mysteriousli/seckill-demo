package com.example.seckilldemo.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("blog")
@AllArgsConstructor
@NoArgsConstructor
public class Blog implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
}
