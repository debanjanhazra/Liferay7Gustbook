<%@page import="com.liferay.portal.kernel.util.CalendarFactoryUtil"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.util.List"%>
<%@ include file="/vendor/init.jsp"%>

<%

Calendar birthdayCalendar = CalendarFactoryUtil.getCalendar();

birthdayCalendar.set(Calendar.MONTH, Calendar.JANUARY);
birthdayCalendar.set(Calendar.DATE, 1);
birthdayCalendar.set(Calendar.YEAR, 1970);
%>
<form action="<%=createVendorURL%>" method="post" class="was-validated">
	<h3>Create Vendor</h3>
	<div class="form-group">
		<label for="title">Vendor Title</label> 
		<input type="text" class="form-control is-invalid" id="title" name="<portlet:namespace/>vendorTitle" placeholder="Enter title" required>
		<div class="invalid-feedback">
	        Please provide a valid Name.
	      </div>
	</div>
	<div class="form-group">
		<select class="custom-select" required>
			<option value="">Select an Organization</option>
			<%
			List<Organization> organizationList = (List<Organization>)request.getAttribute("organizationList");
			for (Organization organization : organizationList) {
			%>
				<option value="<%=organization.getOrganizationId()%>"><c:out value="<%=organization.getName()%>"/> </option>
			<%}%>
		</select>
		<div class="invalid-feedback">Select an Organization</div>
	</div>
	<div class="form-group">
		<aui:input name="birthday" value="<%= birthdayCalendar %>" type="Date"/>
	</div>
	<div class="form-group">
		<label for="desciption">Vendor Describe</label>
		<textarea class="form-control" rows="5" id="desciption" name="<portlet:namespace/>vendorDesciption"></textarea>
	</div>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>