package com.hmis.controller;

import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hmis.domain.GoalVO;
import com.hmis.domain.PersonalInformationVO;
import com.hmis.domain.UscarVO;
import com.hmis.domain.UserMajorVO;
import com.hmis.domain.UserVO;
import com.hmis.service.ApplyService;
import com.hmis.service.UscarService;
import com.hmis.service.UserMajorService;
import com.hmis.service.UserProgramService;
import com.hmis.service.UserService;

/**
 * @author beomsoo
 */
@Controller
@RequestMapping("/user/mypage/*")
public class MyPageController {

   @Inject
   private UserService service;

   @Inject
   private ApplyService ApplyService;

   @Inject
   private UserProgramService UPservice;
   
   @Inject
   private UscarService USservice;
  
   @Inject
   private UserMajorService UMservice;
   
   private static Logger logger = LoggerFactory.getLogger(MyPageController.class);

   // 1) 내 정보 상세보기
   @RequestMapping(value = "/myInfo", method = RequestMethod.GET)
   public void myInfoGET(HttpServletRequest request, Model model) throws Exception {

      logger.info("user myPageGET..............");

      HttpSession session = request.getSession();

      UserVO uVo = (UserVO) session.getAttribute("login");

      int userNo = uVo.getUserNo();
     

      model.addAttribute(service.myInfo(userNo));
      
      /** K-Digital 당시 추가 **/
      // 1-1) myInfo.jsp 로 이동하면서 개인정보 동의여부 전달
      model.addAttribute("piVO", service.personalInformationRead(userNo));

   }

	/**
	* 맵핑 주소 : /myInfoUpdate
	* 리턴 타입 : void
	* 메소드명  : myInfoUpdateGET
	* 매개 변수 : @RequestParam("userNo") int userNo, Model model
	*/
   // 2) 내 정보 수정 GET
   @RequestMapping(value = "/myInfoUpdate", method = RequestMethod.GET)
   public void myInfoUpdateGET(@RequestParam("userNo") int userNo, Model model) throws Exception {
	   
	   logger.info("User myInfo + personalInformation Update POST");
	   
	   // 2-1) myInfoUpdate.jsp 로 이동하면서 학생 상세 정보 전달
	   model.addAttribute(service.myInfo(userNo));
	   
	   // 2-2) myInfoUpdate.jsp 로 이동하면서 개인정보 동의여부 전달
	   model.addAttribute("piVO", service.personalInformationRead(userNo));
	   
   }

	/**
	* 맵핑 주소 : /myInfoUpdate
	* 리턴 타입 : String
	* 메소드명  : myInfoUpdatePOST
	* 매개 변수 : UserVO uVo, PersonalInformationVO piVO, RedirectAttributes rttr
	*/
   // 3) 내 정보 수정 POST
   @RequestMapping(value = "/myInfoUpdate", method = RequestMethod.POST)
   public String myInfoUpdatePOST(UserVO uVo, PersonalInformationVO piVO, RedirectAttributes rttr) throws Exception {

	  logger.info("User myInfo + personalInformation Update POST");
	   
	  // 3-1) 수정된 내 정보를 DB에 저장
      service.myInfoModify(uVo);
      
      /** K-Digital 당시 추가 **/
	  // 3-1) 수정된 개인정보 동의 여부를 DB에 저장
      service.personalInformationModify(piVO);
      
      // 3-3) redirect로 강제 이동할 페이제에 rttr로 "user" 에 "SUCCESS" 담아서 전달
      rttr.addFlashAttribute("user", "SUCCESS");
      
      // 3-4) / 페이지로 강제 이동
      return "redirect:/";
   }

   // 4. 내 점수
   @RequestMapping(value = "/myScore", method = RequestMethod.GET)
   public void myScore(HttpServletRequest request, Model model) throws Exception {
      HttpSession session = request.getSession();
      UserVO uVo = (UserVO) session.getAttribute("login");
      int userNo = uVo.getUserNo();
      

      model.addAttribute(ApplyService.esSelect(userNo));
      model.addAttribute("acceptList", ApplyService.acceptList(userNo));

      model.addAttribute(UPservice.acceptProList(userNo));
      model.addAttribute("acceptProList", UPservice.acceptProList(userNo));
      
      model.addAttribute(USservice.everyUscarList(userNo));
      model.addAttribute("everyUscarList", USservice.everyUscarList(userNo));
      
      model.addAttribute(UMservice.listAll(userNo));
      model.addAttribute("listAll", UMservice.listAll(userNo));
   }

