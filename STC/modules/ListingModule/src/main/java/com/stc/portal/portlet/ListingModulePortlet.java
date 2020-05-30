package com.stc.portal.portlet;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleModel;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.stc.portal.constants.ListingModulePortletKeys;
import com.stc.portal.model.VendorListingModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
		"com.liferay.portlet.display-category=STC",
		"com.liferay.portlet.header-portlet-css=/css/main.css",
		"com.liferay.portlet.instanceable=true",
		"javax.portlet.display-name=ListingModule",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.name=" + ListingModulePortletKeys.LISTINGMODULE,
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=power-user,user"
	},
	service = Portlet.class
)
public class ListingModulePortlet extends MVCPortlet {
	private static final Log _log = LogFactoryUtil.getLog(ListingModulePortlet.class);

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		ThemeDisplay themeDisplay = (ThemeDisplay) renderRequest.getAttribute(WebKeys.THEME_DISPLAY);
		List<DDMStructure> ddmStructureList = DDMStructureLocalServiceUtil.getStructures(themeDisplay.getScopeGroupId());

		String structureKey = StringPool.BLANK;
		if (Validator.isNotNull(ddmStructureList)) {
			_log.info("Structure List size +" + ddmStructureList.size());
			for (DDMStructure ddmStructure : ddmStructureList) {
				if (ddmStructure.getName(LocaleUtil.US).equalsIgnoreCase("vendorTender")) {
					structureKey = ddmStructure.getStructureKey();
					break;
				}
			}
		}
		_log.info("structureKey :::::::::::: " + structureKey);
		List<JournalArticle> articles = JournalArticleServiceUtil.getArticlesByStructureId(themeDisplay.getScopeGroupId(), structureKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
		List<VendorListingModel> vendorListingModelArray = new ArrayList<VendorListingModel>();
		for (JournalArticle article : articles) {
			try {
				_log.info("journal article size :::::::::::: " + articles.size());
				VendorListingModel vendorListingModel = parseDealRegistraionArticle(article);
				vendorListingModel.setWorkId(article.getArticleId());
				vendorListingModelArray.add(vendorListingModel);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		_log.info(vendorListingModelArray.size());

		renderRequest.setAttribute("vendorListingModelArray", vendorListingModelArray);
		super.doView(renderRequest, renderResponse);
	}

	private static VendorListingModel parseDealRegistraionArticle(JournalArticleModel article) throws DocumentException {
		Document document = SAXReaderUtil.read(article.getContent());

		Node tenderTitleNode = document.selectSingleNode("/root/dynamic-element[@name='TenderTitle']/dynamic-content");
		Node tenderDescNode = document.selectSingleNode("/root/dynamic-element[@name='TenderDesc']/dynamic-content");
		Node fileItemNode = document.selectSingleNode("/root/dynamic-element[@name='FileItem']/dynamic-content");

		String tenderTitle = (tenderTitleNode != null) ? tenderTitleNode.getText() : "";
		String tenderDesc = (tenderDescNode != null) ? tenderDescNode.getText() : "";
		String fileItem = (fileItemNode != null) ? fileItemNode.getText() : "";

		VendorListingModel vendorListingModel = new VendorListingModel();
		vendorListingModel.setTenderName(tenderTitle);
		vendorListingModel.setTenderDesc(tenderDesc);
		vendorListingModel.setDocPath(fileItem);

		return vendorListingModel;
	}
}