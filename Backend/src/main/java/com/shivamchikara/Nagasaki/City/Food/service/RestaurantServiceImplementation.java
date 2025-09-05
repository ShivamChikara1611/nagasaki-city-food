package com.shivamchikara.Nagasaki.City.Food.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shivamchikara.Nagasaki.City.Food.Exception.RestaurantException;
import com.shivamchikara.Nagasaki.City.Food.dto.RestaurantDto;
import com.shivamchikara.Nagasaki.City.Food.model.Address;
import com.shivamchikara.Nagasaki.City.Food.model.Restaurant;
import com.shivamchikara.Nagasaki.City.Food.model.User;
import com.shivamchikara.Nagasaki.City.Food.repository.AddressRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.RestaurantRepository;
import com.shivamchikara.Nagasaki.City.Food.repository.UserRepository;
import com.shivamchikara.Nagasaki.City.Food.request.CreateRestaurantRequest;

@Service
public class RestaurantServiceImplementation implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest req, User user) {
        Address address = new Address();
        address.setCity(req.getAddress().getCity());
        address.setCountry(req.getAddress().getCountry());
        address.setPostalCode(req.getAddress().getPostalCode());
        address.setState(req.getAddress().getState());
        address.setStreetAddress(req.getAddress().getStreetAddress());
        Address savedAddress = addressRepository.save(address);

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(savedAddress);
        restaurant.setContactInformation(req.getContactInformation());
        restaurant.setCuisineType(req.getCuisineType());
        restaurant.setDescription(req.getDescription());
        restaurant.setImages(req.getImages());
        restaurant.setName(req.getName());
        restaurant.setOpeningHours(req.getOpeningHours());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedReq)
            throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);

        if (updatedReq.getName() != null) {
            restaurant.setName(updatedReq.getName());
        }
        if (updatedReq.getDescription() != null) {
            restaurant.setDescription(updatedReq.getDescription());
        }
        if (updatedReq.getCuisineType() != null) {
            restaurant.setCuisineType(updatedReq.getCuisineType());
        }
        if (updatedReq.getOpeningHours() != null) {
            restaurant.setOpeningHours(updatedReq.getOpeningHours());
        }
        if (updatedReq.getContactInformation() != null) {
            restaurant.setContactInformation(updatedReq.getContactInformation());
        }
        if (updatedReq.getImages() != null && !updatedReq.getImages().isEmpty()) {
            restaurant.setImages(updatedReq.getImages());
        }
        if (updatedReq.getAddress() != null) {
            Address address = restaurant.getAddress();
            if (address == null) {
                address = new Address();
            }
            if (updatedReq.getAddress().getCity() != null) {
                address.setCity(updatedReq.getAddress().getCity());
            }
            if (updatedReq.getAddress().getCountry() != null) {
                address.setCountry(updatedReq.getAddress().getCountry());
            }
            if (updatedReq.getAddress().getPostalCode() != null) {
                address.setPostalCode(updatedReq.getAddress().getPostalCode());
            }
            if (updatedReq.getAddress().getState() != null) {
                address.setState(updatedReq.getAddress().getState());
            }
            if (updatedReq.getAddress().getStreetAddress() != null) {
                address.setStreetAddress(updatedReq.getAddress().getStreetAddress());
            }
            Address savedAddress = addressRepository.save(address);
            restaurant.setAddress(savedAddress);
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws RestaurantException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        return restaurant.orElseThrow(
                () -> new RestaurantException("Restaurant with id " + restaurantId + " not found"));
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> getRestaurantsByUserId(Long userId) throws RestaurantException {
        List<Restaurant> restaurants = restaurantRepository.findByOwnerId(userId);
        if (restaurants.isEmpty()) {
            throw new RestaurantException("No restaurants found for owner with id " + userId);
        }
        return restaurants;
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return restaurantRepository.findAll();
        }
        return restaurantRepository.findBySearchQuery(keyword.trim());
    }

    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(restaurantId);

        RestaurantDto dto = new RestaurantDto();
        dto.setTitle(restaurant.getName());
        dto.setImages(restaurant.getImages());
        dto.setId(restaurant.getId());
        dto.setDescription(restaurant.getDescription());

        boolean isFavorited = false;
        List<RestaurantDto> favorites = user.getFavorites();
        for (RestaurantDto favorite : favorites) {
            if (favorite.getId().equals(restaurantId)) {
                isFavorited = true;
                break;
            }
        }

        if (isFavorited) {
            favorites.removeIf(favorite -> favorite.getId().equals(restaurantId));
        } else {
            favorites.add(dto);
        }

        userRepository.save(user);
        return dto;
    }

    @Override
    public Restaurant updateRestaurantStatus(Long id) throws RestaurantException {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        return restaurantRepository.save(restaurant);
    }
}
