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

import javax.portlet.Portlet;
import java.io.IOException;
import java.io.PrintWriter;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import org.osgi.service.component.annotations.Component;

@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=STC",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.security-role-ref=power-user,user",
		"javax.portlet.init-param.view-template=/vendor.jsp",
		"javax.portlet.display-name=CreateVendor Portlet"
		
	},
	service = Portlet.class
)
public class CreateVendor extends GenericPortlet {

	@Override
	protected void doView(RenderRequest request, RenderResponse response)
		throws IOException, PortletException {
	}
}