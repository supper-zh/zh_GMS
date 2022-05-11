package com.zh.service;

import java.util.List;

import com.zh.po.Myfile;

public interface MyFileService {
	public Myfile toPdfProjBook(Long fId);
	public Myfile selectById(Long fId);
	public Myfile selectByfName(String fName);
	public int createMyfile(Myfile myfile);
	public int delete(Long fId);
	public List<Myfile> filelist(Myfile myfile);
}
