package com.mysite.sbb.user;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;



@Transactional
public interface UserRepository extends JpaRepository<SiteUser, Long> {
	Optional<SiteUser> findByusername(String username);

	Optional<SiteUser> findBypassword(String password);

	Optional<SiteUser> findByemail(String email);

	List<SiteUser> findAll();


	@Query(value = "SELECT count(*) FROM SITE_USER where username = :username", nativeQuery = true)
	Integer CountBySiteuserUserName(@Param("username") String username);

	@Query(value = "SELECT * FROM SITE_USER", nativeQuery = true)
	List<SiteUser> findByUserFindIdFormEmailAndUserFindIdFormName();

	@Query(value = "SELECT * FROM SITE_USER", nativeQuery = true)
	List<SiteUser> findByUserFindPwFormEmailAndUserFindPwFormUsername();

	@Query(value = "SELECT * FROM SITE_USER", nativeQuery = true)
	List<SiteUser> findByUserLoginFormUsernameAndUserLoginFormPassword();

//	 메일 인증시 mail_auth 값을 1로 바꿔 로그인 허용

	@Modifying
	@Query(value = "update SITE_USER set email_auth=1 where email = :email and email_key = :email_key", nativeQuery = true)
	void UpdateEmail_Auth(@Param("email") String email, @Param("email_key") String email_key);

	@Modifying
	@Query(value = "update SITE_USER set PASSWORD = :password where email = :email and email_key = :email_key", nativeQuery = true)
	void UpdatePassword(@Param("password") String password, @Param("email") String email,
			@Param("email_key") String email_key);

//	 메일 인증 없을시 0 반환
	@Query(value = "SELECT COUNT(*) from SITE_USER WHERE email = :email and email_auth=1", nativeQuery = true)
	Long CountBySiteUser(@Param("email") String email);

	@Modifying
	@Query(value = "update SITE_USER set PASSWORD = :password", nativeQuery = true)
	List<UserChangePwForm> findBypasswordUpdateNewPassword(@Param("password") String password);

	void save(UserChangePwForm user);

	

}
