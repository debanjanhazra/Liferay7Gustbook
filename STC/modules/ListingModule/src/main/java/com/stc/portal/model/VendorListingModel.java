package com.stc.portal.model;

public class VendorListingModel {
	public String tenderName;
	public String tenderDesc;
	public String docPath;
	public String workId;
	public String getWorkId() {
		return workId;
	}

	public void setWorkId(String workId) {
		this.workId = workId;
	}

	public String getTenderName() {
		return tenderName;
	}

	public void setTenderName(String tenderName) {
		this.tenderName = tenderName;
	}

	public String getTenderDesc() {
		return tenderDesc;
	}

	public void setTenderDesc(String tenderDesc) {
		this.tenderDesc = tenderDesc;
	}

	public String getDocPath() {
		return docPath;
	}

	public void setDocPath(String docPath) {
		this.docPath = docPath;
	}
}
