package com.game.db.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.game.db.bean.RoleDataProxy;

public interface RoleDataDb {

	/**
	 * 添加一个 roledata
	 * @param roleData
	 * @return
	 */
    public int insertRoleData(RoleDataProxy roleData);
    
    public int  updateRoleData(RoleDataProxy roleData);
    
	
    /**
     * 根据uin 查找一个 roledata
     * @param Uin
     * @return
     */
	public List<RoleDataProxy> selectRoleData(@Param("Uin")int Uin);
}
