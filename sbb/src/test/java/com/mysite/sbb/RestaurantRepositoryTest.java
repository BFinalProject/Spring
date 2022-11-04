package com.mysite.sbb;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.restaurant.Restaurant;
import com.mysite.sbb.restaurant.RestaurantRepository;
import com.mysite.sbb.restaurant.RestaurantService;

@SpringBootTest
public class RestaurantRepositoryTest {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	RestaurantService restaurantService;
	
	@Test
	public void testRestaurantQurey() {
		
		for (int i = 1; i<=10; i++) {
			String name = restaurantRepository.findByRestaurantname("여기");
			Double lox_x = restaurantRepository.findByRestaurantloxx(127.2157354);
			Double lox_y = restaurantRepository.findByRestaurantloxy(37.2458642);
			this.restaurantService.RestaurantDB(name, lox_x, lox_y);
		}
	}
}
