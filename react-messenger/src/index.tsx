import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import reportWebVitals from "./reportWebVitals";
import './index.sass'
import { Provider } from "react-redux";
import { store } from './store/store'
import { RenderRoutes } from "./auth/RenderRoutes";
import { StrictMode } from "react";

export default function App() {
  return (
    <BrowserRouter>
      <RenderRoutes />
    </BrowserRouter>
  )
}

const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);
root.render(
  <StrictMode>
    <Provider store={store}>
      <App />
    </Provider>
  </StrictMode>
);

reportWebVitals();