package com.tw.shiro.realms;

import com.tw.pojo.Permission;
import com.tw.pojo.Role;
import com.tw.pojo.Staff;
import com.tw.service.StaffService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class LoginRealm extends AuthorizingRealm {

    @Resource
    private StaffService staffService;

    @Override
    public void setName(String name) {
        super.setName("loginRealm");
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String accout = (String)token.getPrincipal();
        Staff _staff = null;
        try {
            _staff = staffService.selByAccount(accout);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(_staff==null){
            return null;
        }
        String password_db = _staff.getPassword();
        String salt = _staff.getSalt();

        SimpleAuthenticationInfo simpleAuthenticationInfo =
                new SimpleAuthenticationInfo(_staff,password_db, ByteSource.Util.bytes(salt),this.getName());
        return  simpleAuthenticationInfo;

    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Staff staff_info = (Staff) principals.getPrimaryPrincipal();

//        从数据库获取权限数据 由于在认证时已经将权限信息存储进去了，所以直接从信息中获取即可
//        但是如果有需要修改权限的需求的话，就需要从数据库获取，并加上手动清除缓存的功能
//        List<Permission> permissionList = staff_info.getPermissionUrlList();//这里的是权限数据，包含了percode和url
        List<Permission> permissionList = staffService.selPermissionByJobId(staff_info.getJobid());
        List<String> permissionCodes = new ArrayList<>();//用来存放权限标识符
        for (Permission permission:permissionList) {
            if(permission!=null){
                permissionCodes.add(permission.getPercode());
            }
        }
        List<Role> roleList = staffService.selRoleByJobId(staff_info.getJobid());
        List<String> roleCodes = new ArrayList<>();//用来存放权限标识符
        for (Role role:roleList) {
            roleCodes.add(role.getName());
        }

//        查到权限数据，返回授权信息（要包括上面的permissionCodes）
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
//        将上述权限标识符填充到simpleAuthorizationInfo中
        simpleAuthorizationInfo.addStringPermissions(permissionCodes);
        simpleAuthorizationInfo.addRoles(roleCodes);
        return simpleAuthorizationInfo;
    }


    //清除缓存
    public void clearCached() {
        PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
        super.clearCache(principals);
    }

}
