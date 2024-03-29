package com.hmis.service;

import java.util.List;

import com.hmis.domain.ApplyVO;
import com.hmis.domain.Criteria;
import com.hmis.domain.GoalVO;
import com.hmis.domain.MisVO;
import com.hmis.domain.PersonalInformationVO;
import com.hmis.domain.SearchCriteria;
import com.hmis.domain.UserVO;
import com.hmis.dto.LoginDTO;
import com.hmis.dto.TotalDTO;

/**
 * @author beomsoo
 */
public interface UserService {
	
   public UserVO login(LoginDTO dto) throws Exception;
   
   /** K-Digital 당시 추가 **/
   // 학생 : 개인정보 동의여부 상세보기 
   public PersonalInformationVO personalInformationRead(int userNo) throws Exception;
   
   /** K-Digital 당시 추가 **/
   // 학생 : 개인정보 동의여부 수정 
   public void personalInformationModify(PersonalInformationVO piVO) throws Exception;

   // 1. 관리자 :: 기초정보관리 - 학생 등록
   public void register(UserVO uVo) throws Exception;

   // 2. 관리자 :: 학생 목록
   public List<UserVO> list(SearchCriteria cri) throws Exception;

   // 3. 관리자 :: 기초정보관리 - 학생상세내역
   public UserVO read(int userNo) throws Exception;

   // 4. 관리자 :: 기초정보관리 - 학생 수정
   public void modify(UserVO uVo) throws Exception;

   // 5. 관리자 :: 기초정보관리 - 학생 삭제
   public void remove(int userNo) throws Exception;

   // 6. MY PAGE - 내 점수(MIS 총점)
   public TotalDTO misTotal(int userNo) throws Exception;

   // 7. MY PAGE - 내 점수(인증항목 총점)
   public TotalDTO subTotal(int userNo) throws Exception;

   // 8. MY PAGE - 내 정보
   public UserVO myInfo(int userNo) throws Exception;

   // 9 . MY PAGE - 내 정보 수정
   public void myInfoModify(UserVO uVo) throws Exception;

   // MY PAGE - 비밀번호 체크
   // public boolean checkPw(int i, String userPw) throws Exception;

   // 10. 페이징처리
   public List<UserVO> listCriteria(Criteria cri) throws Exception;

   public int countPaging(Criteria cri) throws Exception;

   // 11. 검색처리
   public List<UserVO> listSearch(SearchCriteria cri) throws Exception;

   public int listSearchCount(SearchCriteria cri) throws Exception;

   // 12. 관리자 :: 대시보드 - 신청 건수
   public int applyCount() throws Exception;

   // 13. 관리자 :: 대시보드 - 졸업예정자 수
   public int graduateCount() throws Exception;
   
   
   
   public  List<UserVO>  graduateList() throws Exception;

   // 14. 관리자 :: 대시보드 - 졸업대상자 수
   public int graduateToBeCount() throws Exception;
   

   

   // 15. 관리자 :: 대시보드 - 최근 MIS 3개 정보
   public List<MisVO> misInfo() throws Exception;

   // 16. 학생 :: 대시보드 - 최근 승인 대기 신청내역 3개 리스트
   public List<ApplyVO> mainApplyWaitList(int userNo) throws Exception;

   // 17. 학생 :: MY PAGE - 내 목표 설정(등록)
   public void goalInsert(GoalVO gVo) throws Exception;

   // 18. 학생 :: MY PAGE - 내 목표 리스트
   public List<GoalVO> goalList(int userNo) throws Exception;

   // 19. 학생 :: MY PAGE - 내 목표 상세보기
   public GoalVO goalRead(int goalNo) throws Exception;
   
   // 20. 관리자 :: 학생 등록 - 학번 중복체크 AJAX.
   public int checkUserNo(int userNo) throws Exception;

}