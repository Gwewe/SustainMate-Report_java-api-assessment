import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Home from './pages/Home';
import About from './pages/About';
import Login from './pages/Login';
import Reports from './pages/Reports';
import Contact from './pages/Contact';

const App: React.FC = () => {
  return (
    <Router>
      <Header />
      <Routes>
        <div>
            <Route path="/" element={<Home/>} />
          <Route path="/about-us" element={<About/>} />
          <Route path="/log-in" element={<Login/>} />
          <Route path="/reports" element={<Reporsssts/>} />
          <Route path="/contact" element={<Contact/>} />
        </div>
      </Routes>
      <Footer />
    </Router>
  );
};

export default App;
