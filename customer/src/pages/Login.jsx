import React, { useState, useContext, useEffect } from 'react';
import { AppContext } from '../context/AppContext';
import axios from 'axios';
import { toast, ToastContainer } from 'react-toastify';
import { useNavigate } from 'react-router-dom';
import 'react-toastify/dist/ReactToastify.css';

const Login = () => {
    const { backendUrl, token, setToken } = useContext(AppContext);
    const navigate = useNavigate();

    const [state, setState] = useState('Sign Up');
    const [fullName, setFullName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');

    const onSubmitHandler = async (e) => {
        e.preventDefault();
        try {
            if (state === 'Sign Up') {
                const { data } = await axios.post(`${backendUrl}/auth/signup`, {
                    fullName,
                    email,
                    password,
                    role: 'ROLE_CUSTOMER',
                });

                if (data.jwt) {
                    setToken(data.jwt);
                    toast.success('Signup successful!');
                    navigate('/');
                } else {
                    toast.error(data.message || 'Signup failed');
                }
            } else {
                const { data } = await axios.post(`${backendUrl}/auth/signin`, {
                    email,
                    password,
                });

                if (data.jwt) {
                    setToken(data.jwt);
                    toast.success('Login successful!');
                    navigate('/');
                } else {
                    toast.error(data.message || 'Login failed');
                }
            }
        } catch (error) {
            console.error(error);
            toast.error(error.response?.data?.message || error.message || 'Something went wrong');
        }
    };

    useEffect(() => {
        if (token) navigate('/');
    }, [token]);

    return (
        <div className="h-[calc(100vh-77px)] flex items-center justify-center">
            <ToastContainer />
            <form
                onSubmit={onSubmitHandler}
                className="bg-primary/50 backdrop-blur-md flex flex-col gap-4 p-10 w-full max-w-md rounded-xl text-white"
            >
                <h2 className="text-2xl font-semibold">{state === 'Sign Up' ? 'Create Account' : 'Login'}</h2>
                <p>Please {state === 'Sign Up' ? 'sign up' : 'log in'} to continue</p>

                {state === 'Sign Up' && (
                    <div className="w-full">
                        <p>Full Name</p>
                        <input
                            type="text"
                            value={fullName}
                            onChange={(e) => setFullName(e.target.value)}
                            className="bg-secondary/30 rounded w-full p-2 mt-1"
                            required
                        />
                    </div>
                )}

                <div className="w-full">
                    <p>Email</p>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        className="rounded w-full p-2 mt-1 bg-secondary/30"
                        required
                    />
                </div>

                <div className="w-full">
                    <p>Password</p>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        className="rounded w-full p-2 mt-1 bg-secondary/30"
                        required
                    />
                </div>

                <button
                    type="submit"
                    className="text-md mt-4 w-full rounded-md py-2 bg-secondary text-primary hover:bg-primary hover:text-white transition-all duration-300"
                >
                    {state === 'Sign Up' ? 'Create Account' : 'Login'}
                </button>

                <p className="mt-2">
                    {state === 'Sign Up' ? (
                        <>
                            Already have an account?{' '}
                            <span
                                onClick={() => setState('Login')}
                                className="text-primary underline cursor-pointer font-semibold"
                            >
                                Login here
                            </span>
                        </>
                    ) : (
                        <>
                            Create a new account?{' '}
                            <span
                                onClick={() => setState('Sign Up')}
                                className="text-primary underline cursor-pointer font-semibold"
                            >
                                Sign up here
                            </span>
                        </>
                    )}
                </p>
            </form>
        </div>
    );
};

export default Login;
