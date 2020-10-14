package com.obl.mailservice.models;


public class BookPublisher {

	private Integer publisherId;
	
	private String publisherName;

	public Integer getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Integer publisherId) {
		this.publisherId = publisherId;
	}

	public String getPublisherName() {
		return publisherName;
	}

	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

	@Override
	public String toString() {
		return "BookPublisher [publisherId=" + publisherId + ", publisherName=" + publisherName + "]";
	}
	
}