import React from 'react';
import { useNavigate } from 'react-router-dom';
import { assets } from '../assets/assets';

const Hero = () => {
    const navigate = useNavigate();

    return (
        <section
            className="w-full min-h-[80vh] flex items-center justify-center text-center px-4 sm:px-10 bg-secondary my-8"
        >
            <div className="max-w-4xl text-third">
                {/* Headline */}
                <h1 className="text-4xl md:text-6xl font-bold mb-4">
                    Delicious Meals Delivered to Your Doorstep in <span className="text-primary">Stadium City, Nagasaki</span>
                </h1>

                {/* Subtext */}
                <p className="text-lg mb-8 text-gray-500 italic font-light tracking-wider">
                    Fresh local flavors, quick delivery, and easy ordering for residents
                </p>

                {/* CTA Button */}
                <button
                    onClick={() => navigate('/all-restaurants')}
                    className="bg-third/50 text-white cursor-pointer font-semibold px-8 py-4 rounded-lg hover:scale-105 hover:bg-third transition-all duration-300"
                >
                    Order Now
                </button>
            </div>
        </section>
    );
};

export default Hero;
