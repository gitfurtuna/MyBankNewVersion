import React, { useState, useEffect } from 'react';
import useAuth from '../hooks/useAuth';

const UserProfile = () => {
    const { data: userData, isAuthenticated, isLoading, error } = useAuth('http://localhost:8080/auth/hello');
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [dateOfBirth, setDateOfBirth] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [file, setFile] = useState(null);
    const [updateError, setUpdateError] = useState(null);
    const [updateSuccess, setUpdateSuccess] = useState(false);
    const [uploadError, setUploadError] = useState(null);
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [photoUrl, setPhotoUrl] = useState(null);

    useEffect(() => {
        if (userData) {
            setName(userData.name);
            setSurname(userData.surname);
            setEmail(userData.email);
            setDateOfBirth(userData.dateOfBirth);
            setPhoneNumber(userData.phoneNumber);
            setPhotoUrl(userData.photoUrl);
        }
    }, [userData]);

    const handleSave = async (event) => {
        event.preventDefault();
        try {
            const response = await fetch(`http://localhost:8080/auth/profile/${userData.role}/${userData.email}/update`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ name, surname, email, password, dateOfBirth, phoneNumber }),
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error('Failed to update profile');
            }

            setUpdateSuccess(true);
            setUpdateError(null);
        } catch (error) {
            setUpdateError(error.message);
            setUpdateSuccess(false);
        }
    };

    const handleFileChange = (event) => {
        setFile(event.target.files[0]);
    };

    const handleUpload = async (event) => {
        event.preventDefault();
        if (!file) {
            setUploadError('Please select a file to upload.');
            return;
        }

        const formData = new FormData();
        formData.append('file', file);

        try {
            const response = await fetch(`http://localhost:8080/auth/profile/${userData.role}/${userData.email}/photo`, {
                method: 'POST',
                body: formData,
                credentials: 'include',
            });

            if (!response.ok) {
                throw new Error('Failed to upload photo');
            }

            const photo = await response.json();
            const photoUrl = `data:${photo.contentType};base64,${photo.bytes}`;

            setPhotoUrl(photoUrl);
            setUploadSuccess(true);
            setUploadError(null);
        } catch (error) {
            setUploadError(error.message);
            setUploadSuccess(false);
        }
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
        <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', maxWidth: '800px', margin: '0 auto', padding: '20px' }}>

            {/* Обертка для фото */}
            <div style={{ display: 'flex', flexDirection: 'column', alignItems: 'center', width: '100%', marginBottom: '20px' }}>
                {/* Надпись "Photo:" */}
                <label htmlFor="fileInput" style={{ marginBottom: '10px' }}>Photo:</label>

                {/* Квадратик с фото или надписью "No Photo" */}
                <div style={{
                    width: '150px',
                    height: '150px',
                    border: '1px solid #ccc',
                    backgroundColor: '#fff',
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    overflow: 'hidden',
                    marginBottom: '10px'
                }}>
                    {photoUrl ? (
                        <img src={photoUrl} alt="Uploaded" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
                    ) : (
                        <span>No Photo</span>
                    )}
                </div>

                {/* Форма загрузки фото - перемещена ниже */}
                <form style={{ display: 'flex', flexDirection: 'column', alignItems: 'center' }} onSubmit={handleUpload}>
                    <input
                        type="file"
                        id="fileInput"
                        accept="image/*"
                        onChange={handleFileChange}
                        style={{ marginBottom: '10px' }}
                    />
                    <button type="submit" style={{ padding: '10px 20px' }}>Upload</button>
                </form>

                {uploadSuccess && (
                    <div style={{ color: 'white', marginTop: '10px' }}>Photo uploaded successfully!</div>
                )}
                {uploadError && (
                    <div style={{ color: 'red', marginTop: '10px' }}>{uploadError}</div>
                )}
            </div>

            {/* Остальная форма профиля */}
            <form onSubmit={handleSave} style={{ width: '100%', display: 'flex', flexDirection: 'column', alignItems: 'center' }}>
                {/* Поля формы */}
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Name:</label>
                    <input
                        type="text"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                        required
                    />
                </div>
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Surname:</label>
                    <input
                        type="text"
                        value={surname}
                        onChange={(e) => setSurname(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                        required
                    />
                </div>
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                        required
                    />
                </div>
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Date of Birth:</label>
                    <input
                        type="date"
                        value={dateOfBirth}
                        onChange={(e) => setDateOfBirth(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>
                <div style={{ marginBottom: '10px', width: '100%' }}>
                    <label style={{ display: 'block', marginBottom: '5px' }}>Phone Number:</label>
                    <input
                        type="tel"
                        value={phoneNumber}
                        onChange={(e) => setPhoneNumber(e.target.value)}
                        style={{ width: '100%', padding: '8px' }}
                    />
                </div>

                <button type="submit" style={{ padding: '10px 20px', marginTop: '10px' }}>Save Changes</button>
            </form>

            {updateError && <div style={{ color: 'red', marginTop: '10px' }}>{updateError}</div>}
        </div>
    );
};

export default UserProfile;
