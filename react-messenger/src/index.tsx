import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import reportWebVitals from "./reportWebVitals";
import { AuthWrapper } from "./auth/AuthWrapper";
import './index.sass'

export default function App() {
  return (
    <BrowserRouter>
      <AuthWrapper />
    </BrowserRouter>
  )
}

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(<App />);

reportWebVitals();