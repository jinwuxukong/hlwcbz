package com.hlwcbz.modules.admin.upms.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户组织关联表
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */
@Data
@Accessors(chain = true)
@TableName("t_upms_user_organization")
public class UserOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户编号
     */
	private Integer userId;
    /**
     * 组织编号
     */
	private Integer organizationId;
    /**
     * 创建人
     */
	@TableField(fill = FieldFill.INSERT)
	private Integer createId;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	private LocalDateTime createTime;


}
