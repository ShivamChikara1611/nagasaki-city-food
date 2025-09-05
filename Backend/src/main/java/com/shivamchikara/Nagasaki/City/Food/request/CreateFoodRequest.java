package com.shivamchikara.Nagasaki.City.Food.request;



import java.util.List;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateFoodRequest {
	

    
    private String name;
    private String description;
    private Long price;
    private List<String> images;
    private Long restaurantId;

	

}
