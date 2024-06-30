import React from 'react';
import { Link } from 'react-router-dom';
import './Footer.css';

const Footer: React.FC = () => {
  return (
    <footer>
      <nav>
        <ul>
          <li><Link to="/terms">Terms</Link></li>
          <li><Link to="/contact">Contact</Link></li>
          <li><Link to="/about">About Us</Link></li>
          <li><a href="https://www.linkedin.com/in/wednaguirand" target="_blank" rel="noopener noreferrer">
          <img src="/images/linkedin-logo.svg" alt="LinkedIn logo" className="linkedin-logo" />
          </a></li>
        </ul>
      </nav>
      <p>&copy; 2024 SustainMate. All rights reserved.</p>
    </footer>
  );
};

export default Footer;