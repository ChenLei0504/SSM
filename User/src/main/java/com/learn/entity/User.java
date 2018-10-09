package com.learn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author jobob
 * @since 2018-10-08
 */
@Data
@EqualsAndHashCode()
@Accessors(chain = true)
public class User  {

    private static final long serialVersionUID = 1L;
    @TableId
    private String id;

    @TableField("userName")
    private String userName;

    private String password;


}
