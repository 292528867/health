package com.wonders.xlab.healthcloud.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.AbstractPersistable

import javax.persistence.MappedSuperclass
import javax.persistence.Temporal

import static javax.persistence.TemporalType.TIMESTAMP

@MappedSuperclass
abstract class AbstractBaseEntity<ID extends Serializable> extends AbstractPersistable<ID> {

	static final long serialVersionUID = 2834452590374861385L

	@CreatedDate
	@Temporal(TIMESTAMP)
	private Date createdDate;

	@LastModifiedDate
	@Temporal(TIMESTAMP)
	private Date lastModifiedDate;

	Date getCreatedDate() {
		return createdDate
	}

	void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate
	}

	Date getLastModifiedDate() {
		return lastModifiedDate
	}

	void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate
	}
}
