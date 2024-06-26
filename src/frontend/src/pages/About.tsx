import React from 'react';
import { useNavigate } from 'react-router-dom';
import './About.css';

const About: React.FC = () => {
    const navigate = useNavigate();

    const goToReports = () => {
        navigate('api/reports');
      };

  return (
    <div className="about">
      <h2>Welcome to SustainMate</h2>
      <p>You caught us while we were working on this feature. SustainMate's new about page will be coming soon.</p>
      <button onClick={goToReports} className="button-reports">
        Go to Reports
        </button>
    </div>
  );
};

export default About;