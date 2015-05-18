package com.bsi.summer.core.model;


/**
 * 查询权限接口
 * @author Roly
 * @CreateDate 2008-3-17
 */
public interface IAccessItem {
    
    public void setOwn_dpt(String own_dpt);
    
    public String getOwn_dpt();
    
    public void setOwn_usr(String own_usr);
    
    public String getOwn_usr();
}
