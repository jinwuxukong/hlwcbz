package com.hlwcbz.modules.admin.login.controller;

import com.hlwcbz.common.annotation.AuthIgnore;
import com.hlwcbz.common.entity.CallerInfo;
import com.hlwcbz.common.entity.R;
import com.hlwcbz.common.util.JwtUtils;
import com.hlwcbz.modules.admin.login.service.LoginService;
import com.hlwcbz.modules.admin.login.dto.UserInfo;
import com.hlwcbz.modules.admin.upms.entity.Organization;
import com.hlwcbz.modules.admin.upms.service.OrganizationService;
import com.hlwcbz.modules.common.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录逻辑
 *
 * @author hutu
 * @date 2018/8/16 9:33
 */
@Api(tags = "登录控制器")
@RestController
public class LoginController {

    @Autowired
    LoginService loginService;
    @Autowired
    OrganizationService organizationService;

    @AuthIgnore
    @ApiOperation("注册")
    @PostMapping("register")
    public R register(@ApiParam("注册对象") @RequestBody User user) {
        return loginService.register(user);
    }

    @ApiOperation("登录接口")
    @AuthIgnore
    @GetMapping("login")
    public R login(String username, String password) {
        return loginService.login(username, password);
    }

    @ApiOperation("获取用户信息")
    @GetMapping("getUserInfo")
    public R getUserInfo() {
        Organization organization = organizationService.getById(JwtUtils.getCallerInfo().tenantId);
        UserInfo userInfo = new UserInfo();
        CallerInfo callerInfo = JwtUtils.getCallerInfo();
        userInfo.tenantId = callerInfo.tenantId;
        userInfo.uid = callerInfo.uid;
        userInfo.name = callerInfo.name;
        userInfo.nick = callerInfo.nick;
        userInfo.type = callerInfo.type;
        userInfo.setOrgName(organization == null ? "unknown" : organization.getName());
        return R.ok().put("user", userInfo);
    }

    @ApiOperation("获取用户权限信息")
    @GetMapping("getPermissionByUserId")
    public R getPermissionByUserId() {
        return R.ok().put("list", loginService.getPermissionByUserId(JwtUtils.getCallerInfo().uid));
    }

    @ApiOperation("获取角色权限树")
    @GetMapping("getRolePermissionTree")
    public R getRolePermissionTree(Integer roleId) {
        return R.ok().put("treeData", loginService.getPermissionTree()).put("keys", loginService.getRolePermissionIds(roleId));
    }

    @ApiOperation("更新角色权限树")
    @PostMapping("updateRolePermission")
    public R updateRolePermission(@RequestBody Integer[] permissionIds, Integer roleId) {
        return R.ok().put("list", loginService.updateRolePermission(permissionIds, roleId));
    }

    @ApiOperation("退出登录")
    @AuthIgnore
    @GetMapping("logout")
    public R logout() {
        System.out.println("456");
        return R.ok();
    }

}
