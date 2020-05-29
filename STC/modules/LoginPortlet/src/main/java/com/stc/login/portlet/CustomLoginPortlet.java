package com.stc.login.portlet;

import com.stc.login.constants.CustomLoginPortletKeys;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;

import javax.portlet.Portlet;

import org.osgi.service.component.annotations.Component;

/**
 * @author Debanjan Hazra
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=STC",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=CustomLogin",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/login.jsp",
		"javax.portlet.name=" + CustomLoginPortletKeys.CUSTOMLOGIN,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CustomLoginPortlet extends MVCPortlet {
}