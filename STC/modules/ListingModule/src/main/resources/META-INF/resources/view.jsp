<%@ include file="/init.jsp"%>

<table class="table table-striped">
	<thead>
		<tr>
			<th scope="col">Work Id</th>
			<th scope="col">Tender Name</th>
			<th scope="col">Tender Description</th>
		</tr>
	</thead>
	<tbody>
      	<c:forEach var="vendorListingModel" items="${vendorListingModelArray}">
		    <tr>
				<th scope="row"><c:out value = "${vendorListingModel.workId}"/><p></th>
				<td><c:out value = "${vendorListingModel.tenderName}"/><p></td>
				<td><c:out value = "${vendorListingModel.tenderDesc}"/><p></td>
			</tr>
		</c:forEach>
	</tbody>
</table>