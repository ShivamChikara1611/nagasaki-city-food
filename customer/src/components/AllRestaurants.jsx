import React, { useEffect, useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const AllRestaurants = () => {
  const navigate = useNavigate();

  const [restaurants, setRestaurants] = useState([]);
  const [filteredRestaurants, setFilteredRestaurants] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [searchTerm, setSearchTerm] = useState("");
  const [cuisineFilter, setCuisineFilter] = useState("All");
  const [openFilter, setOpenFilter] = useState("All");


  useEffect(() => {
    const fetchRestaurants = async () => {
      try {
        const response = await axios.get(
          `${import.meta.env.VITE_BACKEND_URL}/api/restaurants`
        );
        setRestaurants(response.data);
        setFilteredRestaurants(response.data);
        setLoading(false);
      } catch (err) {
        setError("Failed to fetch restaurants");
        setLoading(false);
      }
    };

    fetchRestaurants();
  }, []);

  useEffect(() => {
    let temp = [...restaurants];

    // Search by name
    if (searchTerm) {
      temp = temp.filter((r) =>
        r.name.toLowerCase().includes(searchTerm.toLowerCase())
      );
    }

    // Filter by cuisine
    if (cuisineFilter !== "All") {
      temp = temp.filter((r) => r.cuisineType === cuisineFilter);
    }

    // Filter by open status
    if (openFilter !== "All") {
      const isOpen = openFilter === "Open";
      temp = temp.filter((r) => r.open === isOpen);
    }

    setFilteredRestaurants(temp);
  }, [searchTerm, cuisineFilter, openFilter, restaurants]);

  const cuisineOptions = [
    "All",
    "Japanese",
    "Chinese",
    "Thai",
    "Indian",
    "Italian",
  ];

  if (loading) {
    return <p className="text-center mt-10 text-gray-500">Loading...</p>;
  }

  if (error) {
    return <p className="text-center mt-10 text-red-500">{error}</p>;
  }

  return (
    <div className="p-6">
      <h2 className="text-4xl font-normal tracking-wider text-third mb-8 text-center">All Restaurants</h2>

      {/* Search + Filters */}
      <div className="flex flex-col sm:flex-row justify-center mb-6 gap-8">
        {/* Search */}
        <input
          type="text"
          placeholder="Search restaurants"
          value={searchTerm}
          onChange={(e) => setSearchTerm(e.target.value)}
          className="px-4 py-1.5 rounded-md w-full sm:w-1/3 bg-secondary/30"
        />

        {/* Cuisine Filter Dropdown with Label */}
        <div className="flex items-center gap-2">
          {/* <label
            htmlFor="cuisineFilter"
            className="mb-1 text-sm font-medium text-gray-700"
          >
            Cuisine Type
          </label> */}
          <select
            id="cuisineFilter"
            value={cuisineFilter}
            onChange={(e) => setCuisineFilter(e.target.value)}
            className="px-4 py-2 rounded-md cursor-pointer bg-secondary/30"
          >
            {cuisineOptions.map((cuisine) => (
              <option key={cuisine} value={cuisine}>
                {cuisine}
              </option>
            ))}
          </select>
        </div>

        {/* Open/Closed Filter */}
        <div className="flex gap-2">
          {["All", "Open", "Closed"].map((status) => (
            <button
              key={status}
              onClick={() => setOpenFilter(status)}
              className={`px-4 rounded-md text-sm ${
                openFilter === status
                  ? "bg-third text-white"
                  : "bg-secondary/30 text-gray-500"
              } transition-colors duration-200 cursor-pointer`}
            >
              {status}
            </button>
          ))}
        </div>
      </div>

      {/* Restaurants Grid */}
      <div className="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-4 gap-6">
        {filteredRestaurants.length === 0 ? (
          <p className="col-span-full text-center text-primary">
            No restaurants found ...
          </p>
        ) : (
          filteredRestaurants.map((restaurant) => (
            <div
              key={restaurant.id}
              onClick={() => navigate(`/restaurants/${restaurant.id}`)}
              className="bg-third/30 rounded-lg shadow hover:shadow-lg transition-shadow duration-300 cursor-pointer relative"
            >
              <img
                src={restaurant.images[0]}
                alt={restaurant.name}
                className="w-full h-40 object-cover rounded-t-lg"
              />
              <div className="py-3 px-2">
                <h3 className="text-lg text-third font-semibold">{restaurant.name}</h3>
                <p className="text-gray-500 text-sm mb-1">
                  {restaurant.description}
                </p>
                <p className="bg-primary text-gray-200 w-fit py-1 px-3 rounded-full text-xs mb-1 absolute top-1 left-1">
                  {restaurant.cuisineType}
                </p>
                <p
                  className={`mt-1.5 text-xs w-fit ml-auto px-3 py-1.5 rounded-full text-gray-200 ${
                    restaurant.open ? "bg-third" : "bg-red-600"
                  }`}
                >
                  {restaurant.open ? "Open Now" : "Closed"}
                </p>
              </div>
            </div>
          ))
        )}
      </div>
    </div>
  );
};

export default AllRestaurants;
