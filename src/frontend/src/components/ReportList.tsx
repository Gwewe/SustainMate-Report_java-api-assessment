import React, { useEffect, useState } from 'react';
import { getReports } from '../api/reportApi';
import './ReportList.css';

const ReportList: React.FC = () => {
  const [reports, setReports] = useState<any[]>([]);

  useEffect(() => {
    const fetchReports = async () => {
      try {
        const data = await getReports();
        setReports(data);
      } catch (error) {
        console.error('Error while fetching reports: ', error);
      }
    };

    fetchReports();
  }, []);

  return (
    <div className="report-list">
      {reports.map(report => (
        <div key={report.id} className="report">
          <h2>{report.category}</h2>
          <a href={report.url}>{report.url}</a>
          <p>{report.description}</p>
          <small>{new Date(report.dateCreated).toLocaleDateString()}</small>
        </div>
      ))}
    </div>
  );
};

export default ReportList;
