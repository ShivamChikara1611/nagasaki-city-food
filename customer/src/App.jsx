import React from 'react'
import { Route, Routes } from 'react-router-dom'
import Home from './pages/Home'
import Navbar from './components/Navbar'
import Footer from './components/Footer'
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';



const App = () => {

  return (
    <div className='relative overflow-hidden'>
      <div className='fixed z-[99] w-full'>
        <Navbar />
      </div>

      {/* actual pages */}
      <div className='pt-[77px]'>
        <ToastContainer />
        <Routes>
          <Route path='/' element={<Home />} />
        </Routes>
        <Footer />
      </div>
    </div>
  )
}

export default App