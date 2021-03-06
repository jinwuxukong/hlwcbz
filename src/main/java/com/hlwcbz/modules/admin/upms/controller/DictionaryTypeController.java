package com.hlwcbz.modules.admin.upms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hlwcbz.common.entity.R;
import com.hlwcbz.modules.admin.upms.entity.DictionaryType;
import com.hlwcbz.modules.admin.upms.service.DictionaryTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 字典类别表 前端控制器
 * </p>
 *
 * @author generator
 * @since 2019-06-05
 */

@Api(tags = "字典类别表")
@RestController
@RequestMapping("dictionaryType")
public class DictionaryTypeController{
    @Autowired
    private DictionaryTypeService dictionaryTypeService;

    @ApiOperation("获取page")
    @GetMapping("/page/{current}/{pageSize}")
    public R getPage(@ApiParam("当前页")@PathVariable("current")int current,@ApiParam("分页大小")@PathVariable("pageSize")int pageSize,
                     @ApiParam("关键字") @RequestParam(required = false) String keyWord) {
        QueryWrapper<DictionaryType> queryWrapper = new QueryWrapper<>();
        Page<DictionaryType> page=new Page<>(current,pageSize);
        if (StrUtil.isNotEmpty(keyWord)) {
            queryWrapper.like("name", keyWord);
        }
        dictionaryTypeService.page(page,queryWrapper);
        return R.ok().put("list",page.getRecords()).put("total",page.getTotal());
    }
    @ApiOperation("新增或更新")
    @PostMapping("/createOrUpdate")
    public R createOrUpdate(@RequestBody @ApiParam("数据对象")DictionaryType data){
        return dictionaryTypeService.saveOrUpdate(data)?R.ok():R.error("保存错误");
    }
    @ApiOperation("删除")
    @PostMapping("/delete/{id}")
    public R delete(@ApiParam("数据对象id")@PathVariable("id")String id){
        return dictionaryTypeService.removeById(id)?R.ok():R.error("删除错误");
    }
    @ApiOperation("通过ID获取一条数据")
    @GetMapping("/read/{id}")
    public R read(@ApiParam("数据对象id")@PathVariable("id")String id){
        return R.ok().put("info",dictionaryTypeService.getById(id));
    }
    @ApiOperation("获取字典类型树")
    @GetMapping("getDicTypeTree")
    public R getDicTypeTree(){
        List<DictionaryType> list = dictionaryTypeService.list();
        List treeList = new ArrayList<>();
        for (DictionaryType obj : list) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("label",obj.getValueCn());
            map.put("key",obj.getKey());
            map.put("id",obj.getId());
            treeList.add(map);
        }
        return R.ok().put("treeData", treeList);
    }
}
