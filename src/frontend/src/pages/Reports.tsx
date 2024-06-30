
import React, { useState, useEffect } from 'react';
import { getAllReports, getReportsByCategory } from '../api/reportApi';
import ReportList from '../components/ReportList';
import './ReportsPage.css';

const ReportsPage: React.FC = () => {
  const [reports, setReports] = useState<any[]>([]);
  const [category, setCategory] = useState<string | null>(null);

  useEffect(() => {
    const fetchReports = async () => {
      try {
        const data = category ? await getReportsByCategory(category) : await getAllReports();
        setReports(data);
      } catch (error) {
        console.error('Error fetching reports:', error);
      }
    };

    fetchReports();
  }, [category]);

  return (
    <div className="reports-page">
      <h2>Reports</h2>
      <div className="categories">
        <button onClick={() => setCategory(null)}>All</button>
        <button onClick={() => setCategory('REGULATIONS')}>Regulations</button>
        <button onClick={() => setCategory('CORPORATE_INITIATIVES')}>Corporate Initiatives</button>
        <button onClick={() => setCategory('BEST_PRACTICES')}>Best Practices</button>
      </div>
      <ReportList reports={reports} />
    </div>
  );
};

export default ReportsPage;
