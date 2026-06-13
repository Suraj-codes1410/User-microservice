package com.ecommerce.user.services;

import com.ecommerce.user.dto.AddressDTO;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.model.Address;
import com.ecommerce.user.model.AppUser;
import com.ecommerce.user.model.UserRole;
import com.ecommerce.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> fetchAllUser() {
        return userRepository.findAll().stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }


    public void addUser(UserRequest userRequest) {

        AppUser appUser = new AppUser();
        updateUserFromRequest(appUser, userRequest);
        userRepository.save(appUser);
    }

    public Optional<UserResponse> fetchUser(Long id) {
        return userRepository.findById(id)
                .map(this::mapToUserResponse);

    }

    public boolean updateUser(Long id, UserRequest updatedUserRequest) {
        return userRepository.findById(id)
                .map(existingAppUser -> {
                    updateUserFromRequest(existingAppUser,updatedUserRequest);
                    userRepository.save(existingAppUser);
                    return true;
                }).orElse(false);

    }

    private void updateUserFromRequest(AppUser appUser, UserRequest userRequest) {
        if (appUser.getRole() == null) {
            appUser.setRole(UserRole.CUSTOMER);
        }
        appUser.setFirstName(userRequest.getFirstName());
        appUser.setLastName(userRequest.getLastName());
        appUser.setEmail(userRequest.getEmail());
        appUser.setPhone(userRequest.getPhone());
        if (userRequest.getAddress() != null) {
            Address address = new Address();
            address.setStreet(userRequest.getAddress().getStreet());
            address.setState(userRequest.getAddress().getState());
            address.setCity(userRequest.getAddress().getCity());
            address.setZipcode(userRequest.getAddress().getZipcode());
            address.setCountry(userRequest.getAddress().getCountry());
            appUser.setAddress(address);
        }
    }


    private UserResponse mapToUserResponse(AppUser appUser) {
        UserResponse response = new UserResponse();

        response.setId(String.valueOf(appUser.getId()));
        response.setFirstName(appUser.getFirstName());
        response.setLastName(appUser.getLastName());
        response.setEmail(appUser.getEmail());
        response.setPhone(appUser.getPhone());
        response.setRole(appUser.getRole());

        if (appUser.getAddress() != null) {

            AddressDTO addressDto = new AddressDTO();

            addressDto.setStreet(appUser.getAddress().getStreet());
            addressDto.setCity(appUser.getAddress().getCity());
            addressDto.setState(appUser.getAddress().getState());
            addressDto.setZipcode(appUser.getAddress().getZipcode());
            addressDto.setCountry(appUser.getAddress().getCountry());

            response.setAddress(addressDto);
        }

        return response;
    }
}



