package com.mysite.sbb.restaurant;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
//@RestController
public class RestaurantController {

	@Autowired
	RestaurantService restaurantService;

	@Autowired
	RestaurantRepository restaurantRepository;

//	@Autowired
//	Restaurant restaurant;

	Logger logger = LoggerFactory.getLogger("com.project.controller.RestaurantController");

	List<Restaurant> reclass = new ArrayList<Restaurant>();
	
	

	@GetMapping("map")	
	public ModelAndView restaurnatdata() {
		ModelAndView mv = new ModelAndView();
		logger.info("============> 여기냐?");
		List<Restaurant> restaurant = new ArrayList<>();
		restaurant = restaurantRepository.findAll();

		mv.addObject("name", restaurant);
		mv.setViewName("map");
		
		return mv;
	}

	

}
