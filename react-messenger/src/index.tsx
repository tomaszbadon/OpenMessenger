import React from 'react';
import ReactDOM from "react-dom/client";
import { useNavigate } from "react-router-dom";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./pages/_layout";
import Chat from "./pages/Chat";
import Login from "./pages/Login";
import reportWebVitals from "./reportWebVitals";
import './index.sass'

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Layout />}>
          <Route index element={<Chat />} />
          <Route path="chat/:username" element={<Chat />} />
          <Route path="login" element={<Login />} />
          <Route path="*" element={<Login />} />
        </Route>
      </Routes>
    </BrowserRouter>
  );
}

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(<App />);

reportWebVitals();