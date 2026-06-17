import React from 'react'; 
import Navbar from '../components/common/Navbar'; 
import Sidebar from '../components/common/Sidebar'; 
import Footer from '../components/common/Footer'; 
 
export default function MainLayout({ children }) { 
  return ( 
    <div className="min-h-screen flex flex-col bg-[#0b0f19] text-gray-100 antialiased"> 
      <Navbar /> 
      <div className="flex-grow flex flex-col md:flex-row"> 
        <Sidebar /> 
        <main className="flex-grow p-4 md:p-8 max-w-5xl mx-auto w-full overflow-x-hidden"> 
          {children} 
        </main> 
      </div> 
      <Footer /> 
    </div> 
  ); 
}