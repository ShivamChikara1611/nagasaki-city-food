import React from 'react'
import { Route, Routes } from 'react-router-dom'
import 'react-toastify/dist/ReactToastify.css';
import { ToastContainer, toast } from 'react-toastify';
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import Home from './pages/Home'
import Login from './pages/Login'
import AllRestaurants from './components/AllRestaurants';
import Restaurant from './pages/Restaurant';



const App = () => {

  return (
    <div className='relative overflow-hidden'>
      <div className='fixed z-[99] w-full'>
        <Navbar />
      </div>

      {/* actual pages */}
      <div className='pt-[77px] md:px-[10%]'>
        <ToastContainer />
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/login' element={<Login/>} />
          <Route path='/all-restaurants' element={<AllRestaurants/>} />
          <Route path='/restaurants/:id' element={<Restaurant/>} />
        </Routes>
        {/* <Footer /> */}
      </div>
    </div>
  )
}

export default App