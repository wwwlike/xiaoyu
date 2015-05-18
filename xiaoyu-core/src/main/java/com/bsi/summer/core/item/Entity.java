package com.bsi.summer.core.item;

import java.io.Serializable;

import javax.persistence.EmbeddedId;

import com.bsi.summer.core.annotation.FieldInfo;

/**
 * ITEM抽象实现类  实现了 hashCode 和 equals
 * 复合主键 getId()方法上面要加入 @EmbeddedId 注解
 * 
 * @author 董立超
 */
public abstract class Entity<T extends Serializable> implements Item<T>{
	@FieldInfo(name="编号")
	public T id;
    
    
    @Override
	public int hashCode() {
		return id == null ? System.identityHashCode(this) : id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass().getPackage() != obj.getClass().getPackage()) {
			return false;
		}
		final BaseBean other = (BaseBean) obj;
		if (id == null) {
			if (other.getId() != null) {
				return false;
			}
		} else if (!id.equals(other.getId())) {
			return false;
		}
		return true;
	}

	public abstract T getId();

	public abstract void setId(T id) ;
    
}