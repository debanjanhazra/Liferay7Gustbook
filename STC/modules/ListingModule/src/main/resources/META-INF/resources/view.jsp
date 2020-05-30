<%@ include file="/init.jsp"%>
<div class="table-responsive">
<liferay-ui:search-container delta="5" deltaConfigurable="true" emptyResultsMessage="No item available">
	<liferay-ui:search-container-results results="${vendorListingModelArray}" />
	<liferay-ui:search-container-row className="com.stc.portal.model.VendorListingModel" modelVar="aProduct">
		<liferay-ui:search-container-column-text href="#" property="workId" name="Work Id" />
		<liferay-ui:search-container-column-text property="tenderName" name="Tender Name" />
		<liferay-ui:search-container-column-text property="tenderDesc" name="Tender Description" />
	</liferay-ui:search-container-row>
	<liferay-ui:search-iterator />
</liferay-ui:search-container>
</div>