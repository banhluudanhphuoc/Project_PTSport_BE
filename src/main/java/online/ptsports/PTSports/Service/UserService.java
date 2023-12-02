package online.ptsports.PTSports.Service;


import online.ptsports.PTSports.DTO.PageDto;
import online.ptsports.PTSports.DTO.UserDto;

import online.ptsports.PTSports.Entity.PasswordResetToken;
import online.ptsports.PTSports.Entity.User;
import org.springframework.validation.Errors;

public interface UserService {

   void createUser(UserDto userDto);
   UserDto updateUser(UserDto user, Integer userId);
   UserDto getUserById(Integer userId);
   PageDto<UserDto> getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
   void deleteUser(Integer userId);

   UserDto findByEmail(String email) ;

   UserDto updateAvatar(UserDto userDto, Integer userId);

   UserDto registerUser(UserDto userDto);

   void updatePassword(UserDto userDTO, String oldPassword);

   void updatePasswords(UserDto userDTO, String token);



   int count();

   PasswordResetToken createPasswordResetToken(UserDto userDto);

   PasswordResetToken getPasswordResetToken(String token);

   void deletePasswordResetToken(PasswordResetToken passwordResetToken);




}
