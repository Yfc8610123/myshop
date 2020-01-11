package com.yfc.my.shop.commoms.persistence;

import java.io.Serializable;
import java.util.Date;
/**
 * 实体类基类
 * <p>title:BaseEntity</p>
 * <p>description:</p>
 *
 *@author yfc
 *@version 1.0.0
 *@date 2019/12/30 16:03
*/
public abstract class BaseEntity implements Serializable {
    private Long id;
    private Date created;
    private Date updated;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
