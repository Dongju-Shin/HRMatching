package com.hmis.controller;

import java.util.ArrayList;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hmis.dto.RecommandDTO;
import com.hmis.service.PostingService;

@Controller
@RequestMapping("/hrMatching")
public class RecommandController {

	private static final Logger logger = LoggerFactory.getLogger(RecommandController.class);

	@Inject
	PostingService service;

	// JSON 파싱할 수 있는 기본 형태 제공
	// "와 {} 제거
	public String remakeJSON(String result) {

		String remake = "";
		for (int i = 0; i < result.length(); i++) {
			if (result.charAt(i) == '"' || result.charAt(i) == '{' || result.charAt(i) == '}') {
				continue;
			}
			remake = remake + result.charAt(i);
		}
		System.out.println("json remaked = " + remake);

		return remake;
	}

	// get방식으로 URL 호출
	@RequestMapping(value = "", method = RequestMethod.GET)
	public String listPagePOST(RecommandDTO recommand, RedirectAttributes rttr) throws Exception {
		logger.info("infoInput get .........");
		logger.info(recommand.toString());

		int num = 5;
		int postingId = recommand.getPostingId();
			
			String Url = "http://127.0.0.1:5000/topNHrs?num=" + num + "&jobID=" + postingId;
			
		// Url = "http://127.0.0.1:5000/topNHrs";
		// Url = "http://127.0.0.1:5000/topNHrs?num=5&jobID=500";

		// REST API호출 후 응답을 받을 때까지 기다리는 동기 방식
		RestTemplate restTemplate = new RestTemplate();
		// RestTemplate의 메서드 중 getForObject를 이용해
		// 주어진 URL주소로 HTTP GET메서드로 객체로 결과 반환

		// 1) 추천 모델에서 결과를 JSON으로 결과 반환
		String result = restTemplate.getForObject(Url, String.class);

		// 2) JSON 파싱
		String remaked = remakeJSON(result);
		String[] remakedArr = remaked.split(",");

		// 3) JSON 결과의 code 값 비교해서 정상/오류 분기
		String res = remakedArr[0].split(":")[1].trim();

		// 3-1 결과 정상이면 100
		if (res.equals("100")) {

			ArrayList<RecommandDTO> recommList = new ArrayList<RecommandDTO>();

			for (String arr : remakedArr) {

				if (arr.contains("statusCode") || arr.contains("msgStr")) {
					continue;
				} else {

					String[] recArr = arr.split(":");
					RecommandDTO dto = new RecommandDTO();
					dto.setPostingId(postingId);
					dto.setSledding(0); // 추천 0으로 설정

					if (arr.contains("recHrSet")) {
						/*
						 * recHrSet: 20162398: 0.9985185282933657
						 */
						dto.setUserNo(Integer.parseInt(recArr[1].trim()));
						dto.setRecommandScore(Float.parseFloat(recArr[2].trim()));

					} else {

						dto.setUserNo(Integer.parseInt(recArr[0].trim()));
						dto.setRecommandScore(Float.parseFloat(recArr[1].trim()));

					}

					recommList.add(dto);

				}

			}

			// 데이터베이스에 저장
			for (RecommandDTO rec : recommList) {
				service.registerRecommand(rec);
			}

			// Read 페이지 이동
			rttr.addAttribute("postingId", postingId);

			return "redirect:/admin/posting/read";

		} else {

			if (res.equals("101")) { // 3-2 결과 오류이면 101

				System.out.println("입력값 오류");
				rttr.addFlashAttribute("msg", "채용 정보를 확인 해주세요.");

			} else if (res.equals("102")) { // 3-2 결과 오류이면 102

				System.out.println("인재추천 불가");
				rttr.addFlashAttribute("msg", "매칭 되는 추천 결과가 없습니다.");

			}

			// 페이지 이동
			return "redirect:/admin/posting/list";

		}

	}
	
}