   // 5. 내 목표 - 리스트
   @RequestMapping(value = "/myGoal", method = RequestMethod.GET)
   public void myGoal(Model model, HttpServletRequest request) throws Exception {
      logger.info("myGoal page....");

      HttpSession session = request.getSession();
      UserVO uVo = (UserVO) session.getAttribute("login");
      int userNo = uVo.getUserNo();

      model.addAttribute("goalList", service.goalList(userNo));

   }

   // 6. 내 목표 - 등록 페이지로 이동
   @RequestMapping(value = "/myGoalRegister", method = RequestMethod.GET)
   public void myGoalRegisterGET() throws Exception {
      logger.info("myGoal RegisterGET.....");
   }
   
   // 7. 내 목표 - 등록 페이지 등록
   @RequestMapping(value = "/myGoalRegister", method = RequestMethod.POST)
   public String myGoalRegisterPOST(GoalVO gVo, RedirectAttributes rttr) throws Exception {
      logger.info("myGoal RegisterPOST.....");

      service.goalInsert(gVo);

      rttr.addFlashAttribute("msg", "처리가 완료되었습니다.");

      return "redirect:/user/mypage/myGoal";
   }
   // 8. 내목표 - 상세보기
   @RequestMapping(value = "/myGoalRead", method = RequestMethod.GET)
   public void myGoalRead(@RequestParam("goalNo") int goalNo, Model model) throws Exception {

      logger.info("myGoalRead.....");
      model.addAttribute(service.goalRead(goalNo));

   }

   // 9. 진로목표  - 삭제하기   
   @RequestMapping(value = "/uscarremove", method = RequestMethod.GET)
   public String uscarremove(@RequestParam("uscarNo") int uscarNo, UscarVO uscar, RedirectAttributes rttr) throws Exception {

      logger.info("uscarremove post.................");
      logger.info(uscar.toString());
      USservice.remove(uscarNo);

      rttr.addFlashAttribute("msg", "REMOVE");
      return "redirect:/user/mypage/myScore";
   }
   // 10. 수강한 전공목록 - 수정하기 페이지로 이동
   @RequestMapping(value = "/modify", method = RequestMethod.GET)
   public void read(HttpServletRequest request, Model model) throws Exception {

        HttpSession session = request.getSession();
         UserVO uVo = (UserVO) session.getAttribute("login");
         int userNo = uVo.getUserNo();
         
      logger.info("read..... userNo : " + userNo);
      
      
      //리스트 & 수정 페이지
      model.addAttribute("listAll", UMservice.listAll(userNo));
      
      

      model.addAttribute("userNo", userNo);
      model.addAttribute("otherList", UMservice.otherList(userNo));// 제외된 학생 LIST

   }
   
   // 11. 수강한 전공목록 - 수정하기
   @RequestMapping(value = "/modify", method = RequestMethod.POST)
   public String updatePOST(@RequestParam("userNo") int userNo, RedirectAttributes rttr,
         @RequestParam(value="arrayMajor", required = true) List<Integer> majorNos) throws Exception {
      
      // 기존 리스트 삭제 - 먼저 실행
      //attService.removeAtt(mis.getMisNo());
      UMservice.umremove(userNo);
      
      
      // 새로운 리스트 등록 - 리스트 삭제 실행 후 실행
      for (int i = 0; i < majorNos.size(); i++) {

         UserMajorVO umVo = new UserMajorVO();
         umVo.setUserNo(userNo);
         umVo.setMajorNo(majorNos.get(i));
         
         logger.info("usermajor majorNos: " + umVo.getMajorNo());
         UMservice.register(umVo);
      }

      
      rttr.addFlashAttribute("msg", "MODIFY");

      return "redirect:/user/mypage/myScore";

   }

   
   
}