import React from 'react';
import { useNavigate } from 'react-router-dom';
import './Login.css';

const Login: React.FC = () => {
    const navigate = useNavigate();

    const goToReports = () => {
        navigate('api/reports');
      };

  return (
    <div className="login">
      <h2>Welcome to SustainMate</h2>
      <p>You caught us while we were working on this feature. SustainMate's new Log In page will be coming soon.</p>
      <button onClick={goToReports} className="button-reports">
        Go to Reports
        </button>
    </div>
  );
};

export default Login;