package com.hlwcbz.modules.admin.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlwcbz.common.entity.R;
import com.hlwcbz.modules.admin.upms.entity.Organization;
import com.hlwcbz.modules.admin.upms.service.OrganizationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 组织 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-08-16
 */

@Api(tags = "组织")
@RestController
@RequestMapping("organization")
public class OrganizationController{
    @Autowired
    private OrganizationService organizationService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Organization> queryWrapper = new QueryWrapper<>();
        Page<Organization> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        organizationService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")Organization data){
        return organizationService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{ids}")
    public R delete(@ApiParam("数据对象id集合")@PathVariable("ids")String ids){
        return organizationService.removeByIds(StrUtil.split(ids,','))?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",organizationService.getById(id));
    }

}
