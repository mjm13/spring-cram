package com.meijm.oauth2.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SysUser {
    @TableId
    private Long id;
    private String userName;
    private String password;
}
