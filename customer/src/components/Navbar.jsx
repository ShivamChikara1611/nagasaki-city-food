import React from 'react'
import { assets } from '../assets/assets'

const Navbar = () => {
    return (
        <div className='fixed top-0 left-0 right-0 bg-transparent text-primary py-1.5 px-5 shadow-primary/10 shadow'>
            <img src={assets.logo} alt="Logo" className='w-12 rounded-full' />
        </div>
    )
}

export default Navbar