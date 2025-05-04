import React from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '../hooks/useAuth';
import accountImage from '../assets/accountImage.jpg';
import friendsImage from '../assets/friendsImage.png';
import merchImage from '../assets/merchImage.jpg';
import profileImage from '../assets/profileImage.jpg';

const UserHomePageForm = () => {
    const { data: userData, isAuthenticated, isLoading, error } = useAuth('http://localhost:8080/auth/hello');
    const navigate = useNavigate();

    const handleNavigation = (path) => {
        navigate(path);
    };

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
        <div className="image-container h1">
            <h1>{userData.name}, please go to ... </h1>

            <div className="links-container">
                <div className="link-item">
                    <a href="/profile" className="custom-links">My Profile</a>
                    <img src={profileImage} alt="Profile" className="framed-images" />
                </div>
                <div className="link-item">
                    <a href="/merch" className="custom-links">My Merch</a>
                    <img src={merchImage} alt="Merch" className="framed-images" />
                </div>
                <div className="link-item">
                    <a href="/friends" className="custom-links">My Friends</a>
                    <img src={friendsImage} alt="Friends" className="framed-images" />
                </div>
                <div className="link-item">
                    <a href="/account" className="custom-links">My Account</a>
                    <img src={accountImage} alt="Account" className="framed-images" />
                </div>
            </div>
        </div>
    );
}

export default UserHomePageForm;
