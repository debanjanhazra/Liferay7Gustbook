package com.stc.portal.portlet;

import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.stc.portal.constants.AdminModulePortletKeys;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Debanjan Hazra
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.display-category=category.stc",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=AdminModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + AdminModulePortletKeys.ADMINMODULE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)


public class AdminModulePortlet extends MVCPortlet {
	
	private static final  Log _log = LogFactoryUtil.getLog(AdminModulePortlet.class);
	
	@Override
		public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {
		_log.info("render");
			super.doView(renderRequest, renderResponse);
		}
	
	public void createTender(ActionRequest actionRequest, ActionResponse actionResponse) {
		String title = ParamUtil.getString(actionRequest, "title");
		String desciption = ParamUtil.getString(actionRequest, "desciption");
		_log.info("title ="+title+" desciption ="+desciption);
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		String articleContent = "<?xml version=\"1.0\"?>\r\n" + 
				" \r\n" + 
				" <root available-locales=\"en_US\" default-locale=\"en_US\">\r\n" + 
				" 	<dynamic-element name=\"TenderTitle\" type=\"text\" index-type=\"keyword\" instance-id=\"mnwc\">\r\n" + 
				" 		<dynamic-content language-id=\"en_US\"><![CDATA["+title+"]]></dynamic-content>\r\n" + 
				" 	</dynamic-element>\r\n" + 
				" 	<dynamic-element name=\"TenderDesc\" type=\"text_box\" index-type=\"text\" instance-id=\"gjwe\">\r\n" + 
				" 		<dynamic-content language-id=\"en_US\"><![CDATA["+desciption+"]]></dynamic-content>\r\n" + 
				" 	</dynamic-element>\r\n" + 
				" 	<dynamic-element name=\"FileItem\" type=\"document_library\" index-type=\"keyword\" instance-id=\"kwqp\">\r\n" + 
				" 		<dynamic-content language-id=\"en_US\"><![CDATA[]]></dynamic-content>\r\n" + 
				" 	</dynamic-element>\r\n" + 
				" </root>";
		
		List<DDMStructure> ddmStructureList = DDMStructureLocalServiceUtil.getStructures(themeDisplay.getScopeGroupId());
		String structureKey = StringPool.BLANK;
		if(Validator.isNotNull(ddmStructureList)) {
			for(DDMStructure ddmStructure : ddmStructureList) {
				if(ddmStructure.getName(LocaleUtil.US).equalsIgnoreCase("vendorTender")) {
					structureKey = ddmStructure.getStructureKey();
					break;
				}
			}
		}
		Map<Locale, String> titleMap = new HashMap<Locale, String>();
		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();
		titleMap.put(Locale.US, title);
		descriptionMap.put(Locale.US, desciption);
		
		
		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(themeDisplay.getScopeGroupId());
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		try {
			JournalArticle article = JournalArticleLocalServiceUtil.addArticle(themeDisplay.getUserId(), themeDisplay.getScopeGroupId(), 0, titleMap, descriptionMap, articleContent, structureKey,
					"", serviceContext);
			boolean mountPoint = false;
			boolean hidden = false;
			DLFolder dlFolder = DLFolderLocalServiceUtil.addFolder (themeDisplay.getUserId(), themeDisplay.getScopeGroupId() ,themeDisplay.getScopeGroupId(), 
					mountPoint, 0, article.getArticleId(), article.getArticleId(), hidden, serviceContext);
			_log.info("folder created  "+dlFolder.getFolderId());
		} catch (PortalException e) {
			e.printStackTrace();
		}
		
	}
}