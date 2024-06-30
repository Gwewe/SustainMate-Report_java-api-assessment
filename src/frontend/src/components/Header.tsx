import React from 'react';
import { Link } from 'react-router-dom';
import './Header.css';

const Header: React.FC = () => {
  return (
    <header>
      <h1>
        SustainMate.
        <img src={process.env.PUBLIC_URL + 'images/Plant.svg'} alt="image plant logo" className='logo'></img>
        </h1>
      <nav>
        <ul>
          <li><Link to="/">Home</Link></li>
          <li><Link to="/about">About us</Link></li>
          <li><Link to ="/login">Log In</Link></li>
          <li><Link to="/reports">Reports</Link></li>
          <li><Link to="/contact">Contact us</Link></li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;