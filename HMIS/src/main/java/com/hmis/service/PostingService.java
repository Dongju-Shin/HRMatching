package com.hmis.service;

import java.util.List;

import com.hmis.domain.PostingVO;
import com.hmis.domain.SearchCriteria;
import com.hmis.dto.RecommandDTO;

/**
 * @author beomsoo
 */
public interface PostingService {
	
	// 1) 관리자 : 채용공고 등록
	public void registerPosting(PostingVO postingVO) throws Exception;
	
	// 2) 관리자 : 채용공고 상세보기
	public PostingVO readPosting(int postingId) throws Exception;
	
	// 3) 관리자 : 채용공고 수정
	public void modifyPosting(PostingVO postingVO) throws Exception;

	// 4) 관리자 : 채용공고 삭제
	public void removePosting(int postingId) throws Exception;
	
	// 5) 관리자 : 검색 가능한 채용공고 목록 페이징 처리
	public List<PostingVO> listSearchCriteria(SearchCriteria cri) throws Exception;

	// 6) 관리자 : 검색 가능한 채용공고 목록 페이징 처리
	public int listCountSearchCriteria(SearchCriteria cri) throws Exception;
	
	// 7) 관리자 : 인재 추천
	public List<RecommandDTO> recommandStudent(RecommandDTO recommandDTO) throws Exception;
	
	// 8) 학생 : 채용공고 추천
	public List<RecommandDTO> recommandPosting(RecommandDTO recommandDTO) throws Exception;
	
	// 인재 추천 결과 저장
	public void registerRecommand(RecommandDTO recommandDTO) throws Exception;

}
