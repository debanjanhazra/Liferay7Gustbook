<form action="<%=createTenderURL%>" method="post">
	<h3>Create Vendor</h3>
	<div class="form-group">
		<label for="title">Title</label> 
		<input type="text" class="form-control" id="title" name="<portlet:namespace/>title" placeholder="Enter title">
	</div>
	<div class="form-group">
		<label for="desciption">Describe</label> 
		<textarea class="form-control" rows="5" id="desciption" name="<portlet:namespace/>desciption" ></textarea>
	</div>
	<button type="submit" class="btn btn-primary">Submit</button>
</form>