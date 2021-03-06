package com.zh.controller;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zh.po.BaseDept;
import com.zh.po.BaseMajor;
import com.zh.po.MidCheck;
import com.zh.po.OpenReport;
import com.zh.po.ProjBook;
import com.zh.po.ReplyGroup;
import com.zh.po.Review;
import com.zh.po.Review1;
import com.zh.po.ScoreProportion;
import com.zh.po.SelectTitle;
import com.zh.po.Student;
import com.zh.po.Teacher;
import com.zh.po.Thesis;
import com.zh.po.ThesisAttachment;
import com.zh.po.Title;
import com.zh.po.Title1;
import com.zh.service.BaseMajorService;
import com.zh.service.MidCheckService;
import com.zh.service.OpenReportService;
import com.zh.service.ProjBookService;
import com.zh.service.ReplyGroupService;
import com.zh.service.ReviewService;
import com.zh.service.ScoreProportionService;
import com.zh.service.SelectTitleService;
import com.zh.service.StudentService;
import com.zh.service.TeacherService;
import com.zh.service.ThesisAttachmentService;
import com.zh.service.ThesisService;
import com.zh.service.TitleService;

@Controller
public class TeacherController {
	
	private static final int PAGE_SIZE = 5;
	
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private BaseMajorService baseMajorService;
	
	@Autowired
	private TitleService titleService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private SelectTitleService selectTitleService;
	
	@Autowired
	private ProjBookService projBookService;
	
	@Autowired
	private OpenReportService openReportService;
	
	@Autowired
	private MidCheckService midCheckService;
	
	@Autowired
	private ThesisService thesisService;
	
	@Autowired
	private ThesisAttachmentService thesisAttachmentService;
	
	@Autowired
	private ReplyGroupService replyGroupService;
	
	@Autowired
	private ReviewService reviewService;
	
	@Autowired
	private ScoreProportionService scoreProportionService;
	
