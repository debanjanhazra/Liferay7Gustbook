/**
 * Copyright 2000-present Liferay, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stc.portal.portlet;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ContactLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.stc.portal.constants.AdminModulePortletKeys;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=STC",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AdminModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/vendor/vendor.jsp",
		"javax.portlet.name=" + AdminModulePortletKeys.CREATEVENDOR,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class CreateVendor extends MVCPortlet {
	private static final Log _log = LogFactoryUtil.getLog(CreateVendor.class);

	@Override
	public void render(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		List<Organization> organizationList = OrganizationLocalServiceUtil.getOrganizations(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		for (Organization organization : organizationList) {
			organization.getName();
			organization.getOrganizationId();
		}
		request.setAttribute("organizationList", organizationList);
		super.render(request, response);
	}
	public void createVendorUser(ActionRequest actionRequest, ActionResponse actionResponse, Organization organization,ServiceContext serviceContext,long gpIdForUser){
		Date date=new Date();
		ServiceContext serviceContext1 = new ServiceContext();
		serviceContext1.setUuid(UUID.randomUUID().toString());
		serviceContext1.setCreateDate(date);
		serviceContext1.setModifiedDate(date);
		long companyId=PortalUtil.getDefaultCompanyId();
		long creatorUserId=0;
		boolean autoPassword=false;
		boolean autoScreenName=false;
		boolean male=true;
		boolean sendEmail = false;
		int prefixId=1;
		int suffixId=1;
		int birthdayMonth=1;
		int birthdayDay=1;
		int birthdayYear=1970;
		String screenName="sceene";
		String jobTitle="";
		long[] groupIds = null;
		long[] organizationIds = null;
		long[] userGroupIds = null;
		String password1="test";
		String password2="test";
		String firstName="firstname";
		String emailAddress="usertest@liferay.com";
		long facebookId=0;
		String openId="";
		Locale locale=LocaleUtil.getDefault();
		Role rolePu;
		try {
			rolePu = RoleLocalServiceUtil.getRole(companyId, RoleConstants.POWER_USER);
			long[] roleIds= {rolePu.getRoleId()};
			User users43=UserLocalServiceUtil.addUserWithWorkflow(creatorUserId, companyId, autoPassword, password1,
					password2, autoScreenName, screenName, emailAddress, facebookId, openId, locale, firstName,
					"", "", prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear, jobTitle, groupIds,
					organizationIds, roleIds, userGroupIds, sendEmail, serviceContext1);
		} catch (PortalException e) {
			e.printStackTrace();
		}
	}
}