package com.hlwcbz.common.aspect;

import com.hlwcbz.common.annotation.Logical;
import com.hlwcbz.common.annotation.RequiresPermissions;
import com.hlwcbz.common.enums.ResultCode;
import com.hlwcbz.common.exception.GlobalException;
import com.hlwcbz.common.util.JwtUtils;
import com.hlwcbz.modules.admin.login.service.LoginService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * 权限验证，切面处理类
 * @author hutu
 * @date 2018/6/10 15:12
 */
@Aspect
@Component
public class PermissionsAspect {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	LoginService loginService;

	@Pointcut("@annotation(com.hlwcbz.common.annotation.RequiresPermissions)")
	public void permissionsPointCut() {}

	@Before("permissionsPointCut()")
	public void doBefore(JoinPoint joinPoint) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		List<String> permissions = loginService.getUserPermissions(JwtUtils.getCallerInfo().uid);
		RequiresPermissions requiresPermissions = method.getAnnotation(RequiresPermissions.class);
		boolean havePermission = false;
		if(requiresPermissions != null){
			String[] reqPermissions = requiresPermissions.value();
			if (reqPermissions.length > 0){
				if (requiresPermissions.logical().equals(Logical.AND)) {
						havePermission = permissions.containsAll(Arrays.asList(reqPermissions));
				}else {
					for (String permission:reqPermissions) {
						if (permissions.contains(permission)){
							havePermission = true;
							break;
						}
					}
				}
			}
		}
		if (!havePermission){
			logger.info("无权限访问,需要权限："+ Arrays.toString(requiresPermissions.value()));
			throw new GlobalException(ResultCode.UNAUTHORIZED);
		}
	}
}
