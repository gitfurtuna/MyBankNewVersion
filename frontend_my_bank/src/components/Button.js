import React from 'react';

function Button({ onClick, children, type, disabled, className }) {
  return (
    <button
      onClick={onClick}
      type={type || 'button'}
      disabled={disabled}
      className={`button ${className || ''}`}
    >
      {children}
    </button>
  );
}

export default Button;