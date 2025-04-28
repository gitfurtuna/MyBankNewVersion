import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import InputField from '../components/InputField';
import Button from '../components/Button';
import Alert from '../components/Alert';
import './styles.css';
import axios from 'axios';

function RegistrationForm() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const [successMessage, setSuccessMessage] = useState('');
    const [fieldErrors, setFieldErrors] = useState({});
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        setErrorMessage('');
        setSuccessMessage('');
        setFieldErrors({});

        let hasErrors = false;
        const errors = {};

                if (!name) {
                    errors.name = 'Please enter your name.';
                    hasErrors = true;
                }
                if (!surname) {
                    errors.surname = 'Please enter your surname.';
                    hasErrors = true;
                }
                if (!dateOfBirth) {
                    errors.dateOfBirth = 'Please enter your date of birth.';
                    hasErrors = true;
                }
                if (!phoneNumber) {
                    errors.phoneNumber = 'Please enter your phone number.';
                    hasErrors = true;
                }
                if (!email) {
                    errors.email = 'Please enter your email.';
                    hasErrors = true;
                }
                if (!password) {
                    errors.password = 'Please enter your password.';
                    hasErrors = true;
                }

                setFieldErrors(errors);

                if (hasErrors) return;


        try {
            const response = await axios.post('http://localhost:8080/auth/register', {
                withCredentials: true,
                email: email,
                password: password,
                name: name,
                surname: surname,
                dateOfBirth: dateOfBirth,
                phoneNumber: phoneNumber
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 201) {
                setSuccessMessage('Registration successful!');
                setEmail('');
                setPassword('');
                setName('');
                setSurname('');
                setDateOfBirth('');
                setPhoneNumber('');

             }
             }catch (error) {
                 console.error('Registration error:', error);
                 if (error.response) {
                     if (error.response.status === 409) {
                         setErrorMessage(error.response.data.message);
                     } else {
                         setErrorMessage(`Registration failed: ${error.response.status} - ${error.response.data}`);
                     }
                 } else if (error.request) {
                     setErrorMessage('Registration failed: No response from server');
                 } else {
                     setErrorMessage('Registration failed: An unexpected error occurred');
                 }
        }
    };

    return (
        <>
            <h1>Client Registration Form</h1>
            {successMessage && <Alert message={successMessage} type="success" />}
            {errorMessage && <Alert message={errorMessage} type="error" />}

            <form onSubmit={handleSubmit} className="form-container">
                <InputField
                    label="Name"
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    required
                    placeholder="Enter your name"
                    errorMessage={fieldErrors.name}

                />
                <InputField
                    label="Surname"
                    type="text"
                    value={surname}
                    onChange={(e) => setSurname(e.target.value)}
                    required
                    placeholder="Enter your surname"
                    errorMessage={fieldErrors.surname}

                />
                <InputField
                    label="Date of Birth"
                    type="date"
                    value={dateOfBirth}
                    onChange={(e) => setDateOfBirth(e.target.value)}
                    required
                    placeholder="Enter your date of birth"
                    errorMessage={fieldErrors.dateOfBirth}

                />
                <InputField
                    label="Phone Number"
                    type="tel"
                    value={phoneNumber}
                     onChange={(e) => {
                            const value = e.target.value;
                            const phonePattern = /^[0-9+]*$/;


                            if (value.length > 15) {
                                setFieldErrors((prevErrors) => ({
                                    ...prevErrors,
                                    phoneNumber: 'Phone number must contain no more than 15 digits.',
                                }));
                            } else if (!phonePattern.test(value)) {
                                setFieldErrors((prevErrors) => ({
                                    ...prevErrors,
                                    phoneNumber: 'Phone number must contain only digits.',
                                }));
                            } else {
                                setFieldErrors((prevErrors) => ({
                                    ...prevErrors,
                                    phoneNumber: '',
                                }));
                                setPhoneNumber(value);
                            }
                        }}

                    required
                    placeholder="Enter your phone number"
                    errorMessage={fieldErrors.phoneNumber}

                />
                <InputField
                    label="Email"
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                    required
                    placeholder="Enter your email"
                    errorMessage={fieldErrors.email}

                />
                <InputField
                    label="Password"
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    required
                    placeholder="Enter your password"
                    errorMessage={fieldErrors.password}

                />
                {/* Кнопки */}

                 <Button type="submit" className="form-button">Register now</Button>
                 <Button type="button" onClick={() => navigate('/login')} className="form-button">Login</Button>
           </form>
        </>
    );
}

export default RegistrationForm;