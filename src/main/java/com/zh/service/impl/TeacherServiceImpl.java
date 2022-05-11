package com.zh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.TeacherMapper;
import com.zh.po.BaseDept;
import com.zh.po.BaseMajor;
import com.zh.po.Teacher;
import com.zh.po.TeacherExample;
import com.zh.service.TeacherService;
@Service
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;
	
	
	@Override
	public Teacher findTeacher(String tId, String tPwd) {
		
		TeacherExample example = new TeacherExample();
		//用来封装查询条件的
		TeacherExample.Criteria criteria=example.createCriteria();
		
		criteria.andTIdEqualTo(tId);
		criteria.andTPwdEqualTo(tPwd);
		criteria.andTStateEqualTo(1);
		
		List<Teacher> list= teacherMapper.selectByExample(example);
		//select * from Teacher where tId=? and tPwd=?
		if (list!=null&& list.size()>0) {
			 return list.get(0);
		}
		return null;
	}


	@Override
	public Teacher findTeacherById(String tId) {
		TeacherExample example = new TeacherExample();
		//用来封装查询条件的
		TeacherExample.Criteria criteria=example.createCriteria();
		
		criteria.andTIdEqualTo(tId);
		
		List<Teacher> list= teacherMapper.selectByExample(example);
		//select * from Teacher where tId=?
		if (list!=null&& list.size()>0) {
			 return list.get(0);
		}
		return null;
	}


	@Override
	public int editInfo(Teacher teacher) {
		int result = 0;
		result = teacherMapper.updateByPrimaryKeySelective(teacher);
		
		if(teacher.getPower()!=null && teacher.getPower().equals("否")) {
			teacherMapper.update1(teacher.gettId());
		}
		System.out.println(result);
		return result;
	}


	@Override
	public List<Teacher> Teacherlist(Teacher teacher) {
		List<Teacher> list = null;
		TeacherExample example = new TeacherExample();
		//用来封装查询条件的
		TeacherExample.Criteria criteria=example.createCriteria();
		if(teacher!=null) {
			if(teacher.gettName() !=null&&!"".equals(teacher.gettName())) {
				criteria.andTNameLike("%" + teacher.gettName() + "%");
				example.setOrderByClause("t_state desc");
				list = teacherMapper.selectByExample(example);
				return list;
			}
		}
		example.setOrderByClause("t_state desc");
		list = teacherMapper.selectByExample(example);
		return list;
	}


	@Override
	public int createTeacher(Teacher teacher) {
		return teacherMapper.insert(teacher);
	}


	@Override
	public int editInfo1(BaseDept baseDept) {
		return teacherMapper.editInfo1(baseDept.getDeptName(),baseDept.getDeptId());
	}


	@Override
	public int editInfo2(BaseMajor baseMajor) {
		return teacherMapper.editInfo2(baseMajor.getMajorName(),baseMajor.getMajorId());
	}


	@Override
	public List<Teacher> findTeacherBydept(String dept) {
		return teacherMapper.findTeacherBydept(dept);
	}

}
