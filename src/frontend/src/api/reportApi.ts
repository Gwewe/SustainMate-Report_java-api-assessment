import axios from 'axios';
import {Report} from '../ReportInterface';
import { promises } from 'dns';

const API_URL = 'http://localhost:8080/api/reports';

// Get all reports
export const getAllReports = async (): Promise<Report[]> => {
  const response = await axios.get(API_URL);
  return response.data;
};

// To find report by their id.
export const getReportById = async (id: number): Promise<Report> => {
  try {
    const response = await axios.get(`${API_URL}/${id}`);
    return response.data;
  }catch (error) {
    console.error(`The report with this id was not found.'${id} :`, error);
    throw error;
  }

};

//Get all the reports by their category
export const getAllReportByCategory = async (category: string) => {
  try {
    const response = await axios.get(`${API_URL}/category/${category}`);
    return response.data;
  }catch (error) {
    console.error(`An unexpected error occurred while retrieving reports`, error);
    throw error;
  }

}

//Search report by keyword
export const searchDescriptionByKeyword = async (keyword: string): Promise<Report[]> => {
  try {
    const response = await axios.get(`${API_URL}/search`, {
      params: { wordToFind: keyword },
    });
    return response.data;
  } catch (error) {
    console.error(`An unexpected error occurred while retrieving reports matching the keyword. '${keyword}' :`, error);
    throw error;
  }
}