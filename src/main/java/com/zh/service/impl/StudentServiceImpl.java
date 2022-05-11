package com.zh.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zh.dao.StudentMapper;
import com.zh.po.BaseDept;
import com.zh.po.BaseMajor;
import com.zh.po.Student;
import com.zh.po.StudentExample;
import com.zh.service.StudentService;
@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	private StudentMapper studentMapper;
	
	/**
	 * 通过id，密码查询学生
	 */
	@Override
	public Student findStudent(String sId, String sPwd) {
		
		StudentExample example = new StudentExample();
		//用来封装查询条件的
		StudentExample.Criteria criteria=example.createCriteria();
		
		criteria.andSIdEqualTo(sId);
		criteria.andSPwdEqualTo(sPwd);
		criteria.andSStateEqualTo(1);
		List<Student> list= studentMapper.selectByExample(example);
		if (list!=null&& list.size()>0) {
			 return list.get(0);
		}
		return null;
	}
	
	/**
	 * 通过id查询学生
	 */
	@Override
	public Student findStudentById(String sId) {
		StudentExample example = new StudentExample();
		//用来封装查询条件的
		StudentExample.Criteria criteria=example.createCriteria();
		
		criteria.andSIdEqualTo(sId);
		
		List<Student> list= studentMapper.selectByExample(example);
		//select * from Student where sId=? and sPwd=?
		if (list!=null&& list.size()>0) {
			 return list.get(0);
		}
		return null;
	}

	/**
	 * 修改学生信息
	 */
	@Override
	public int editInfo(Student student) {
		return studentMapper.updateByPrimaryKeySelective(student);
	}

	@Override
	public int findStudentSum(String major) {
		StudentExample example = new StudentExample();
		StudentExample.Criteria criteria=example.createCriteria();
		criteria.andMajorEqualTo(major);
		return studentMapper.countByExample(example);
	}

	@Override
	public List<Student> findStudnetBySeltitlState(String major) {
		return studentMapper.findStudnetBySeltitlState(major);
	}

	@Override
	public List<Student> Studentlist(Student student) {
		List<Student> list = null;
		StudentExample example = new StudentExample();
		//用来封装查询条件的
		StudentExample.Criteria criteria=example.createCriteria();
		if(student!=null) {
			if(student.getsName() !=null&&!"".equals(student.getsName())) {
				criteria.andSNameLike("%" + student.getsName() + "%");
				example.setOrderByClause("s_state desc");
				list = studentMapper.selectByExample(example);
				return list;
			}
		}
		example.setOrderByClause("s_state desc");
		list = studentMapper.selectByExample(example);
		return list;
	}

	@Override
	public int createStudent(Student student) {
		return studentMapper.insert(student);
	}

	@Override
	public int editInfo1(BaseDept baseDept,String oldName) {
		return studentMapper.editInfo1(baseDept.getDeptName(),oldName);
	}

	@Override
	public int editInfo2(BaseMajor baseMajor) {
		return studentMapper.editInfo2(baseMajor.getMajorName(),baseMajor.getMajorId());
	}



}
