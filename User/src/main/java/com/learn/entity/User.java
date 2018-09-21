package com.learn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.io.Serializable;

/**
 * //TODO 添加类/接口功能描述
 *
 * @author 陈雷
 * @date 2018-09-13
 */
@Data
//表名
@TableName("user")
public class User <T extends Model> extends Model<T> {
    /**
     * 主键
     */
    @TableId("id")
    private String id;

    /**
     * 字段userName
     * 属性userName
     */
    @TableField("userName")
    private String userName;

    /**
     * 字段password
     * 属性userPassword
     */
    @TableField(value = "password")
    private String userPassword;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
