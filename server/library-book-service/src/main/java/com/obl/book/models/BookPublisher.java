package com.obl.book.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class BookPublisher {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer publisherId;
	
	@Column(unique = true, nullable = false)
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