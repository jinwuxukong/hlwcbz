package com.hlwcbz.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDate;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.jboss.logging.Field;

/**
 * <p>
 * 
 * </p>
 *
 * @author generator
 * @since 2020-03-10
 */
@Getter
@Setter
@Accessors(chain = true)
@ApiModel("")
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
	@ApiModelProperty("主键")
	private Integer id;
    /**
     * 账号
     */
	@ApiModelProperty("账号")
	private String account;
    /**
     * 账号
     */
	@ApiModelProperty("账号")
	private Integer name;
    /**
     * 密码
     */
	@ApiModelProperty("密码")
	private String pass;
    /**
     * 昵称
     */
	@ApiModelProperty("昵称")
	private String nickName;
    /**
     * 生日
     */
	@ApiModelProperty("生日")
	private LocalDate birthday;
    /**
     * 类型（1管理员，2用户）
     */
	@ApiModelProperty("类型（1管理员，2用户）")
	private Integer type;
    /**
     * 1.男2.女
     */
	@ApiModelProperty("1.男2.女")
	private Integer sex;
    /**
     * 头像
     */
	@ApiModelProperty("头像")
	private String headPortrait;
	/**
	 * 手机号
	 */
	@ApiModelProperty("手机号")
	private String phone;

	@TableField(exist = false)
	private Integer[] roles;
    /**
     * 创建人名称
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人名称")
	private String createName;
    /**
     * 创建人ID
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建人ID")
	private Integer createId;
    /**
     * 创建时间
     */
	@TableField(fill = FieldFill.INSERT)
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
    /**
     * 更新人名称
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人名称")
	private String updateName;
    /**
     * 更新人ID
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新人ID")
	private Integer updateId;
    /**
     * 更新时间
     */
	@TableField(fill = FieldFill.INSERT_UPDATE)
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
    /**
     * 逻辑删除标记(已删除：1，未删除：0)
     */
	@ApiModelProperty("逻辑删除标记(已删除：1，未删除：0)")
    @TableLogic
	private Boolean isDeleted;
	@ApiModelProperty("")
	private Integer ordId;


}
