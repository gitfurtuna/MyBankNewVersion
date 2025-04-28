import React from 'react';
import useAuth from '../hooks/useAuth';

const UserHomePageForm  = () => {

    const { data: userData, isAuthenticated, isLoading, error } = useAuth('http://localhost:8080/auth/user');

    if (isLoading) {
        return <div>Loading...</div>;
    }

    if (error) {
        return <div>Error: {error}</div>;
    }

    if (!isAuthenticated) {
        return <div>Please log in to access this page.</div>;
    }

    return (
        <div>
            <h1>Welcome, {userData.name}!</h1> {/*  */}
            <p>Email: {userData.email}</p> {/*  */}
            {/*  */}
        </div>
    );
};

export default UserHomePageForm ;
