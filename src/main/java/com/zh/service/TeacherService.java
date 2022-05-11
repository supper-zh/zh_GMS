package com.zh.service;

import java.util.List;

import com.zh.po.BaseDept;
import com.zh.po.BaseMajor;
import com.zh.po.Teacher;

public interface TeacherService {
	public Teacher findTeacher(String tId, String tPwd);
	public Teacher findTeacherById(String tId);
	public int editInfo(Teacher teacher);
	public List <Teacher> Teacherlist(Teacher teacher);
	public int createTeacher(Teacher teacher);
	public int editInfo1(BaseDept baseDept);
	public int editInfo2(BaseMajor baseMajor);
	public List<Teacher> findTeacherBydept(String dept);
}
