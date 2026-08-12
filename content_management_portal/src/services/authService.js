import api from "./api";

const login = async (email, password) => {
    const response = await api.post("/auth/login", {
        email,
        password,
    });

    const token = response.data.token;

    localStorage.setItem("adminToken", token);

    return response.data;
};

const logout = () => {
    localStorage.removeItem("adminToken");
};

const isLoggedIn = () => {
    return localStorage.getItem("adminToken") !== null;
};

export default {
    login,
    logout,
    isLoggedIn,
};