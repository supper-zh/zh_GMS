package com.zh.service;

import com.zh.po.Admin;

public interface AdminService {
	public Admin findAdmin(String adminId, String adminPwd);
	public Admin getAdmin(String adminId);
	public int editInfo(Admin admin);
}
