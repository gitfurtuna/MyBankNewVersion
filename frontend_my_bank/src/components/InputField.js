import React from 'react';

function InputField({ label, type, value, onChange, placeholder, required, errorMessage }) {
  return (
    <div className="input-field-container">
      <label>
        {label}:
        <input
          type={type}
          value={value}
          onChange={onChange}
          placeholder={placeholder}
          required={required}
          className="input-field"
        />
      </label>
      {errorMessage && <span className="error-message">{errorMessage}</span>}
    </div>
  );
}

export default InputField;
