package com.bsi.summer.core.item;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.GenericGenerator;

/**
 * 主键为uuid的 实体bean的基类
 * 
 * @author dlc
 * @version 创建时间：2011-1-2
 */

@MappedSuperclass
public class BaseBean extends Entity<String> {

	private static final long serialVersionUID = -8940217011530662620L;
	//public String id;// ID
	@Id
	@Column(length = 32, nullable = true)
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		if (StringUtils.isEmpty(id)) {
			id = null;
		}
		this.id = id;
	}
	

}
