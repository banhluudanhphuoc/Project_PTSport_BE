package online.ptsports.PTSports;

import lombok.extern.slf4j.Slf4j;
import online.ptsports.PTSports.Config.AppConstants;
import online.ptsports.PTSports.Entity.Role;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Repository.RoleRepo;
import online.ptsports.PTSports.Repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Arrays;
import java.util.Date;
import java.util.List;


@SpringBootApplication
@EnableJpaAuditing //Phải thêm vào mới gen ngày
@Slf4j
public class PtSportsApplication implements CommandLineRunner {

	private final UserRepo userRepo;
	private final RoleRepo roleRepo;

	public PtSportsApplication(UserRepo userRepo,
							   RoleRepo roleRepo) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
	}

	public static void main(String[] args) {
		SpringApplication.run(PtSportsApplication.class, args);
	}
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	@Override
	public void run(String... args) throws Exception {
		log.info("BEGIN INSERT ROLE DUMP");
		try {
			// Kiểm tra xem đã có tài khoản Admin chưa
			if (userRepo.existsByEmail("phuocbld2@gmail.com")) {
				log.info("Admin account already exists. Skipping data initialization.");
				return;
			}

			Role adminRole = new Role();
			adminRole.setRoleId(AppConstants.ADMIN_ID);
			adminRole.setRoleName("ROLE_ADMIN");

			Role userRole = new Role();
			userRole.setRoleId(AppConstants.USER_ID);
			userRole.setRoleName("ROLE_USER");

			List<Role> roles = List.of(adminRole, userRole);
			List<Role> savedRoles = roleRepo.saveAll(roles);

			savedRoles.forEach(System.out::println);

			User user = new User();
			user.setPassword(new BCryptPasswordEncoder().encode("123456"));
			user.setName("SYS ADMIN");
			user.setEmail("phuocbld2@gmail.com");
			user.setIsActive(true);
			user.setAvatar("default.png");
			user.setBirthdate(new Date());
			user.setRoles(Arrays.asList(adminRole));

			userRepo.save(user);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
