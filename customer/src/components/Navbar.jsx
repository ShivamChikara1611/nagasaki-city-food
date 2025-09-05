import React, { useState, useContext } from 'react'
import { assets } from '../assets/assets'
import { NavLink, useNavigate } from 'react-router-dom'
import { AppContext } from '../context/AppContext';
import { toast } from 'react-toastify';

const Navbar = () => {

    const navigate = useNavigate();

    const { token, setToken} = useContext(AppContext);

    const [showMenu, setShowMenu] = useState(false);

    const logout = async () => {
        setToken(false);
        localStorage.removeItem('token');
        toast.success('Logged out successfully');
        navigate('/login');
    }


    return (
        <div className={`flex items-center justify-between text-md py-2 ${showMenu ? 'md:backdrop-blur-lg' : 'backdrop-blur-lg'} text-primary shadow px-2 md:px-[10%]`}>
            <img onClick={() => navigate('/')} className='w-[60px] rounded-full cursor-pointer' src={assets.logo} alt="" />
            <ul className='hidden md:flex items-start gap-5 font-normal'>
                <NavLink to='/'>
                    <li className='py-1 tracking-wide'>Home</li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden' />
                </NavLink>
                <NavLink to='/all-restaurants'>
                    <li className='py-1 tracking-wide'>Restaurants</li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden' />
                </NavLink>
                <NavLink to='/about'>
                    <li className='py-1 tracking-wide'>About</li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden' />
                </NavLink>
                <NavLink to='/contact'>
                    <li className='py-1 tracking-wide'>Contact</li>
                    <hr className='border-none outline-none h-0.5 bg-primary w-3/5 m-auto hidden' />
                </NavLink>
            </ul>

            <div className='flex items-center gap-4'>
                {
                    token
                        ? <button onClick={logout} className='bg-secondary backdrop-blur-md text-primary px-8 py-3 text-sm rounded-full hover:bg-primary hover:text-white transition-all duration-300 font-light hidden md:block cursor-pointer tracking-wide'>Logout</button>
                        : <button onClick={() => navigate('/login')} className='bg-secondary backdrop-blur-md text-primary px-8 py-3 text-sm rounded-full hover:bg-primary hover:text-white transition-all duration-300 font-light hidden md:block cursor-pointer tracking-wide'>Create Account</button>
                }

                <img onClick={() => setShowMenu(true)} className='w-[40px] md:hidden cursor-pointer' src={assets.menu_icon} alt="" />

                {/*--------Mobile Menu-----------*/}

                <div className={`${showMenu ? 'fixed w-full' : 'h-0 w-0'} md:hidden right-0 top-0 bottom-0 overflow-hidden transition-all text-gray-200 backdrop-blur-md bg-black/30`}>
                    <div className='flex items-center justify-between px-5 py-4'>
                        <img className='w-[60px] rounded-full cursor-pointer' src={assets.logo} alt="" />
                        <img className='w-[50px] cursor-pointer' onClick={() => setShowMenu(false)} src={assets.cross_icon} alt="" />
                    </div>
                    <ul className='flex flex-col items-center gap-5 mt-12 px-5 text-lg font-medium w-full'>
                        <NavLink onClick={() => setShowMenu(false)} to='/'><p className='px-10 py-2 rounded-full'>HOME</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/all-restaurants'><p className='px-10 py-2 rounded-full'>RESTAURANTS</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/about'><p className='px-10 py-2 rounded-full'>ABOUT</p></NavLink>
                        <NavLink onClick={() => setShowMenu(false)} to='/contact'><p className='px-10 py-2 rounded-full'>CONTACT US</p></NavLink>

                        {
                            token
                                ? null
                                : <button onClick={() => navigate('/login') & setShowMenu(false)} className='bg-white/20 backdrop-blur-md text-gray-200 hover:bg-primary/70 transition-all duration-300 px-8 py-3 rounded-full font-light md:hidden absolute bottom-10'>Create account</button>
                        }
                    </ul>
                </div>
            </div>
        </div>
    )
}

export default Navbar