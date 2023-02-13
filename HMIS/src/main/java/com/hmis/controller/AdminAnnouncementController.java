package com.hmis.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hmis.domain.AnnouncementVO;
import com.hmis.domain.PageMaker;
import com.hmis.domain.SearchCriteria;
import com.hmis.service.AnnouncementService;

@Controller
@RequestMapping("admin/announcement/*")
public class AdminAnnouncementController {

	@Inject
	private AnnouncementService service;

	private static Logger logger = LoggerFactory.getLogger(AdminAnnouncementController.class);

	// 관리자 :: 취업 공고 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("Admin Announcement List Form.....");
		logger.info(cri.toString());

		model.addAttribute("list", service.selectAllAnnouncements(cri));

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.selectAllAnnouncementsCount(cri));
		model.addAttribute("pageMaker", pageMaker);

	}

	// 2. 관리자 :: 상세보기
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(@RequestParam("annNo") int annNo, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {
		logger.info("readPage.........");
		// 취업공고 내용
		model.addAttribute("aVo", service.selectOneAnnouncementByNum(annNo));

		model.addAttribute("page", cri.getPage());
		model.addAttribute("perPageNum", cri.getPerPageNum());
		model.addAttribute("searchType", cri.getSearchType());
		model.addAttribute("keyword", cri.getKeyword());
	}

	// 3. 관리자 :: 취업공고 등록 화면
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public void registerGET(HttpServletRequest request) throws Exception {
		logger.info("Admin Announcement register get..");

	}

	// 4. 관리자 :: 취업공고 등록
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerPOST(AnnouncementVO aVo, RedirectAttributes rttr) throws Exception {

		logger.info("Admin (Announcement Register POST .......");
		logger.info("vo : " + aVo);

		service.insertAnnouncement(aVo);
		logger.info("vo : " + aVo);
		rttr.addFlashAttribute("msg", "등록되었습니다.");

		return "redirect:/admin/announcement/list";
	}

	// 5. 관리자 :: 취업공고 수정
	@RequestMapping(value = "/modify", method = RequestMethod.GET)
	public void modifyGET(@RequestParam("annNo") int annNo, @ModelAttribute("cri") SearchCriteria cri, Model model)
			throws Exception {
		logger.info("Admin notice modifyGET....");
		model.addAttribute("aVo", service.selectOneAnnouncementByNum(annNo));
	}

	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	public String modifyPOST(AnnouncementVO vo, @ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		logger.info("Admin Announcement modifyPOST.....");
		logger.info(cri.toString());

		service.updateAnnouncement(vo);

		rttr.addFlashAttribute("msg", "수정되었습니다.");
		rttr.addFlashAttribute("vo", vo);

		logger.info(rttr.toString());

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		return "redirect:/admin/announcement/list";
	}

	// 6. 관리자 :: 취업공고 관리 - 삭제
	@RequestMapping(value = "/remove", method = RequestMethod.POST)
	public String adremove(@RequestParam("annNo") int annNo, SearchCriteria cri, RedirectAttributes rttr)
			throws Exception {

		service.deleteAnnouncement(annNo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "삭제되었습니다.");

		return "redirect:/admin/announcement/list";

	}
	
	
	// 관리자 :: 취업 공고 인재 추천 목록
	@RequestMapping(value = "/recommandlist", method = RequestMethod.GET)
	public void recommandlist(@RequestParam("annNo") int annNo, @ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("Admin Announcement recommandlist Form.....");
		logger.info(cri.toString());

		model.addAttribute("aVo", service.selectOneAnnouncementByNum(annNo));
		
		//TODO: 인재추천 모델 호출하여 인채추천 list 연동 시키기 


	}

}
