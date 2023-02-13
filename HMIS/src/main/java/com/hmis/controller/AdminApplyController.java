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

import com.hmis.domain.ApplyVO;
import com.hmis.domain.LicenseVO;
import com.hmis.domain.OriginalLogVO;
import com.hmis.domain.PageMaker;
import com.hmis.domain.SearchCriteria;
import com.hmis.service.ApplyService;
import com.hmis.service.LicenseService;

@Controller
@RequestMapping("admin/apply/*")
public class AdminApplyController {

	@Inject
	private ApplyService service;

	@Inject
	private LicenseService licenseService;

	private static Logger logger = LoggerFactory.getLogger(AdminApplyController.class);

	// 관리자 :: 졸업인증신청 관리 - 신청 목록
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public void list(@ModelAttribute("cri") SearchCriteria cri, Model model) throws Exception {
		logger.info("Admin Apply List Form.....");
		logger.info(cri.toString());
 
		model.addAttribute("allList", service.adApplyList(cri));
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.adApplyListSearchCount(cri));

		model.addAttribute("pageMaker", pageMaker);

	}

	// 관리자 :: 졸업인증신청 관리 - 신청 상세조회
	@RequestMapping(value = "/read", method = RequestMethod.GET)
	public void read(HttpServletRequest request, @RequestParam("applyNo") int applyNo,
			@ModelAttribute("cri") SearchCriteria cri, Model model, RedirectAttributes rttr) throws Exception {

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		model.addAttribute("applyNo", applyNo);
		logger.info("Admin readPage.........");
		model.addAttribute(service.adApplyRead(applyNo));
		logger.info("readPage.fileVO: " + service.fileList(applyNo).size());
		model.addAttribute("fileVO", service.fileList(applyNo));
		logger.info("originalLogVO............. ");
		model.addAttribute("originalLogVO", service.oriLogList(applyNo));
	}

	// 관리자 :: 졸업인증신청 관리 - 신청 - 원본확인
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public String check(OriginalLogVO oLVo, RedirectAttributes rttr, @RequestParam("mapping") String mapping,
			@ModelAttribute("cri") SearchCriteria cri, Model model, @RequestParam("applyNo") int applyNo)
			throws Exception {

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("applyNo", applyNo);

		service.oriLogRegister(oLVo);

		rttr.addFlashAttribute("msg", "처리 완료되었습니다.");
		rttr.addFlashAttribute("originalLogVO", oLVo);

		String url = "";
		if (mapping.equals("read")) {
			url = mapping;
		}
		// http://hmis.kr/admin/apply/read?page=1&perPageNum=10&searchType&keyword&applyNo=264
		if (mapping.equals("waitRead")) {
			url = mapping;
		}

		return "redirect:/admin/apply/" + url;
		// + url +
		// "?page=${cri.page}&perPageNum=${cri.perPageNum}&searchType=${cri.searchType}&keyword=${cri.keyword}&applyNo=${applyVO.applyNo}"
	}

	// 관리자 :: 졸업인증신청 관리 - 신청 승인
	@RequestMapping(value = "/accept", method = RequestMethod.POST)
	public String accept(@RequestParam("applyNo") int applyNo, @RequestParam("reason") String reason,
			@RequestParam("approver") String approver, @RequestParam("mapping") String mapping, ApplyVO aVo,
			@ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr) throws Exception {
		logger.info("Accept Apply");

		// 승인 시, 기존 항목명이 수정 됐을 경우에 항목 update 기능 추가
		// 1.기존 항목명 Data와 정보가 같은지 확인 applyNo의 subNo(자격증번호) 비교
		int bSubNo = service.searchSubNo(applyNo);

		// 2.기존 자격증 항목과 동일하지 않으면, TBL_APPLY 자격증 정보 update 수행
		if ((aVo.getSubNo() > 0) && bSubNo != aVo.getSubNo()) {

			service.updateSubNo(aVo);
		}

		service.accept(applyNo, reason, approver);

		// 승인 완료 시 e-포트폴리오 자격증 테이블에 자격 정보 저장
		LicenseVO lVo = new LicenseVO();
		lVo.setApplyNo(aVo.getApplyNo());
		lVo.setUserNo(aVo.getUserNo());
		lVo.setSubNo(aVo.getSubNo());
		lVo.setSerialNum(aVo.getSerialNum());
		lVo.setAcDate(aVo.getAcDate());
		lVo.setScore(aVo.getScore());

		licenseService.insertLicense(lVo);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "처리 완료되었습니다.");

		String url = "";
		if (mapping.equals("list")) {
			url = mapping;
		}
		if (mapping.equals("waitList")) {
			url = mapping;
		}

		return "redirect:/admin/apply/" + url;
	}

	// 관리자 :: 졸업인증신청 관리 - 신청 거절
	@RequestMapping(value = "/deny", method = RequestMethod.POST)
	public String deny(@RequestParam("applyNo") int applyNo, @RequestParam("reason") String reason,
			@RequestParam("approver") String approver, @RequestParam("mapping") String mapping,
			@ModelAttribute("cri") SearchCriteria cri, RedirectAttributes rttr) throws Exception {
		logger.info("Deny Apply");
		service.deny(applyNo, reason, approver);

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		rttr.addFlashAttribute("msg", "처리 완료되었습니다.");

		String url = "";
		if (mapping.equals("list")) {
			url = mapping;
		}
		if (mapping.equals("waitList")) {
			url = mapping;
		}

		return "redirect:/admin/apply/" + url;
	}

	// 관리자 :: 졸업인증신청 관리 - 승인대기 신청내역 목록
	@RequestMapping(value = "/waitList", method = RequestMethod.GET)
	public void waitList(Model model, @ModelAttribute("cri") SearchCriteria cri) throws Exception {
		logger.info("Admin Apply WaitList");
		model.addAttribute("waitList", service.waitList());

		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.adApplyListSearchCount(cri));

		model.addAttribute("pageMaker", pageMaker);
	}

	// 관리자 :: 졸업인증신청 관리 - 승인대기 신청 상세 조회
	@RequestMapping(value = "/waitRead", method = RequestMethod.GET)
	public void waitRead(HttpServletRequest request, @RequestParam("applyNo") int applyNo,
			@ModelAttribute("cri") SearchCriteria cri, Model model, RedirectAttributes rttr) throws Exception {

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());

		logger.info("Admin Apply Wait Read Page..");
		model.addAttribute(service.adApplyRead(applyNo));
		logger.info("waitReadPage.fileVO: " + service.fileList(applyNo).size());
		model.addAttribute("fileVO", service.fileList(applyNo));
		logger.info("originalLogVO............. ");
		model.addAttribute("originalLogVO", service.oriLogList(applyNo));
	}

	// 관리자 :: 졸업인증신청 관리 - 거절 OR 승인 후 -> 승인 대기
	@RequestMapping(value = "/standBy", method = RequestMethod.POST)
	public String standBy(@RequestParam("applyNo") int applyNo, @RequestParam("approver") String approver, ApplyVO aVo,
			RedirectAttributes rttr, @RequestParam("mapping") String mapping, @ModelAttribute("cri") SearchCriteria cri,
			OriginalLogVO oLVo, Model model) throws Exception {

		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("applyNo", applyNo);

		// 승인 OR 거절 후 다시 승인 대기로 되돌리기
		service.standBy(applyNo, approver);

		// 승인 OR 거절 후 다시 승인 대기로 되돌리기 시 e-포트폴리오 자격증 테이블에 자격 정보 삭제
		LicenseVO lVo = new LicenseVO();
		lVo.setApplyNo(aVo.getApplyNo());
		lVo.setUserNo(aVo.getUserNo());
		licenseService.deleteLicense(lVo);

		// 로그 되돌리기
		service.oriLogRegister(oLVo);

		rttr.addFlashAttribute("msg", "처리 완료되었습니다.");
		rttr.addFlashAttribute("originalLogVO", oLVo);

		String url = "";
		if (mapping.equals("read")) {
			url = mapping;
		}
		if (mapping.equals("waitRead")) {
			url = mapping;
		}

		return "redirect:/admin/apply/" + url;
	}

}
