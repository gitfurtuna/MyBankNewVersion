import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import RegistrationForm from './pages/RegistrationForm';
import AuthenticationForm from './pages/AuthenticationForm';
import UserHomePageForm from './pages/UserHomePageForm';
import UserProfile from './pages/UserProfile';
import UserFriends from './pages/UserFriends';
import Layout from './components/Layout';
import './App.css';
import myLogo from './assets/logo.png';

function App() {
  return (
    <Router>
      <div className="App">
        <Layout>
          <Routes>
            <Route path="/register" element={<RegistrationForm />} />
            <Route path="/login" element={<AuthenticationForm />} />
            <Route path="/hello" element={<UserHomePageForm />} />
            <Route path="/profile" element={<UserProfile />} />
            <Route path="/friends" element={<UserFriends />} />
            <Route path="/" element={
              <div className="image-container h1">
                <h1>Welcome!
                Please go to <a href="/login" class="custom-link" >Login Page</a> or <a href="/register" class="custom-link">Register Page</a>< br/>
                My Bank is Your Bank</h1>
                <img src={myLogo} alt="My bank logo" className="framed-image" />
              </div>
            } />
          </Routes>
        </Layout>
      </div>
    </Router>
  );
}

export default App;