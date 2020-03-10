package com.hlwcbz.modules.admin.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlwcbz.common.entity.R;
import com.hlwcbz.common.util.TreeUtil;
import com.hlwcbz.modules.admin.upms.vo.PermissionVo;
import com.hlwcbz.modules.admin.upms.entity.Permission;
import com.hlwcbz.modules.admin.upms.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 权限 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "权限")
@RestController
@RequestMapping("permission")
public class PermissionController{
    @Autowired
    private PermissionService permissionService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        Page<Permission> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        permissionService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R create(@RequestBody @ApiParam("数据对象")@Validated Permission data){
        return permissionService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{id}")
    public R delete(@ApiParam("数据对象id")@PathVariable("id")String id){
        return permissionService.removeById(id)?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",permissionService.getById(id));
    }

    @ApiOperation("获取权限树")
    @GetMapping("getPermissionTree")
    public R getPermissionTree(){
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("orders");
        List<Permission> list = permissionService.list(queryWrapper);
        List<PermissionVo> treeList = new ArrayList<>();
        for (Permission obj : list) {
            PermissionVo treenode = new PermissionVo(obj.getId(), obj.getPid(), obj.getTitle(),obj);
            treeList.add(treenode);
        }
        return R.ok().put("treeData",TreeUtil.buildByRecursive(treeList, 0));
    }
}
