package com.hlwcbz.modules.admin.login.service.imlp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hlwcbz.common.annotation.OperationLog;
import com.hlwcbz.common.entity.CallerInfo;
import com.hlwcbz.common.entity.R;
import com.hlwcbz.common.enums.ResultCode;
import com.hlwcbz.common.enums.OperateEnum;
import com.hlwcbz.common.util.CacheUtils;
import com.hlwcbz.common.util.JwtUtils;
import com.hlwcbz.common.util.TreeUtil;
import com.hlwcbz.modules.admin.login.service.LoginService;
import com.hlwcbz.modules.admin.upms.service.UserService;
import com.hlwcbz.modules.admin.upms.vo.PermissionTreeNode;
import com.hlwcbz.modules.admin.upms.entity.*;
import com.hlwcbz.modules.admin.upms.mapper.*;
import com.hlwcbz.modules.admin.upms.service.RolePermissionService;
import com.hlwcbz.modules.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @author hutu.
 * @date: 2017/11/6 10:38
 */
@Service("loginServiceImpl")
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private UserPermissionMapper usePermissionMapper;
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public R register(User user) {
        if(!StringUtils.isEmpty(userMapper.selectOne(new QueryWrapper<User>().eq("account",user.getAccount())))){
            return R.error(ResultCode.USERNAME_ALREADY_EXIST);
        }
        user.setNickName(user.getAccount());
        if(!userService.saveUser(user)){
            throw new RuntimeException();
        }
        return R.ok();
    }

    @Override
    @OperationLog(value = "登录后台", type = OperateEnum.SELECT, module = "com.hlwcbz.modules.login")
    public R login(String username, String password) {
        User user = userService.getOne(new QueryWrapper<User>().eq("account", username));
        CallerInfo callerInfo = new CallerInfo();
        String token;
        if (user == null || !user.getPass().equals(password)) {
            return R.error(ResultCode.USERNAME_OR_PASS_ERROR);
        } else {
            callerInfo.uid = user.getId();
            callerInfo.name = user.getAccount();
            callerInfo.nick = user.getNickName();
            callerInfo.type = user.getType();
            token = JwtUtils.createToken(callerInfo);
            CacheUtils.userCache.put(user.getId().toString(),token);
        }
        return R.ok().put("token", token);
    }

    @Override
    public List<Permission> getPermissionByUserId(Integer userId) {
        List<UserRole> userRoleList = userRoleMapper.selectList(new QueryWrapper<UserRole>()
                .eq("userId", userId));
        List<UserPermission> userPermissionList = usePermissionMapper.selectList(new QueryWrapper<UserPermission>()
                .eq("userId", userId));
        Set<Integer> roleIds = userRoleList.stream().filter(obj -> obj.getRoleId() != 0).map(UserRole::getRoleId).collect(Collectors.toSet());
        Set<Integer> permissionIds = new HashSet<>();
        if (roleIds.size() > 0) {
            List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>()
                    .in("roleId", roleIds));
            permissionIds = rolePermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(RolePermission::getPermissionId).collect(Collectors.toSet());
        }
        permissionIds.addAll(userPermissionList.stream().filter(obj -> obj.getPermissionId() != 0).map(UserPermission::getPermissionId).collect(Collectors.toSet()));
        // 无权限
        if (permissionIds.size() == 0) {
            return new ArrayList();
        }
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>()
                .in("id", permissionIds).orderByAsc("orders"));

        return permissionList;
    }

    @Override
    public List getUserPermissions(Integer userId) {
        List<Permission> list = getPermissionByUserId(userId);
        List<String> permissions = list.stream().map(Permission::getPermissionValue).collect(Collectors.toList());
        return permissions;
    }

    @Override
    public List getPermissionTree() {
        List<Permission> list = permissionMapper.selectList(null);
        List<PermissionTreeNode> treeList = new ArrayList<>();
        for (Permission obj : list) {
            PermissionTreeNode treenode = new PermissionTreeNode(obj.getId(), obj.getPid(), obj.getTitle(), obj.getName());
            treeList.add(treenode);
        }
        return TreeUtil.buildByRecursive(treeList, 0);
    }

    @Override
    public List getRolePermissionIds(Integer roleId) {
        // 找到是目录的菜单
        List<Permission> permissionList = permissionMapper.selectList(new QueryWrapper<Permission>().eq("type", 1));
        List<RolePermission> rolePermissionList = rolePermissionMapper.selectList(new QueryWrapper<RolePermission>().eq("roleId", roleId));
        List<Integer> permissionIds = rolePermissionList.stream().map(RolePermission::getPermissionId).collect(Collectors.toList());

        for (Permission obj : permissionList) {
            permissionIds.removeIf(id -> obj.getId().equals(id));
        }
        return permissionIds;
    }

    @Autowired
    RolePermissionService rolePermissionService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @OperationLog(value = "更新角色权限树", type = OperateEnum.UPDATE, module = "com.hlwcbz.modules.upms")
    public boolean updateRolePermission(Integer[] permissionIds, Integer roleId) {
        rolePermissionService.remove(new QueryWrapper<RolePermission>().eq("roleId", roleId));

        Set<RolePermission> rolePermissions = new HashSet<>();
        List<Permission> permissionList = permissionMapper.selectList(null);

        for (int permissionId : permissionIds) {
            findParentPermission(permissionList, rolePermissions, roleId, permissionId);
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissions.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissions);

        return true;
    }

    public void findParentPermission(List<Permission> permissionList, Set<RolePermission> rolePermissions, int roleId, int permissionId) {
        int parentId = 0;
        for (Permission obj : permissionList) {
            if (obj.getId().equals(permissionId) && obj.getPid() != 0) {
                for (Permission tmp : permissionList) {
                    if (tmp.getId().equals(obj.getPid())) {
                        RolePermission rolePermission = new RolePermission();
                        rolePermission.setRoleId(roleId);
                        rolePermission.setPermissionId(tmp.getId());
                        rolePermissions.add(rolePermission);
                        break;
                    }
                }
                parentId = obj.getPid();
                break;
            }
        }
        if (parentId != 0) {
            findParentPermission(permissionList, rolePermissions, roleId, parentId);
        }
    }
}
