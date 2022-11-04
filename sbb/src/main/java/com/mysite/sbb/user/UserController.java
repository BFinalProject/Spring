package com.mysite.sbb.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger("com.mysite.sbb.user.UserController");

	@Autowired
	private final UserService userService;

	@Autowired
	private final UserRepository userRepository;

//	@Autowired
//	WebClient webClient;

	@GetMapping("/signup")
	public String signup(UserCreateForm userCreateForm) {
		return "signup_form";
	}

	@PostMapping("/signup")
	public String signup(Model model, @Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "signup_form";
		}

		if (!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
			return "signup_form";
		}

		try {

			userService.create(userCreateForm.getUsername(), userCreateForm.getEmail(), userCreateForm.getGender(),
					userCreateForm.getPassword1(), userCreateForm.getName(), userCreateForm.getPhon());
		} catch (DataIntegrityViolationException e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
			model.addAttribute("error_msg", "이미 등록된 사용자입니다.");
			return "signup_form";
		} catch (Exception e) {
			e.printStackTrace();
			bindingResult.reject("signupFailed", e.getMessage());
			return "signup_form";
		}
		logger.info("===========> post singnup");
		return "redirect:/";
	}

	@GetMapping("/login")
	public String login() {
		logger.info("===========>get login");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println("여기에요=================+++++++++++++++++" + principal);
		return "login_form";

	}

	@PostMapping("/login_success")
	public String loginSuccess(HttpServletRequest request, @Param("username") String username,
			@Param("password") String password) {

		AuthInfo authInfo = new AuthInfo(username, password);

		HttpSession session = request.getSession();
		session.setAttribute("user", authInfo);

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			System.out.println(((UserDetails) principal).getUsername());
		} else {
			System.out.println(principal.toString());
		}
		System.out.println("여기도 나오나요??????+++++++++++++++++++++++++++++++++++" + principal);
		System.out.println("여기도 나오나요???+++++++++++++++++++++++++++++++++++" + session.getAttribute("user"));
		return "redirect:/";
	}

//	@PostMapping("/login")
//	public String login(@Valid @ModelAttribute UserLoginForm userLoginForm, BindingResult bindingResult, HttpServletResponse response) {
//		logger.info("===========>post login");
//    	System.out.println("===========>post login");
//		if (bindingResult.hasErrors()) {
//	    	System.out.println("===========>post login");
//
//	        return "/login_form";
//	    }
//
//	    SiteUser loginMember = userService.login(userLoginForm.getUsername(), userLoginForm.getPassword());
//	    if (loginMember == null) {
//	        bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//	        return "/login_form";
//	    }
//
//	    //쿠키에 시간 정보를 주지 않으면 세션 쿠키가 된다. (브라우저 종료시 모두 종료)
//	    Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
//	    response.addCookie(idCookie);
//	    
//	    return "redirect:/";
//	}

	@GetMapping("/find_id")
	public String find_id(UserFindIdForm userFindIdForm) {
		return "find_id";
	}

	@PostMapping("/find_id")
	public ModelAndView find_id(@Valid UserFindIdForm userFindIdForm, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView();
		try {

			mv = userService.find_id(userFindIdForm.getEmail(), userFindIdForm.getName());
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return mv;
	}

	@GetMapping("/find_pw")
	public String find_pw(UserFindPwForm userFindPwForm) {
		return "find_pw";
	}

	@PostMapping("/find_pw")
	public String find_pw(@Valid UserFindPwForm userFindPwForm, BindingResult bindingResult) {

		try {
			userService.sendMail(userService.find_pw(userFindPwForm.getEmail(), userFindPwForm.getUsername()));
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}

		return "redirect:/user/login";
	}

	@RequestMapping("/registerEmail")
	public ModelAndView registerEmail(@RequestParam String email, @RequestParam String email_key) {
		ModelAndView mv = new ModelAndView();

		if ((userRepository.CountBySiteUser(email)).intValue() == 1) {
			List<SiteUser> tmp_list = userRepository.findAll();
			for (SiteUser user : tmp_list) {

				if ((user.getEmail()).equals(email) && (user.getEmail_key()).equals(email_key)) {
					String pwd = userService.resetPwd(user.getEmail(), user.getEmail_key());
					mv.addObject("pwd", pwd);
					mv.setViewName("showPassword");

					return mv;
				}
			}

		}
		return mv;
	}

	@GetMapping("/change_pwd")
	public ModelAndView change_pw(HttpServletRequest request, UserChangePwForm userChangePwForm) {
		logger.info("change_pw ++++++++++++++++++++++++++++++");
		ModelAndView mv = new ModelAndView();
		HttpSession session = request.getSession();
		AuthInfo user = (AuthInfo) session.getAttribute("user");
		mv.addObject("user", user);
		mv.setViewName("change_pwd");
		return mv;
	}

//	@PostMapping("/change_pwd")
//	public String passwordEdit(Model model, UserChangePwForm form, BindingResult result,
//			@AuthenticationPrincipal SiteUser currentMember) {
//		if (result.hasErrors()) {
//			return "redirect:/login";
//		}
//
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//
//		if (!encoder.matches(form.getPassword(), currentMember.getPassword())) {
//			model.addAttribute("error", "현재 패스워드 불일치");
//			return "/change_pwd";
//		}
//
//		if (form.getPassword().equals(form.getPassword1())) {
//			model.addAttribute("error", "동일한 패스워드");
//			return "/change_pwd";
//		}
//
//		if (!form.getPassword1().equals(form.getPassword2())) {
//			model.addAttribute("error", "새 패스워드 불일치");
//			return "/change_pwd";
//		}
//
//		String encodedNewPwd = encoder.encode(form.getPassword2());
//		userService.changepw(currentMember.getUsername(), encodedNewPwd);
//		currentMember.setPassword(encodedNewPwd);
//		return "redirect:/";
//	}

//	@PostMapping("/change_pw")
//	public String change_pw(@ModelAttribute("password") String password, @ModelAttribute("password1") String password1, 
//			@ModelAttribute("password2") String password2, @ModelAttribute("username") String username,
//							BindingResult bindingResult)throws Exception {
//		ModelAndView mv = new ModelAndView();
//		mv.addObject("password", password);
//		mv.set("password", password);
//		mv.addObject("password", password);
//		
//		
//
//		if (bindingResult.hasErrors()) {
//			return "change_pw";
//		}
//
//		if (!password1.equals(password2)) {
//			bindingResult.rejectValue("password2", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다.");
//			return "change_pw";
//		}
//
//		try {
//			
//			userService.changepw(password1, username);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			bindingResult.reject("signupFailed", e.getMessage());
//			return "change_pw";
//		}
//
//		return "redirect:/";
//	}

//	public String deleteUser(@RequestParam("Email") String Email, RedirectAttributes redirectAttr,
//			SessionStatus sessionStatus) {
//
//		int result = userService.deleteMember(Email);
//
//		if (result > 0) {
//			redirectAttr.addFlashAttribute("msg", "성공적으로 회원정보를 삭제했습니다.");
//			SecurityContextHolder.clearContext();
//		} else
//			redirectAttr.addFlashAttribute("msg", "회원정보삭제에 실패했습니다.");
//
//		return "redirect:/";
//	}
}
