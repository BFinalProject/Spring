package com.mysite.sbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {
	@RequestMapping("/sbb")
	@ResponseBody
	public String index() {
		return "안녕하세요 sbb에 오신것을 환영합니다.";
	}
	
	@RequestMapping("/map")
	public String map() {
		return "/map";
	}
	
	@RequestMapping("/")
    public String root() {
        return "redirect:/question/list";
        
//root 메서드를 추가하고 / URL을 매핑했다. 리턴 문자열 
//redirect:/question/list는 /question/list URL로 페이지를 리다이렉트 하라는 명령어이다. 
//스프링부트는 리다이렉트 또는 포워딩을 다음과 같이 할 수 있다.
//redirect:<URL> - URL로 리다이렉트 (리다이렉트는 완전히 새로운 URL로 요청이 된다.)
//forward:<URL> - URL로 포워드 (포워드는 기존 요청 값들이 유지된 상태로 URL이 전환된다.)
//이제 http://localhost:8080 페이지 접속을 하면 root 메서드가 실행되어 
//질문 목록이 표시되는 것을 확인할 수 있을 것이다.
        
    }

}
