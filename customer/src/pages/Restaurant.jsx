import React, { useEffect, useState, useContext } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import { AppContext } from "../context/AppContext";

const Restaurant = () => {
    const { id } = useParams();
    const { token } = useContext(AppContext);
    const [restaurant, setRestaurant] = useState(null);
    const [foods, setFoods] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState("");
    const [currentImageIndex, setCurrentImageIndex] = useState(0);
    const [quantities, setQuantities] = useState({}); // per-food quantities

    // Fetch restaurant by id
    useEffect(() => {
        const fetchRestaurant = async () => {
            try {
                const response = await axios.get(
                    `${import.meta.env.VITE_BACKEND_URL}/api/restaurants/${id}`
                );
                setRestaurant(response.data);
                setLoading(false);
            } catch (err) {
                setError("Failed to fetch restaurant details");
                setLoading(false);
            }
        };

        fetchRestaurant();
    }, [id]);

    // Fetch foods for this restaurant
    useEffect(() => {
        const fetchFoods = async () => {
            try {
                const response = await axios.get(
                    `${import.meta.env.VITE_BACKEND_URL}/api/food/restaurant/${id}`
                );
                setFoods(response.data);
            } catch (err) {
                console.error("Failed to fetch foods", err);
            }
        };

        if (id) fetchFoods();
    }, [id]);

    // Auto slideshow
    useEffect(() => {
        if (restaurant && restaurant.images.length > 1) {
            const interval = setInterval(() => {
                setCurrentImageIndex(
                    (prev) => (prev + 1) % restaurant.images.length
                );
            }, 3000);
            return () => clearInterval(interval);
        }
    }, [restaurant]);

    const handleQuantityChange = (foodId, value) => {
        setQuantities((prev) => ({
            ...prev,
            [foodId]: value > 0 ? value : 1,
        }));
    };

    const handleAddToCart = async (foodId) => {
        const quantity = quantities[foodId] || 1;
        try {
            await axios.put(
                `${import.meta.env.VITE_BACKEND_URL}/api/customer/cart/add`,
                {
                    menuItemId: foodId,
                    quantity: quantity,
                },
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            alert("Item added to cart!");

            // ✅ reset quantity for this food back to 1
            setQuantities((prev) => ({
                ...prev,
                [foodId]: 1,
            }));
        } catch (err) {
            console.error("Error adding to cart:", err);
            alert("Failed to add item to cart.");
        }
    };

    if (loading) {
        return <p className="text-center mt-10 text-gray-500">Loading...</p>;
    }

    if (error) {
        return <p className="text-center mt-10 text-red-500">{error}</p>;
    }

    if (!restaurant) {
        return <p className="text-center mt-10 text-gray-500">No data found</p>;
    }

    return (
        <div className="p-6 max-w-4xl mx-auto">
            {/* Slideshow */}
            <div className="relative w-full h-72 mb-6 overflow-hidden rounded-lg shadow">
                {restaurant.images.map((img, index) => (
                    <img
                        key={index}
                        src={img}
                        alt={`${restaurant.name} ${index}`}
                        className={`absolute inset-0 w-full h-full object-cover transition-opacity duration-1000 ${
                            index === currentImageIndex
                                ? "opacity-100"
                                : "opacity-0"
                        }`}
                    />
                ))}
            </div>

            {/* Info Section */}
            <div className="bg-white rounded-lg shadow p-6 mb-6">
                <h2 className="text-2xl font-bold mb-4">{restaurant.name}</h2>
                <p className="text-gray-700 mb-4">{restaurant.description}</p>

                <p className="text-gray-600">
                    <span className="font-semibold">Cuisine:</span>{" "}
                    {restaurant.cuisineType}
                </p>

                <p className="text-gray-600 mt-2">
                    <span className="font-semibold">Address:</span>{" "}
                    {restaurant.address.streetAddress}, {restaurant.address.city}
                    , {restaurant.address.state},{" "}
                    {restaurant.address.postalCode},{" "}
                    {restaurant.address.country}
                </p>

                <p className="text-gray-600 mt-2">
                    <span className="font-semibold">Contact:</span>{" "}
                    {restaurant.contactInformation.email} |{" "}
                    {restaurant.contactInformation.mobile}
                </p>

                <p
                    className={`mt-4 font-semibold ${
                        restaurant.open ? "text-green-600" : "text-red-600"
                    }`}
                >
                    {restaurant.open ? "Open Now" : "Closed"}
                </p>
            </div>

            {/* Foods Section */}
            <h3 className="text-xl font-bold mb-4">Menu</h3>
            <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6">
                {foods.length === 0 ? (
                    <p className="col-span-full text-center text-gray-500">
                        No foods available.
                    </p>
                ) : (
                    foods.map((food) => (
                        <div
                            key={food.id}
                            className="bg-white rounded-lg shadow hover:shadow-lg transition-shadow duration-300 flex flex-col"
                        >
                            <img
                                src={food.images[0]}
                                alt={food.name}
                                className="w-full h-40 object-cover rounded-t-lg"
                            />
                            <div className="p-4 flex-1 flex flex-col">
                                <h4 className="text-lg font-semibold mb-2">
                                    {food.name}
                                </h4>
                                <p className="text-gray-600 text-sm mb-2">
                                    {food.description}
                                </p>
                                <p className="text-gray-800 font-medium mb-2">
                                    ¥{food.price}
                                </p>
                                <p
                                    className={`font-medium ${
                                        food.available
                                            ? "text-green-600"
                                            : "text-red-600"
                                    }`}
                                >
                                    {food.available
                                        ? "Available"
                                        : "Not Available"}
                                </p>

                                {/* Quantity + Add to Cart */}
                                <div className="mt-3 flex items-center gap-2">
                                    <input
                                        type="number"
                                        min="1"
                                        value={quantities[food.id] || 1}
                                        onChange={(e) =>
                                            handleQuantityChange(
                                                food.id,
                                                parseInt(e.target.value) || 1
                                            )
                                        }
                                        className="w-16 px-2 py-1 border rounded"
                                    />
                                    <button
                                        onClick={() => handleAddToCart(food.id)}
                                        className="px-4 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
                                    >
                                        Add to Cart
                                    </button>
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default Restaurant;
