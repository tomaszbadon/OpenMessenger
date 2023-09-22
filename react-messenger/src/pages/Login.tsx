import { useReducer } from 'react';
import { useNavigate } from "react-router-dom";
import { login } from '../auth/AuthenticationSlice';
import { useAppDispatch, useAppSelector } from '../auth/types';
import './Login.sass'

interface FormData { username: string, password: string }

const Login = () => {

  const navigate = useNavigate()

  const { user } = useAppSelector((state) => state.authSlice)

  const dispatch = useAppDispatch()

  const handleLoginForm = () => {
    dispatch(login({user: {username: formData.username, isAuthenticated: true }, tokens: []}))
    navigate("/chat")
  }

  const localFormReducer = (formData: FormData, newItems: any) => {
    return {...formData, ...newItems }
  }

  const [formData, setFormData] = useReducer(localFormReducer, {username: "dominica.rosatti", password: "my_password"});

  return <>
    <div className="box login login-form box-500">
      <img className='box-img' src="assets/chat_icon_128.png" alt='Chat icon' width="100px" />
      <div>
          <label>Username:</label>
          <input value={formData.username} onChange={(e) => setFormData({username: e.target.value}) } type="text" placeholder=""/>
          <label>Password:</label>
          <input value={formData.password} onChange={(e) => setFormData({password: e.target.value}) } type="password" placeholder="" />
          <div className="button-div">
            <button className="button" onClick={handleLoginForm} >Login</button>
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