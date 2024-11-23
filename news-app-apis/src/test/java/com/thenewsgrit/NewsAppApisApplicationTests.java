package com.thenewsgrit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.thenewsgrit.repositories.UserRepo;

@SpringBootTest
class NewsAppApisApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	@Test
	void contextLoads() {
	}

	public void repoTest() {
		String className = this.userRepo.getClass().getName();
	    String packName =this.userRepo.getClass().getPackageName();
	    System.out.println(className);
	    System.out.println(packName);

	}
	
}
