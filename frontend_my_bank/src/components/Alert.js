import React from 'react';

function Alert({ message, type, className }) {
    return (
        <div className={`alert ${type} ${className ? className : ''}`}>
            {message}
        </div>
    );
}

export default Alert;
