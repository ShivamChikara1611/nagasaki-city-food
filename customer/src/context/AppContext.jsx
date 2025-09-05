import { createContext, useState, useEffect } from "react";
import axios from "axios";
import { toast } from "react-toastify";

export const AppContext = createContext();

const AppContextProvider = (props) => {
    const backendUrl = import.meta.env.VITE_BACKEND_URL;

    const [token, setToken] = useState(localStorage.getItem('jwt') || false);

    useEffect(() => {
        if (token) {
            localStorage.setItem('jwt', token);
        } else {
            localStorage.removeItem('jwt');
        }
    }, [token]);

    const value = {
        token,
        setToken,
        backendUrl
    };

    return <AppContext.Provider value={value}>{props.children}</AppContext.Provider>;
};

export default AppContextProvider;
