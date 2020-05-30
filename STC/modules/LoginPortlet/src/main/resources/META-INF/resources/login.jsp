<%@ include file="init.jsp"%>
<c:choose>
    <c:when test="<%= themeDisplay.isSignedIn() %>">
        <%
        String signedInAs = HtmlUtil.escape(user.getFullName());
        if (themeDisplay.isShowMyAccountIcon() && (themeDisplay.getURLMyAccount() != null)) {
            String myAccountURL = String.valueOf(themeDisplay.getURLMyAccount());
            signedInAs = "<a class=\"signed-in\" href=\"" + HtmlUtil.escape(myAccountURL) + "\">" + signedInAs + "</a>";
        }
        %>
        <liferay-ui:message arguments="<%= signedInAs %>" key="you-are-signed-in-as-x" translateArguments="<%= false %>" />
    </c:when>
    <c:otherwise>
    
        <%
        String redirect = ParamUtil.getString(request, "redirect");
        %>
    
        <portlet:actionURL name="/login/login" var="loginURL">
            <portlet:param name="mvcRenderCommandName" value="/login/login" />
        </portlet:actionURL>

		<aui:form action="<%=loginURL%>" autocomplete='on' cssClass="sign-in-form form-signin" method="post" name="loginForm">
        	<div class="text-center mb-4">
				<img class="mb-4" src="/image/company_logo" alt="" width="72" height="72">
				<h1 class="h3 mb-3 font-weight-normal">Login Form</h1>
			</div>
            <aui:input name="saveLastPath" type="hidden" value="<%= false %>" />
            <aui:input name="redirect" type="hidden" value="<%= redirect %>" />
            <div class="form-label-group">       
	            <aui:input autoFocus="true" cssClass="clearable" label="email-address" name="login" showRequiredLabel="<%= false %>" type="text" value="">
	                <aui:validator name="required" />
	            </aui:input>
			</div>
			
			<div class="form-label-group">
	            <aui:input name="password" showRequiredLabel="<%= false %>" type="password">
	                <aui:validator name="required" />
	            </aui:input>
            </div>
            
            <aui:button-row>
                <aui:button cssClass="btn btn-lg btn-primary btn-block" type="submit" value="sign-in" />
            </aui:button-row>
                
        </aui:form>
    </c:otherwise>
</c:choose>