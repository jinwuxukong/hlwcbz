package com.hlwcbz.modules.admin.upms.vo;

import com.hlwcbz.common.entity.TreeNode;
import com.hlwcbz.modules.admin.upms.entity.Permission;

public class PermissionVo extends TreeNode {

    Permission permission;

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }

    public PermissionVo(int id, int parentId, String label, Permission permission) {
        super(id, parentId, label);
        this.permission = permission;
    }
}
