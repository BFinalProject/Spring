package com.mysite.sbb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mysite.sbb.question.QuestionService;

//@SpringBootTest 애너테이션은 SbbApplicationTests 클래스가 스프링부트 테스트 클래스임을 의미한다. 
//그리고 @Autowired 애너테이션은 스프링의 DI 기능으로 questionRepository 객체를 스프링이 자동으로 생성해 준다.
// *DI(Dependency Injection) - 스프링이 객체를 대신 생성하여 주입한다.

//@Autowired
//객체를 주입하기 위해 사용하는 스프링의 애너테이션이다. 
//객체를 주입하는 방식에는 @Autowired 외에 Setter 또는 생성자를 사용하는 방식이 있다. 
//순환참조 문제와 같은 이유로 @Autowired 보다는 생성자를 통한 객체 주입방식이 권장된다. 
//하지만 테스트 코드의 경우에는 생성자를 통한 객체의 주입이 불가능하므로 테스트 코드 작성시에만 
//@Autowired를 사용하고 실제 코드 작성시에는 생성자를 통한 객체 주입방식을 사용하겠다.

//testJpa 메서드 위의 @Test 애너테이션은 testJpa 메서드가 테스트 메서드임을 나타낸다. 
//위 클래스를 JUnit으로 실행하면 @Test 애너테이션이 붙은 메서드가 실행된다.
//JUnit은 테스트코드를 작성하고 작성한 테스트코드를 실행하기 위해 사용하는 자바의 테스트 프레임워크이다.
//testJpa 메서드의 내용을 잠시 살펴보자. testJpa 메서드는 q1, q2 라는 Question 엔티티 객체를 생성하고 
//QuestionRepository를 이용하여 그 값을 데이터베이스에 저장하는 코드이다.


//"인터페이스에 findBySubject 라는 메서드를 선언만 하고 구현은 하지 않았는데 도대체 어떻게 실행이 되는 거지?"
//이러한 마법은 JpaRepository를 상속한 QuestionRepository 객체가 생성될때 벌어진다. 
//(DI에 의해 스프링이 자동으로 QuestionRepository 객체를 생성한다. 이 때 프록시 패턴이 사용된다고 한다.) 
//리포지터리 객체의 메서드가 실행될때 JPA가 해당 메서드명을 분석하여 쿼리를 만들고 실행한다.
//즉, 여러분은 findBy + 엔티티의 속성명
//(예:findBySubject)과 같은 리포지터리 메서드를 작성하면 해당 속성의 값으로 데이터를조회할수 있다.

//항목	예제	 설명 ORM
//And	/ findBySubjectAndContent(String subject, String content) /	여러 컬럼을 and 로 검색
//Or / findBySubjectOrContent(String subject, String content) / 여러 컬럼을 or 로 검색
//Between / findByCreateDateBetween(LocalDateTime fromDate, LocalDateTime toDate) / 컬럼을 between으로 검색
//LessThan / findByIdLessThan(Integer id) / 작은 항목 검색
//GreaterThanEqual / findByIdGraterThanEqual(Integer id) / 크거나 같은 항목 검색
//Like / findBySubjectLike(String subject) / like 검색
//In / findBySubjectIn(String[] subjects) / 여러 값중에 하나인 항목 검색
//OrderBy / findBySubjectOrderByCreateDateAsc(String subject) / 검색 결과를 정렬하여 전달



@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionService  questionService;
	

	
	@Test
	void testJpa() {
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content, null);
		}
	}
}