	@InitBinder
	 protected void initBinder(WebDataBinder binder) {
	     SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	     binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
	 }
	
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping(value = "/teacher/roleset.action", method = RequestMethod.GET)
	public String roleset(HttpSession session) {
		return "views/user/teacher/roleset";
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/teacher/toindex.action", method = RequestMethod.GET)
	public ModelAndView toIndex(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		int sum = titleService.findTitleSum1(teacher.gettId(),"?????????");
		int s = selectTitleService.findSelTitleListByState2(teacher.gettId(),"??????");
		int s1 = selectTitleService.findSelTitleListByState2(teacher.gettId(),"?????????");
		int sumProjBook = 0;
		int sumOpenReport = 0;
		int sumMidCheck = 0;
		int sumThesis = 0;
		List<ProjBook> list = projBookService.findProjBookBytIdAndAgree1(null, teacher.gettId(), "??????");
		if(list!=null) {
			sumProjBook = list.size();
		}
		List<OpenReport> list1 = openReportService.findOpenReportBytIdAndAgree1(null, teacher.gettId(), "??????");
		if(list1!=null) {
			sumOpenReport = list1.size();
		}
		List<MidCheck> list2 = midCheckService.findMidCheckBytIdAndAgree1(null, teacher.gettId(), "??????");
		if(list2!=null) {
			sumMidCheck = list2.size();
		}
		List<Thesis> list3 = thesisService.findThesisBytIdAndAgree1(null, teacher.gettId(), "??????");
		if(list3!=null) {
			sumThesis = list3.size();
		}
		
		ModelAndView mv = new ModelAndView();
		mv.addObject("sum", sum);
		mv.addObject("s", s);
		mv.addObject("s1", s1);
		mv.addObject("sumProjBook", sumProjBook);
		mv.addObject("sumOpenReport", sumOpenReport);
		mv.addObject("sumMidCheck", sumMidCheck);
		mv.addObject("sumThesis", sumThesis);
		mv.setViewName("views/user/teacher/index");
	    return mv;
	}
	
	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/teacher/totitlelist.action")
	public ModelAndView totitlelist(HttpSession session, @ModelAttribute("title") Title title,
			                                               @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		// pageNo ??????      pageSize ???????????????
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Title1>list = titleService.findTitleListBytId(title ,(String)teacher.gettId());
		List <BaseMajor> list1 = baseMajorService.findMajorBydeptId(teacher.getDeptId());
		PageInfo<Title1> pageInfo = new PageInfo<>(list,5);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", title);
		mv.addObject("BaseMajor", list1);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/titlelist");
		return mv;
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/teacher/getStudentInfoById.action")
	@ResponseBody
	public Student getStudentInfoById(String sId) {
		Student student = studentService.findStudentById(sId);
		return student;
	}
	
	/**
	 * ????????????(??????)
	 */
	@RequestMapping(value = "/teacher/titledelete.action")
	@ResponseBody
	public String titledelete(Long titlId) {
		int rows = titleService.deleteTitle(titlId);
	    if(rows > 0){			
	        return "OK";
	    }else{
	        return "FAIL";			
	    }
	}
	
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/teacher/createTitle.action")
	@ResponseBody
	public String createTitle(Title title,HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		title.settId(teacher.gettId());
		title.setTitlState("?????????");
		title.setSelState("????????????");
	    int rows = titleService.createTitle(title);
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ?????????????????????
	 */
	@RequestMapping("/teacher/createTitle1.action")
	@ResponseBody
	public String createTitle1(Title title) {
		String sId = title.getsId();
		title.setTitlState("?????????????????????");
		title.setSelState("????????????");
		title.setsId(null);
	    int rows = titleService.createTitle(title);
	    System.out.println(title.getTitlId());
	    if(rows > 0){
	    	SelectTitle selectTitle = new SelectTitle();
	    	selectTitle.setsId(sId);
	    	selectTitle.setTitlId(title.getTitlId());
	    	selectTitle.setSeltitlState("?????????");
	    	selectTitleService.createSelectTitle(selectTitle);
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/teacher/toSelecttitlelist.action")
	public ModelAndView toSelecttitlelist(HttpSession session, @ModelAttribute("title") Title title,
			                                                   @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		// pageNo ??????      pageSize ???????????????
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Title> list = selectTitleService.findSelTitleListByState3(title, teacher.gettId(), "?????????");
		PageInfo<Title> pageInfo = new PageInfo<>(list,5);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", title);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/selectTitlelist");
		return mv;
	}
	
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping(value = "/teacher/editSelTitle.action")
	@ResponseBody
	public String editSelTitle(SelectTitle selectTitle) {
		String titlState = titleService.findTitleById(selectTitle.getTitlId()).getTitlState();
		int rows = selectTitleService.updateSelTitle(selectTitle);
		if(rows > 0){
			Title title = new Title();
			title.setTitlId(selectTitle.getTitlId());
			if(selectTitle.getSeltitlState().equals("??????") && titlState.equals("?????????????????????")) {
				title.setTitlState("?????????");
				titleService.updateTitleById(title);
				SelectTitle selectTitle2 = new SelectTitle();
				selectTitle2.setsId(selectTitle.getsId());
				selectTitle2.setTitlId(selectTitle.getTitlId());
				selectTitle2.setSeltitlState("????????????????????????");
				selectTitleService.updateSelTitle(selectTitle2);
			}
			if(selectTitle.getSeltitlState().equals("??????")) {
				title.setSelState("????????????");
				titleService.updateTitleSelStateById(title);
				SelectTitle selectTitle1 = new SelectTitle();
				selectTitle1.setTitlId(selectTitle.getTitlId());
				selectTitle1.setSeltitlState("?????????");
				selectTitleService.updateSelTitle1(selectTitle1);
			}
			if(selectTitle.getSeltitlState().equals("??????") && titlState.equals("?????????????????????")) {
				title.setTitlState("???????????????");
				titleService.updateTitleById(title);
			}
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ?????????????????????(?????????)
	 */
	@RequestMapping("/teacher/projBooklist.action")
	public ModelAndView projBooklist(HttpSession session ,@ModelAttribute("projBook") ProjBook projBook ,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<ProjBook>list = projBookService.findProjBookBytIdAndAgree(projBook, teacher.gettId(),"?????????");
		PageInfo<ProjBook> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/projbooklist");
		return mv;
	}
	
	/**
	 * ?????????????????????(?????????)
	 */
	@RequestMapping("/teacher/projBooklist1.action")
	public ModelAndView projBooklist1(HttpSession session ,@ModelAttribute("projBook") ProjBook projBook ,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<ProjBook>list = projBookService.findProjBookBytIdAndAgree1(projBook, teacher.gettId(),"?????????");
		PageInfo<ProjBook> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/projbooklist1");
		return mv;
	}
	
	/**
	 * ????????????????????????(?????????)
	 */
	@RequestMapping("/teacher/openReportlist.action")
	public ModelAndView openReportlist(HttpSession session ,@ModelAttribute("openReport") OpenReport openReport ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<OpenReport>list = openReportService.findOpenReportBytIdAndAgree(openReport, teacher.gettId(),"?????????");
		PageInfo<OpenReport> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/openReportlist");
		return mv;
	}
	
	/**
	 * ????????????????????????(?????????)
	 */
	@RequestMapping("/teacher/openReportlist1.action")
	public ModelAndView openReportlist1(HttpSession session ,@ModelAttribute("openReport") OpenReport openReport ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<OpenReport>list = openReportService.findOpenReportBytIdAndAgree1(openReport, teacher.gettId(),"?????????");
		PageInfo<OpenReport> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/openReportlist1");
		return mv;
	}
	
	/**
	 * ????????????????????????(?????????)
	 */
	@RequestMapping("/teacher/midChecklist.action")
	public ModelAndView midChecklist(HttpSession session ,@ModelAttribute("midCheck") MidCheck midCheck ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<MidCheck>list = midCheckService.findMidCheckBytIdAndAgree(midCheck, teacher.gettId(),"?????????");
		PageInfo<MidCheck> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/midChecklist");
		return mv;
	}
	
	/**
	 * ????????????????????????(?????????)
	 */
	@RequestMapping("/teacher/midChecklist1.action")
	public ModelAndView midChecklist1(HttpSession session ,@ModelAttribute("midCheck") MidCheck midCheck ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<MidCheck>list = midCheckService.findMidCheckBytIdAndAgree1(midCheck, teacher.gettId(),"?????????");
		PageInfo<MidCheck> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/midChecklist1");
		return mv;
	}
	
	/**
	 * ??????????????????(?????????)
	 */
	@RequestMapping("/teacher/thesislist.action")
	public ModelAndView thesislist(HttpSession session ,@ModelAttribute("thesis") Thesis thesis ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Thesis>list = thesisService.findThesisBytIdAndAgree(thesis, teacher.gettId(),"?????????");
		PageInfo<Thesis> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/thesislist");
		return mv;
	}
	
	/**
	 * ??????????????????(?????????)
	 */
	@RequestMapping("/teacher/thesislist1.action")
	public ModelAndView thesislist1(HttpSession session ,@ModelAttribute("thesis") Thesis thesis ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Thesis>list = thesisService.findThesisBytIdAndAgree1(thesis, teacher.gettId(),"?????????");
		PageInfo<Thesis> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/thesislist1");
		return mv;
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping("/teacher/thesisAttachmentlist.action")
	public ModelAndView thesisAttachmentlist(HttpSession session ,@ModelAttribute("thesisAttachment") ThesisAttachment thesisAttachment ,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, PAGE_SIZE);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<ThesisAttachment>list = thesisAttachmentService.findThesisAttachmentList1(thesisAttachment, (String)teacher.gettId());
		PageInfo<ThesisAttachment> pageInfo = new PageInfo<>(list,5);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/thesisAttachmentlist");
		return mv;
	}
	
	/**
	 * ??????????????????(????????????)
	 */
	@RequestMapping("/teacher/studentScore.action")
	public ModelAndView studentScore (HttpSession session,@RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum){
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Title1>list = titleService.findTitleListBytId11((String)teacher.gettId());
		ScoreProportion scoreProportion = scoreProportionService.getScoreProportion("1");
		PageInfo<Title1> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("scoreProportion", scoreProportion);
		mv.setViewName("views/user/teacher/studentscore");
		return mv;
	}
	
	/**
	 * ???????????????????????????(????????????)
	 */
	@RequestMapping("/teacher/editStudentScore.action")
	@ResponseBody
	public String studentScore (SelectTitle selectTitle){
		System.out.println(selectTitle.getsId());
		System.out.println(selectTitle.getTitlId());
		System.out.println(selectTitle.gettScore());
		int rows = 0;
		try {
			rows = selectTitleService.updateSelTitle(selectTitle);
		} catch(Exception e) {
			
		}
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ?????????????????????????????????(??????)
	 */
	@RequestMapping(value = "/teacher/topersonInfo.action")
	public String topersonInfo(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		teacher = teacherService.findTeacher(teacher.gettId(), teacher.gettPwd());
		session.setAttribute("USER_INFO", teacher);
		return "views/user/teacher/personInfo";
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/teacher/editInfo.action")
	@ResponseBody
	public String editInfo(Teacher teacher) {
		int rows =0;
		try{
			rows = teacherService.editInfo(teacher);
			}catch(Exception e){
				rows =0;
			}
		if(rows > 0){
	    	System.out.println("OK");
	        return "OK";
	    }else{
	    	System.out.println("FAIL");
	        return "FAIL";
	    }
	}
	
	
	/**
	 * ???????????????????????????(??????)
	 */
	@RequestMapping(value = "/teacher/toeditPwd.action")
	public String toeditPwd(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		teacher = teacherService.findTeacherById(teacher.gettId());
		session.setAttribute("USER_INFO", teacher);
		return "views/user/teacher/editPwd";
	}
	
	/**
	 * ??????????????????
	 */
	@RequestMapping(value = "/teacher/editPwd.action")
	@ResponseBody
	public String editPwd(Teacher teacher) {
		int rows =0;
		try{
			rows = teacherService.editInfo(teacher);
			}catch(Exception e){
				
			}
		if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}

	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/teacher/leading/Index.action", method = RequestMethod.GET)
	public ModelAndView Index(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		int sum = studentService.findStudentSum(teacher.getMajor());
		int s = titleService.findTitleSum2(teacher.getMajor(),"????????????");
		int s1 = sum - s;
		int sumProjBook = 0;
		int sumOpenReport = 0;
		int sumMidCheck = 0;
		int sumThesis = 0;
		List<ProjBook> list = projBookService.findProjBookByMajorAndAgree(null, teacher.getMajor(), "??????");
		if(list!=null) {
			sumProjBook = list.size();
		}
		List<OpenReport> list1 = openReportService.findOpenReportByMajorAndAgree(null, teacher.getMajor(), "??????");
		if(list1!=null) {
			sumOpenReport = list1.size();
		}
		List<MidCheck> list2 = midCheckService.findMidCheckByMajorAndAgree(null, teacher.getMajor(), "??????");
		if(list2!=null) {
			sumMidCheck = list2.size();
		}
		List<Thesis> list3 = thesisService.findThesisByMajorAndAgree(null, teacher.getMajor(), "??????");
		if(list3!=null) {
			sumThesis = list3.size();
		}
		ModelAndView mv = new ModelAndView();
		mv.addObject("sum", sum);
		mv.addObject("s", s);
		mv.addObject("s1", s1);
		mv.addObject("sumProjBook",sumProjBook);
		mv.addObject("sumOpenReport",sumOpenReport);
		mv.addObject("sumMidCheck",sumMidCheck);
		mv.addObject("sumThesis",sumThesis);
		mv.setViewName("views/user/teacher/leading/Index");
	    return mv;
	}
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/teacher/leading/totitlelist.action")
	public ModelAndView titlelist(HttpSession session, @ModelAttribute("title") Title title,
			                                               @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		// pageNo ??????      pageSize ???????????????
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		title.setMajor(teacher.getMajor());
		title.setTitlState("?????????");
		List<Title1>list = titleService.findTitleListBytitlState(title);
		List<Student> list1= studentService.findStudnetBySeltitlState(teacher.getMajor());
		PageInfo<Title1> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", title);
		mv.addObject("list1", list1);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/leading/titlelist");
		return mv;
	}
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/teacher/leading/totitlelist1.action")
	public ModelAndView titlelist1(HttpSession session, @ModelAttribute("title") Title title,
			                                               @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		// pageNo ??????      pageSize ???????????????
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		title.setMajor(teacher.getMajor());
		title.setTitlState("?????????");
		List<Title1>list = titleService.findTitleListBytitlState1(title);
		PageInfo<Title1> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("title", title);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/leading/titlelist1");
		return mv;
	}
	
	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/teacher/leading/checkTitle.action")
	@ResponseBody
	public String checkTitle(Title title) {
		SelectTitle selectTitle = new SelectTitle();
		selectTitle = selectTitleService.findBytitlId(title.getTitlId());
		if(selectTitle!=null) {
			if(title.getTitlState().equals("???????????????") && selectTitle.getSeltitlState().equals("????????????????????????")) {
				selectTitle.setSeltitlState("??????");
				selectTitleService.updateSelTitle(selectTitle);
			}
			if(title.getTitlState().equals("?????????") && selectTitle.getSeltitlState().equals("????????????????????????")) {
				selectTitle.setSeltitlState("??????");
				selectTitleService.updateSelTitle(selectTitle);
			}
		}
		int rows = titleService.updateTitleById(title);
		if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ??????????????????????????????????????????????????????
	 */
	@RequestMapping(value = "/teacher/leading/setStudent.action")
	@ResponseBody
	public String setStudent(HttpSession session,HttpServletRequest request,SelectTitle selectTitle) {
		selectTitle.setSeltitlState("??????");
		int rows =0;
		try{
			rows = selectTitleService.createSelectTitle(selectTitle);
			}catch(Exception e){
				
			}
		if(rows > 0){
			Title title = new Title();
			title.setTitlId(selectTitle.getTitlId());
			title.setSelState("????????????");
			titleService.updateTitleSelStateById(title);
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ????????????????????????????????????????????????
	 */
	@RequestMapping("/teacher/leading/projBooklist.action")
	public ModelAndView ProjBooklist(HttpSession session ,@ModelAttribute("projBook") ProjBook projBook ,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<ProjBook>list = projBookService.findProjBookByMajorAndAgree(projBook, teacher.getMajor(), "??????");
		PageInfo<ProjBook> pageInfo = new PageInfo<>(list,10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/leading/projbooklist");
		return mv;
	}
	
	/**
	 * ???????????????????????????????????????????????????
	 */
	@RequestMapping("/teacher/leading/openReportlist.action")
	public ModelAndView OpenReportlist(HttpSession session ,@ModelAttribute("openReport") OpenReport openReport ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<OpenReport>list = openReportService.findOpenReportByMajorAndAgree(openReport, teacher.getMajor(),"??????");
		PageInfo<OpenReport> pageInfo = new PageInfo<>(list,10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/leading/openReportlist");
		return mv;
	}

	/**
	 * ???????????????????????????????????????????????????
	 */
	@RequestMapping("/teacher/leading/midChecklist.action")
	public ModelAndView MidCheckList(HttpSession session ,@ModelAttribute("midCheck") MidCheck midCheck ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<MidCheck>list = midCheckService.findMidCheckByMajorAndAgree(midCheck, teacher.getMajor(),"??????");
		PageInfo<MidCheck> pageInfo = new PageInfo<>(list,10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/leading/midChecklist");
		return mv;
	}
	
	/**
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping("/teacher/leading/thesislist.action")
	public ModelAndView Thesislist(HttpSession session ,@ModelAttribute("thesis") Thesis thesis ,
                                                            @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Thesis>list = thesisService.findThesisByMajorAndAgree(thesis, teacher.getMajor(),"??????");
		PageInfo<Thesis> pageInfo = new PageInfo<>(list,10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/leading/thesislist");
		return mv;
	}
	
	/**
	 * ?????????????????????????????????
	 */
	@RequestMapping("/teacher/leading/thesisAttachmentlist.action")
	public ModelAndView ThesisAttachmentlist(HttpSession session ,@ModelAttribute("thesisAttachment") ThesisAttachment thesisAttachment ,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<ThesisAttachment>list = thesisAttachmentService.findThesisAttachmentListByMajor(thesisAttachment, (String)teacher.getMajor());
		PageInfo<ThesisAttachment> pageInfo = new PageInfo<>(list,10);
        ModelAndView mv = new ModelAndView();
        mv.addObject("pageInfo", pageInfo);
        mv.setViewName("views/user/teacher/leading/thesisAttachmentlist");
		return mv;
	}
	
	/**
	 * ??????????????????(?????????????????????)
	 */
	@RequestMapping("/teacher/leading/studentScore.action")
	public ModelAndView StudentScore (HttpSession session,@ModelAttribute("title") Title title,
			                                              @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum){
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Title1>list = titleService.findTitleListBymajor(title,teacher.getMajor());
		ScoreProportion scoreProportion = scoreProportionService.getScoreProportion("1");
		PageInfo<Title1> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("scoreProportion", scoreProportion);
		mv.setViewName("views/user/teacher/leading/studentScore");
		return mv;
	}
	
	/**
	 * ?????????????????????????????????(?????????????????????)
	 */
	@RequestMapping("/teacher/leading/editStudentScore.action")
	@ResponseBody
	public String editStudentScore (SelectTitle selectTitle){
		int rows = 0;
		try {
			rows = selectTitleService.updateSelTitle(selectTitle);
		} catch(Exception e) {
			
		}
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ???????????????????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/toreply.action")
	public ModelAndView reply(HttpSession session,@RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List <ReplyGroup> list = replyGroupService.replyGrouplist(teacher.gettId());
		PageInfo<ReplyGroup> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/leading/reply");
		return mv;
	}
	
	/**
	 * ??????????????????????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/tocreateReply.action")
	public ModelAndView tocreateReply(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List<Teacher> list= teacherService.findTeacherBydept(teacher.getDept());
		ModelAndView mv = new ModelAndView();
		mv.addObject("list", list);
		mv.setViewName("views/user/teacher/leading/newReply");
		return mv;
	}
	
	/**
	 * ???????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/createReply.action")
	@ResponseBody
	public String createReply(ReplyGroup replyGroup) {
		
		int rows = 0;
		try {
			rows = replyGroupService.createReply(replyGroup);
		}catch(Exception e) {
			rows = 0;
		}
	    if(rows > 0){
	    	String replyMember = replyGroup.getReplyMember();
			int l = replyMember.length();
			String s = replyMember.replaceAll(",","");
			int ll = s.length();
			int cc = l - ll + 1;
			String []member = new String[2];
			for(int i = 0;i < cc;i++) {
				member[i] = replyMember.substring(0, replyMember.indexOf(" "));
				System.out.println("????????????"+member[i]);
				replyMember = replyMember.substring(replyMember.indexOf(",") + 1,replyMember.length()-1);
			}
			String replyStudent = replyGroup.getReplyStudent();
			int l1 = replyStudent.length();
			String s1 = replyStudent.replaceAll(",","");
			int l2 = s1.length();
			int c = l1 - l2 + 1;
			String []student = new String[4];
			for(int i = 0;i < c;i++) {
				student[i] = replyStudent.substring(0, replyStudent.indexOf(" "));
				List<Thesis> list = thesisService.findThesisBytIdAndAgreeAndBatch(student[i], "??????",replyGroup.getBatch());
				for(int m = 0;m < list.size();m++) {
					Review review = new Review();
					review.setsId(list.get(m).getsId());
					review.setMemberTId(replyGroup.getReplyLeader().substring(0, replyGroup.getReplyLeader().indexOf(" ")));
					review.setReplyId(replyGroup.getReplyId());
					review.setReviewScore(0);
					review.setReviewType("??????????????????");
					reviewService.insertReview(review);
				}
				for(int j = 0;j < cc;j++) {
					for(int k = 0;k < list.size();k++) {
						Review review = new Review();
						review.setsId(list.get(k).getsId());
						review.setMemberTId(member[j]);
						review.setReplyId(replyGroup.getReplyId());
						review.setReviewScore(0);
						review.setReviewType("??????????????????");
						reviewService.insertReview(review);
					}
				}
				System.out.println("????????????"+student[i]+"????????????");
				replyStudent = replyStudent.substring(replyStudent.indexOf(",") + 1,replyStudent.length()-1);
			}
	        return "OK";
	        
	    }else{
	        return "FAIL";
	    }
	}
	/**
	 * ???????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/replydelete.action")
	@ResponseBody
	public String replydelete(String replyId) {
		int rows = replyGroupService.deleteReply(replyId);
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ?????????????????????????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/topersonInfo.action")
	public String TopersonInfo(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		teacher = teacherService.findTeacher(teacher.gettId(), teacher.gettPwd());
		session.setAttribute("USER_INFO", teacher);
		return "views/user/teacher/leading/personInfo";
	}	
	
	/**
	 * ???????????????????????????(???????????????)
	 */
	@RequestMapping(value = "/teacher/leading/toeditPwd.action")
	public String ToeditPwd(HttpSession session) {
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		teacher = teacherService.findTeacherById(teacher.gettId());
		session.setAttribute("USER_INFO", teacher);
		return "views/user/teacher/leading/editPwd";
	}
	
	// ????????????????????????
	@RequestMapping("/teacher/leading/export.action")
	public void export(HttpSession session,HttpServletResponse response) {
		
	HSSFWorkbook book = new HSSFWorkbook();
	//??????sheet
	HSSFSheet sheet = book.createSheet("???????????????");
	sheet.setDefaultColumnWidth(15);
	sheet.setColumnWidth(3, 40*256);
	sheet.setColumnWidth(5, 40*256);
	sheet.setColumnWidth(8, 40*256);
	sheet.setColumnWidth(11, 40*256);
	sheet.setColumnWidth(14, 40*256);

	//???????????????
	HSSFRow titleRow = sheet.createRow(0);
	//?????????????????????????????????
	titleRow.createCell(0).setCellValue("??????");
	titleRow.createCell(1).setCellValue("??????");
	titleRow.createCell(2).setCellValue("????????????");
	titleRow.createCell(3).setCellValue("??????");
	titleRow.createCell(4).setCellValue("????????????");
	titleRow.createCell(5).setCellValue("??????????????????");
	titleRow.createCell(6).setCellValue("??????????????????");
	titleRow.createCell(7).setCellValue("???????????????");
	titleRow.createCell(8).setCellValue("?????????????????????");
	titleRow.createCell(9).setCellValue("?????????????????????");
	titleRow.createCell(10).setCellValue("????????????");
	titleRow.createCell(11).setCellValue("??????????????????");
	titleRow.createCell(12).setCellValue("??????????????????");
	titleRow.createCell(13).setCellValue("????????????");
	titleRow.createCell(14).setCellValue("??????????????????");
	titleRow.createCell(15).setCellValue("??????????????????");
	titleRow.createCell(16).setCellValue("?????????");
	
	Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
	List<Title1>list = titleService.findTitleListBymajor2(teacher.getMajor());

	for (int i = 0; i < list.size(); i++) {

	Title1 title = list.get(i);

	HSSFRow row = sheet.createRow(i+1);
	
	row.createCell(0).setCellValue(i+1);
	row.createCell(1).setCellValue(title.getsId());
	row.createCell(2).setCellValue(title.getsName());
	row.createCell(3).setCellValue(title.getTitlName());
	row.createCell(4).setCellValue(title.gettName());
	if(title.gettComments()==null) {
		row.createCell(5).setCellValue("");
	}else {
		row.createCell(5).setCellValue(title.gettComments());
	}
	if(title.gettScore()==0) {
		row.createCell(6).setCellValue("");
	}else {
		row.createCell(6).setCellValue(title.gettScore());
	}
	
	if(title.getReplyScore()==null) {
		row.createCell(7).setCellValue("");
		row.createCell(8).setCellValue("");
		row.createCell(9).setCellValue("");
		row.createCell(10).setCellValue("");
		row.createCell(11).setCellValue("");
		row.createCell(12).setCellValue("");
		row.createCell(13).setCellValue("");
		row.createCell(14).setCellValue("");
		row.createCell(15).setCellValue("");
	}else {
		List<Review1> list1 = reviewService.findBySId(title.getsId());
		row.createCell(7).setCellValue(list1.get(0).getMemberTName());
		row.createCell(8).setCellValue(list1.get(0).getReviewComments());
		row.createCell(9).setCellValue(list1.get(0).getReviewScore());
		row.createCell(10).setCellValue(list1.get(1).getMemberTName());
		row.createCell(11).setCellValue(list1.get(1).getReviewComments());
		row.createCell(12).setCellValue(list1.get(1).getReviewScore());
		row.createCell(13).setCellValue(list1.get(2).getMemberTName());
		row.createCell(14).setCellValue(list1.get(2).getReviewComments());
		row.createCell(15).setCellValue(list1.get(2).getReviewScore());
	}
	
	if(title.gettScore()==0 ||title.getReplyScore()==null) {
		row.createCell(16).setCellValue("????????????");
	}
	else {
		row.createCell(16).setCellValue(title.gettScore()*0.5 + title.getReplyScore());
	}
	
	}

	try {
	//???????????????,?????????????????????????????????
	response.addHeader("Content-Disposition",
	"attachment;filename=" + new String("???????????????.xls".getBytes(), "ISO-8859-1"));
	book.write(response.getOutputStream());
	} catch (Exception e) {
	e.printStackTrace();
	}
	}

	/**
	 * ???????????????????????????
	 */
	@RequestMapping(value = "/teacher/toreview.action")
	public ModelAndView review(HttpSession session,@ModelAttribute("review") Review1 review,
			                                       @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		List <Review1> list = reviewService.findByMemberId(review,teacher.gettId());
		String replyLeader = teacher.gettId()+" "+teacher.gettName();
		List <Review1> list2 = reviewService.findByReplyLeader(replyLeader);
		int i = 0;
		PageInfo<Review1> pageInfo = new PageInfo<>(list,10);
		ModelAndView mv = new ModelAndView();
		if(list2.size()!=0) {
			i = 1;
		}
		mv.addObject("i", i);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/review");
		return mv;
	}
	
	/**
	 * ????????????????????????????????????
	 */
	@RequestMapping(value = "/teacher/editReviewScore.action")
	@ResponseBody
	public String editReviewScore (Review review){
		int rows = 0;
		try {
			rows = reviewService.updateReview(review);
		} catch(Exception e) {
			
		}
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
	
	/**
	 * ?????????????????????????????????????????????
	 */
	@RequestMapping(value = "/teacher/toReplyleader.action")
	public ModelAndView toReplyleader(HttpSession session,@ModelAttribute("review") Review1 review,
                                                   @RequestParam(value="pageNum",required=false,defaultValue="1") int pageNum) {
		PageHelper.startPage(pageNum, 10);
		Teacher teacher = (Teacher)session.getAttribute("USER_INFO");
		String replyLeader = teacher.gettId()+" "+teacher.gettName();
		List <Review1> list2 = reviewService.findReviewByRLer(review,replyLeader);
		int i = 0;
		PageInfo<Review1> pageInfo = new PageInfo<>(list2,10);
		ModelAndView mv = new ModelAndView();
		if(list2.size()!=0) {
			i = 1;
		}
		mv.addObject("i", i);
		mv.addObject("pageInfo", pageInfo);
		mv.setViewName("views/user/teacher/replyLeader");
		return mv;
	}
	
	/**
	 * ??????????????????????????????
	 */
	@RequestMapping(value = "/teacher/getReviewBySId.action")
	@ResponseBody
	public List<Review1> getReviewBySId(String sId){
		List<Review1> list = null;
	  try{
		  list = reviewService.findBySId(sId);
	  }catch(Exception e){
	     
	  }
	  return list;
	}
	
	/**
	 * ????????????????????????
	 */
	@RequestMapping(value = "/teacher/saveScore.action")
	@ResponseBody
	public String saveScore(String sId,Long titlId){
		int rows = 0;
		List<Review1> list = null;
		int flag = 0;
		try {
			list = reviewService.findBySId(sId);
		} catch(Exception e) {
			
		}
		for(int i = 0;i<list.size();i++) {
			if(list.get(i).getReviewScore()==0) {
				flag = 1;
			}
		}
		if(flag == 1) {
			return "FAIL1";
		}
		int score1 = 0;
		int score2 = 0;
		int count = 0;
		for(int i = 0;i<list.size();i++) {
			if(i == 0) {
				score1 = list.get(i).getReviewScore();
			}else {
				score2 += list.get(i).getReviewScore();
				count++;
			}
		}
		ScoreProportion scoreProportion = scoreProportionService.getScoreProportion("1");
		double replyScore = score1 * scoreProportion.getLeaderScoreProportion() + (score2/count)*(scoreProportion.getReviewScoreProportion());
		DecimalFormat df = new DecimalFormat("#.00");
		replyScore = Double.parseDouble(df.format(replyScore));
		try {
			SelectTitle selectTitle = new SelectTitle();
			selectTitle.setsId(sId);
			selectTitle.setTitlId(titlId);
			selectTitle.setReplyScore(replyScore);
			rows = selectTitleService.updateReplyScore(selectTitle);
		} catch(Exception e) {
			
		}
	    if(rows > 0){
	        return "OK";
	    }else{
	        return "FAIL";
	    }
	}
}
