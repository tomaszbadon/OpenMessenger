import { useEffect, useReducer } from 'react';
import { useNavigate } from "react-router-dom";
import { AuthData, UserContextNullable } from '../auth/AuthWrapper';
import './Login.sass'

const Login = () => {

  const navigate = useNavigate()

  interface FormData { username: string, password: string }

  const userContext: UserContextNullable = AuthData()

  function reducer(formData: FormData, newItems: any): FormData{
    return {...formData, ...newItems }
  }

  const [formData, setFormData] = useReducer(reducer, {username: "dominica.rosatti", password: "my_password"});

  async function doLogin() {
    try {
      await userContext?.login(formData.username, formData.password)
      navigate("/chat")
    } catch(error) {
      console.error(error)
    }
  }

  return <>
    <div className="box login login-form box-500">
      <img className='box-img' src="assets/chat_icon_128.png" alt='Chat icon' width="100px" />
      <div>
          <label>Username:</label>
          <input value={formData.username} onChange={(e) => setFormData({username: e.target.value}) } type="text" placeholder=""/>
          <label>Password:</label>
          <input value={formData.password} onChange={(e) => setFormData({password: e.target.value}) } type="password" placeholder="" />
          <div className="button-div">
            <button className="button" onClick={doLogin} >Login</button>
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