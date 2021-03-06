package com.hlwcbz.modules.admin.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 角色名称
     */
	private String name;
    /**
     * 角色标识
     */
	private String code;
    /**
     * 角色描述
     */
	private String description;
    /**
     * 是否启用（1 启用，0 禁用）
     */
	private Integer status;
    /**
     * 状态值
     */
	private String statusShow;
    /**
     * 排序
     */
	private Integer orders;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	private String createName;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	private Integer createId;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private String updateName;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Integer updateId;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
    @TableLogic
	private Integer isDeleted;
    /**
     * 组织id
     */
	@TableField(fill = FieldFill.INSERT)
	private Integer orgId;


}
