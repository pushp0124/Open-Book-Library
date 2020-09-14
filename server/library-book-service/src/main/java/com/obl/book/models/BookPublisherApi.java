package com.obl.book.models;

import java.util.List;

public class BookPublisherApi {
	private Long total_count;
	private List<BookPublisher> publishers;
	public Long getTotal_count() {
		return total_count;
	}
	public void setTotal_count(Long total_count) {
		this.total_count = total_count;
	}
	public List<BookPublisher> getPublishers() {
		return publishers;
	}
	public void setPublishers(List<BookPublisher> publishers) {
		this.publishers = publishers;
	}
	@Override
	public String toString() {
		return "BookPublisherApi [total_count=" + total_count + ", publishers=" + publishers + "]";
	}
	
	
}
