import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

function useAuth(url) {
    const [data, setData] = useState(null);
    const [isAuthenticated, setIsAuthenticated] = useState(false);
    const [isLoading, setIsLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    const refreshAccessToken = async () => {
        try {
            const response = await axios.post('/auth/token', {}, { withCredentials: true });
            return response;
        } catch (error) {
            console.error("Error refreshing access token:", error);
            return null;
        }
    };

    const refreshRefreshToken = async () => {
        try {
            const response = await axios.post('/auth/refresh', {}, { withCredentials: true });
            return response;
        } catch (error) {
            console.error("Error refreshing refresh token:", error);
            return null;
        }
    };

    const fetchData = async () => {
        setIsLoading(true);
        setError(null);

        try {
            const response = await axios.get(url, { withCredentials: true });

            if (response.status === 200) {
                setIsAuthenticated(true);
                setData(response.data);
            } else {
                throw new Error(`Request failed with status ${response.status}`);
            }
        } catch (error) {
            handleAuthError(error);
        } finally {
            setIsLoading(false);
        }
    };

    const handleAuthError = async (error) => {
        setIsAuthenticated(false);
        if (error.response && (error.response.status === 401 || error.response.status === 403)) {
            console.warn("Token expired, attempting to refresh access token...");
            const refreshResponse = await refreshAccessToken();

            if (refreshResponse && refreshResponse.status === 200) {
                console.log("Access token refreshed successfully.");
                await fetchData(); // Пытаемся снова получить данные
            } else {
                console.warn("Attempting to refresh refresh token...");
                const refreshResponse2 = await refreshRefreshToken();

                if (refreshResponse2 && refreshResponse2.status === 200) {
                    console.log("Access and refresh token refreshed successfully.");
                    await fetchData(); // Пытаемся снова получить данные
                } else {
                    console.error("Failed to refresh tokens. Redirecting to login.");
                    setError("Session expired. Please log in again.");
                    navigate('/login');
                }
            }
        } else {
            setError(error.message || "Request failed");
        }
    };

    useEffect(() => {
        fetchData();
    }, [url, navigate]);

    return { data, isAuthenticated, isLoading, error };
}

export default useAuth;
