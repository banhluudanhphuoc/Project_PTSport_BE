package online.ptsports.PTSports.Service.IMPL;



import online.ptsports.PTSports.Config.AppConstants;
import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.Response.ApiResponse;
import online.ptsports.PTSports.DTO.UserDto;
import online.ptsports.PTSports.Entity.Role;
import online.ptsports.PTSports.Entity.User;
import online.ptsports.PTSports.Exeption.ConflictException;
import online.ptsports.PTSports.Exeption.ResoureNotFoundException;
import online.ptsports.PTSports.Repository.RoleRepo;
import online.ptsports.PTSports.Repository.UserRepo;
import online.ptsports.PTSports.Service.EmailService;
import online.ptsports.PTSports.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private EmailService emailService;


    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        User user = new ModelMapper().map(userDto, User.class);
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new ConflictException("Email is already registered");
        }

        user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        this.userRepo.save(user);

        // tra ve idsau khi tao
        userDto.setUserId(user.getUserId());


    }

    @Override
    @Transactional
    public UserDto updateUser(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setBirthdate(userDto.getBirthdate());

        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.convertToUserDto(updatedUser);
        return userDto1;
    }

    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        this.userRepo.delete(user);
    }

    @Override
    public UserDto findByEmail(String email) { // java8, optinal
        User user = userRepo.findByEmail(email);
        if (user == null)
            throw new NoResultException();
        return new ModelMapper().map(user, UserDto.class);
    }

    @Override
    @Transactional
    public UserDto updateAvatar(UserDto userDto, Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        user.setAvatar(userDto.getAvatar());
        User updatedUser = this.userRepo.save(user);
        UserDto userDto1 = this.convertToUserDto(updatedUser);
        return userDto1;
    }

    @Override
    @Transactional
    public UserDto registerUser(UserDto userDto) {

        if (userRepo.existsByEmail(userDto.getEmail())) {
            throw new ConflictException("Email is already registered");

        }



        User user = modelMapper.map(userDto, User.class);

        Role role = roleRepo.findById(AppConstants.USER_ID).get();
        user.getRoles().add(role);
        user.setAvatar("default.png");
//        user.setPassword();
        user.setBirthdate(userDto.getBirthdate());


        User registeredUser = userRepo.save(user);


        // Tạo mã xác thực email
        String emailVerificationToken = UUID.randomUUID().toString();

        // Gửi email xác thực
        String verificationLink = "http://localhost:3000/verify-email?token=" + emailVerificationToken;
        emailService.sendEmail(user.getEmail(), "Xác thực email", "Nhấn vào liên kết sau để xác thực email: " + verificationLink);

        // Lưu emailVerificationToken và đánh dấu user chưa xác thực
        user.setEmailVerificationToken(emailVerificationToken);
        user.setIsActive(false); // Chưa xác thực
        userRepo.save(user);

        ApiResponse response = new ApiResponse();
        response.setMessage("Đăng ký thành công. Vui lòng kiểm tra email để xác thực.");
        response.setSuccess(true);

        userDto = modelMapper.map(registeredUser, UserDto.class);

        return userDto;
    }

//    @Transactional
//    @Override
//    public void updatePassword(UserDto userDTO) {
//        User user = userRepo.findById(userDTO.getUserId()).orElseThrow(NoResultException::new);
//
//        user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));
//
//        userRepo.save(user);
//    }
@Transactional
@Override
public void updatePassword(UserDto userDTO, String oldPassword) {
    User user = userRepo.findById(userDTO.getUserId()).orElseThrow(NoResultException::new);

    // Kiểm tra mật khẩu cũ
    if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) {
        throw new IllegalArgumentException("Mật khẩu cũ không đúng");
    }

    // Cập nhật mật khẩu mới
    user.setPassword(new BCryptPasswordEncoder().encode(userDTO.getPassword()));

    userRepo.save(user);
}


    @Override
    public UserDto getUserById(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(() -> new ResoureNotFoundException("User", "Id", userId));
        return this.convertToUserDto(user);
    }

    @Override
    public PageDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        if (pageNumber != null && pageSize != null) {
            Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

            Pageable p = PageRequest.of(pageNumber, pageSize, sort);

            Page<User> pageUser = this.userRepo.findAll(p);
            List<User> allUsers = pageUser.getContent();
            List<UserDto> userDtos = allUsers.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());

            PageDto<UserDto> userPageResponse = new PageDto<>();
            userPageResponse.setContents(userDtos);
            userPageResponse.setPageNumber(pageUser.getNumber());
            userPageResponse.setPageSize(pageUser.getSize());
            userPageResponse.setTotalElements(pageUser.getTotalElements());
            userPageResponse.setTotalPages(pageUser.getTotalPages());
            userPageResponse.setLastPage(pageUser.isLast());
            return userPageResponse;

        } else {

            List<User> allUsers = this.userRepo.findAll();
            List<UserDto> userDtos = allUsers.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
            PageDto<UserDto> userPageResponse = new PageDto<>();
            userPageResponse.setContents(userDtos);
            return userPageResponse;

        }

    }


//    @Override
//    public List<UserDto> searchUsers(String keyword) {
//        List<User> users = this.userRepo.searchByEmail("%" + keyword +"%");
//        List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
//        return userDtos;
//    }

    private User convertToUser(UserDto userDto) {
        //cach 2
        User user = this.modelMapper.map(userDto, User.class);
        //cach1
//        User user = new User();
//        user.setId(userDto.getId());
//        user.setName(userDto.getName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }

    private UserDto convertToUserDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        //cach 1
//        UserDto userDto = new UserDto();
//        userDto.setId(user.getId());
//        userDto.setName(user.getName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }
    @Override
    public int count(){
        return (int)userRepo.count();
    }
}
