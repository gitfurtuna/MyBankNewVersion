import React from 'react';

function Layout({ children }) {
  return (
    <div className="layout">
      <header>
        {/* Navigation bar or header content */}
      </header>
      <main>
        {children}
      </main>
      <footer>
        {/* Footer content */}
      </footer>
    </div>
  );
}

export default Layout;
