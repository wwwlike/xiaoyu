package com.bsi.summer.core.model;

import java.util.Date;

/**
 * 可审核的实体类接口
 * @author Roly
 * @CreateDate 2008-5-13
 */
public interface IAuditBean  {

    public Date getAudit_date();
    
    public void setAudit_date(Date audit_date);
    
    public String getAudit_msg();
    
    public void setAudit_msg(String audit_msg);
    
    public String getAudit_person();
    
    public void setAudit_person(String audit_person);
    
    public String getAudit_status();
    
    public void setAudit_status(String audit_status);
}
