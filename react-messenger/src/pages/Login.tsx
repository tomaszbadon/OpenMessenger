import { useReducer } from 'react';
import { useNavigate } from "react-router-dom";
import { Credentials, UserContext } from '../auth/types';
import { useLazyGetTokensQuery } from '../service/loginService';
import './Login.sass'
import { getUserContext, saveUserContext } from '../util/userContextManagement';

const Login = () => {

  const navigate = useNavigate()

  const localFormReducer = (formData: Credentials, newItems: any) => {
    return { ...formData, ...newItems }
  }

  const [formData, setFormData] = useReducer(localFormReducer, { username: "dominica.rosatti", password: "my_password" } )

  const [trigger] = useLazyGetTokensQuery();  

  const handleLoginForm = async () => {
    let payload = await trigger({ username: formData.username, password: formData.password }).unwrap();
    if (typeof payload !== 'undefined') {
      let userContext: UserContext = { user: { username: formData.username, isAuthenticated: true }, tokens: payload.tokens }
      saveUserContext(userContext)
      navigate("/chat")
    }
  }

  return <>
    <div className="box login login-form box-500">
      <img className='box-img' src="assets/chat_icon_128.png" alt='Chat icon' width="100px" />
      <div>
        <label>Username:</label>
        <input value={formData.username} onChange={(e) => setFormData({ username: e.target.value })} type="text" placeholder="" />
        <label>Password:</label>
        <input value={formData.password} onChange={(e) => setFormData({ password: e.target.value })} type="password" placeholder="" />
        <div className="button-div">
          <button className="button" onClick={handleLoginForm}>Login</button>
        </div>
      </div>
      <div className="links">
        <p><a href="#">Forgot Username / Password?</a></p>
        <p><a href="#">Sign Up</a></p>
      </div>
    </div>
  </>
};

export default Login;